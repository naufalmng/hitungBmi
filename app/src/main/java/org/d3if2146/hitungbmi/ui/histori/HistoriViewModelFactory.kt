package org.d3if2146.hitungbmi.ui.hitung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.d3if2146.hitungbmi.core.data.source.db.BmiDao
import org.d3if2146.hitungbmi.ui.histori.HistoriViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class HistoriViewModelFactory (private val db: BmiDao): ViewModelProvider.Factory {
    override fun  <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HistoriViewModel::class.java)){
            return HistoriViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}