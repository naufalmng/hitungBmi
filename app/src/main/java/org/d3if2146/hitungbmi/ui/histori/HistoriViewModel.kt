package org.d3if2146.hitungbmi.ui.histori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2146.hitungbmi.core.data.repository.AppRepository
import org.d3if2146.hitungbmi.core.data.source.db.BmiDao
import org.d3if2146.hitungbmi.core.data.source.db.BmiEntity

class HistoriViewModel(private val db: BmiDao): ViewModel() {
    private val appRepository: AppRepository = AppRepository(db)

    val listDataBmi : LiveData<List<BmiEntity>> get() = (appRepository.getListDataBmi())
    var dataBmi: BmiEntity? = null
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() =_isLoading


    suspend fun clearAllData() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            appRepository.clearAllData()
        }
    }

    suspend fun updateData(id: Long, date: Long, berat: Float,tinggi: Float,isMale: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val dataBmi = BmiEntity(id = id,tanggal = date,berat = berat,tinggi = tinggi,isMale = isMale)
        appRepository.updateData(dataBmi)
    }

    fun hapusData(bmi: BmiEntity?){
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.removeData(bmi)
        }
    }

    fun setIsLoading(boolean: Boolean){
        _isLoading.value = boolean
    }

}