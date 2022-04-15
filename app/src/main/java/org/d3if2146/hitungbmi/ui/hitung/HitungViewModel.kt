package org.d3if2146.hitungbmi.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2146.hitungbmi.core.data.source.db.BmiDao
import org.d3if2146.hitungbmi.core.data.source.db.BmiEntity
import org.d3if2146.hitungbmi.core.data.source.model.HasilBmi
import org.d3if2146.hitungbmi.core.data.source.model.KategoriBmi
import org.d3if2146.hitungbmi.core.data.source.model.UserInput

class HitungViewModel(private val db: BmiDao): ViewModel() {
    private var _hasilBmi = MutableLiveData<HasilBmi?>()
    val hasilBmi : LiveData<HasilBmi?> get() = _hasilBmi
//    val data = db.getLastBmi()

    private var _navigasi = MutableLiveData<KategoriBmi?>()
    val navigasi: LiveData<KategoriBmi?> get() = _navigasi

    fun mulaiNavigasi(){
        _navigasi.value = hasilBmi.value?.kategori
    }
    fun selesaiNavigasi(){
        _navigasi.value = null
    }

    fun getUserInput(berat: String, tinggi: String, gender: String): UserInput{
        return UserInput(berat,tinggi,gender)
    }

    fun saveUserInput(berat: String, tinggi: String, gender: String){
        UserInput(berat,tinggi,gender)
    }
    fun deleteUserInput(){
        UserInput("","","")
    }



   fun hitungBmi(berat: String, tinggi: String, isMale: Boolean){
       viewModelScope.launch {
           withContext(Dispatchers.IO){
               val dataBmi = BmiEntity(berat = berat.toFloat(),tinggi = tinggi.toFloat(),isMale = isMale)
               db.insert(dataBmi)
           }
       }

        val cmTinggi = tinggi.toFloat()/100
        val bmi = berat.toFloat() / (cmTinggi * cmTinggi)
        val kategori = getKategori(bmi,isMale)
        _hasilBmi.value = HasilBmi(bmi,kategori)

    }


    private fun getKategori(bmi: Float, isMale: Boolean): KategoriBmi {
        val kategori = if (isMale) {
            when {
                bmi < 20.5 -> KategoriBmi.KURUS
                bmi >= 27 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        } else{
            when{
                bmi < 18.5 -> KategoriBmi.KURUS
                bmi >= 25 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        }
        return kategori
    }
}