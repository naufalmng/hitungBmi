package org.d3if2146.hitungbmi.core.data.source.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class UserInput(
    val berat: String,
    val tinggi: String,
    val gender: String
): Serializable
