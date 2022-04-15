package org.d3if2146.hitungbmi

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2146.hitungbmi.core.data.source.db.BmiEntity
import org.d3if2146.hitungbmi.core.data.source.model.HasilBmi
import org.d3if2146.hitungbmi.core.data.source.model.KategoriBmi

@SuppressLint("ClickableViewAccessibility")
fun Context.setupBtnOnLongClickListener(view: View) {
    view.setOnTouchListener { v, event ->
        if (event?.action == MotionEvent.ACTION_DOWN) {
            // scale your value
            val reducedvalue = 0.95F
            v?.scaleX = reducedvalue
            v?.scaleY = reducedvalue
        } else if (event?.action == MotionEvent.ACTION_UP) {
            v?.scaleX = 1f
            v?.scaleY = 1f
        }
        false
    }
}

fun BmiEntity.hitungBmi(): HasilBmi{

    val cmTinggi = tinggi/100
    val bmi = berat / (cmTinggi * cmTinggi)
    val kategori = getKategori(bmi,isMale)
    return HasilBmi(bmi,kategori)

}

fun getKategori(bmi: Float, isMale: Boolean): KategoriBmi {
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