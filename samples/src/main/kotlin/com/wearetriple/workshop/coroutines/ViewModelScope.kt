package com.wearetriple.workshop.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    fun doSomething() {
        viewModelScope.launch { // Remember this one?

        }
    }
}