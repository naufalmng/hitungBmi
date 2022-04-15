package org.d3if2146.hitungbmi.ui.hitung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.d3if2146.hitungbmi.core.data.source.db.BmiDao
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class HitungViewModelFactory(private val db: BmiDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HitungViewModel::class.java)){
            return HitungViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}