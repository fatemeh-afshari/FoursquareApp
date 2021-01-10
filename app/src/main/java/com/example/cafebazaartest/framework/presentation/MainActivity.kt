package com.example.cafebazaartest.framework.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.cafebazaartest.R
import com.example.cafebazaartest.business.domain.state.DialogInputCaptureCallback
import com.example.cafebazaartest.business.domain.state.Response
import com.example.cafebazaartest.business.domain.state.StateMessageCallback
import com.example.cafebazaartest.framework.presentation.common.VenueFragmentFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),UIController {

    @Inject
    lateinit var fragmentFactory: VenueFragmentFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        setFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    private fun setFragmentFactory(){
        supportFragmentManager.fragmentFactory = fragmentFactory
    }

    private fun inject(){
        (application as BaseApplication).appComponent
            .inject(this)
    }
    override fun displayProgressBar(isDisplayed: Boolean) {
        if(isDisplayed)
            main_progress_bar.visibility = View.VISIBLE
        else
            main_progress_bar.visibility = View.GONE
    }

    override fun hideSoftKeyboard() {
        TODO("Not yet implemented")
    }

    override fun displayInputCaptureDialog(title: String, callback: DialogInputCaptureCallback) {
        TODO("Not yet implemented")
    }

    override fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    ) {
       stateMessageCallback.removeMessageFromStack()
    }

}