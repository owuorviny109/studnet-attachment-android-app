package com.attachmentplatform.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    // You can inject any needed repositories or dependencies here later
) : ViewModel() {

    // Add any shared app-level logic or state here (optional)

}
