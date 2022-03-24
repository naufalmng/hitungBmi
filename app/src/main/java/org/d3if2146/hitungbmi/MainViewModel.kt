package org.d3if2146.hitungbmi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var _bbLiveData = MutableLiveData<Int?>()
    private val bbLiveData = MutableLiveData<Int?>()

    private var _tbLiveData = MutableLiveData<Int?>()
    private val tbLiveData = MutableLiveData<Int?>()


}