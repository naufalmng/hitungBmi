package org.d3if2146.hitungbmi.ui.histori

import androidx.lifecycle.ViewModel
import org.d3if2146.hitungbmi.core.data.source.db.BmiDao
import org.d3if2146.hitungbmi.core.data.source.db.BmiDb

class HistoriViewModel(db: BmiDao): ViewModel() {
    val data = db.getLastBmi()

}