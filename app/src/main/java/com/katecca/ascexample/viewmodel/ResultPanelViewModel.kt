package com.katecca.ascexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultPanelViewModel : ViewModel() {
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private val _result = MutableLiveData("")
    var result: LiveData<String> = _result

    // onNameChanged is an event we're defining that the UI can invoke
    // (events flow up from UI)
    fun onResultChanged(newResult: String) {
        _result.postValue(result.value + newResult + "\n")
    }

    fun clear(){
        _result.value = ""
    }
}