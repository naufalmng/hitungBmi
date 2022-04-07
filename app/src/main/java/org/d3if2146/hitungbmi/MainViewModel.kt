package org.d3if2146.hitungbmi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if2146.hitungbmi.core.data.source.model.HasilBmi
import org.d3if2146.hitungbmi.core.data.source.model.KategoriBmi

class MainViewModel: ViewModel() {
    private var _hasilBmi = MutableLiveData<HasilBmi?>()
    val hasilBmi : LiveData<HasilBmi?> get() = _hasilBmi


   fun hitungBmi(berat: String, tinggi: String, isMale: Boolean){
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