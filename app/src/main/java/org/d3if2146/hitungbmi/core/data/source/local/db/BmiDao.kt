package org.d3if2146.hitungbmi.core.data.source.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface BmiDao {
    @Insert
    suspend fun insert(bmi: BmiEntity)

    @Query("SELECT * FROM bmi")
    fun getListDataBmi(): LiveData<List<BmiEntity>>

    @Query("SELECT * FROM bmi")
    fun getArrayListOfDataBmi(): BmiEntity

    @Query("DELETE FROM bmi")
    suspend fun clearDataBmi()

    @Delete
    suspend fun removeDataBmi(bmi: BmiEntity?)

    @Update
    suspend fun updateDataBmi(bmi: BmiEntity)

}