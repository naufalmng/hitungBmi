package org.d3if2146.hitungbmi.core.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2146.hitungbmi.core.data.source.db.BmiDao
import org.d3if2146.hitungbmi.core.data.source.db.BmiEntity

class AppRepository(private val bmiDao: BmiDao) {
    suspend fun clearAllData() = bmiDao.clearDataBmi()
    suspend fun insertData(bmi: BmiEntity) = bmiDao.insert(bmi)
    suspend fun removeData(bmi: BmiEntity?) = bmiDao.removeDataBmi(bmi)
    suspend fun updateData(bmi: BmiEntity) = bmiDao.updateDataBmi(bmi)
    fun getArrayListOfDataBmi() = bmiDao.getArrayListOfDataBmi()
    fun getListDataBmi() = bmiDao.getListDataBmi()
}