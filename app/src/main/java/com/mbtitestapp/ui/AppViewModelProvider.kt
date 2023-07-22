package com.mbtitestapp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mbtitestapp.MbtiApplication
import com.mbtitestapp.ui.select.SelectViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            SelectViewModel(mbtiApplication().container.mbtiInfoRepository, mbtiApplication().container.questionRepository)
        }
    }
}
fun CreationExtras.mbtiApplication(): MbtiApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MbtiApplication)