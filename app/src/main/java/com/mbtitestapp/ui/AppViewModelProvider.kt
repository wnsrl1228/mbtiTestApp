package com.mbtitestapp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mbtitestapp.MbtiApplication
import com.mbtitestapp.ui.result.MbtiResultViewModel
import com.mbtitestapp.ui.select.SelectViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for SelectViewModel
        initializer {
            SelectViewModel(
                this.createSavedStateHandle(),
                mbtiApplication().container.questionRepository,
                mbtiApplication().container.mbtiResultRepository
            )
        }

        initializer {
            MbtiResultViewModel(
                this.createSavedStateHandle(),
                mbtiApplication().container.mbtiResultRepository
            )
        }

    }
}
fun CreationExtras.mbtiApplication(): MbtiApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MbtiApplication)