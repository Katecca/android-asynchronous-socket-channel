package com.katecca.ascexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConnectBtnViewModel : ViewModel() {
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _enabled = MutableLiveData<Boolean>()
    val enabled: LiveData<Boolean> = _enabled

    // onNameChanged is an event we're defining that the UI can invoke
    // (events flow up from UI)
    fun onNameChanged(newName: String) {
        _name.postValue(newName)
    }

    fun onEnabledChanged(enabled: Boolean) {
        _enabled.postValue(enabled)
    }
}