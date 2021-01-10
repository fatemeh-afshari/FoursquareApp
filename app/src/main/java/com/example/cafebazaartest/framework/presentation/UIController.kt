package com.example.cafebazaartest.framework.presentation

import com.example.cafebazaartest.business.domain.state.DialogInputCaptureCallback
import com.example.cafebazaartest.business.domain.state.Response
import com.example.cafebazaartest.business.domain.state.StateMessageCallback

interface UIController {

    fun displayProgressBar(isDisplayed: Boolean)

    fun hideSoftKeyboard()

    fun displayInputCaptureDialog(title: String, callback: DialogInputCaptureCallback)

    fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    )

}