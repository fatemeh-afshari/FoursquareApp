package com.example.cafebazaartest.framework.presentation.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.cafebazaartest.di.AppComponent
import com.example.cafebazaartest.framework.presentation.BaseApplication
import com.example.cafebazaartest.framework.presentation.MainActivity
import com.example.cafebazaartest.framework.presentation.UIController

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.lang.ClassCastException

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseFragment
constructor(
    private @LayoutRes val layoutRes: Int
): Fragment() {
    lateinit var uiController: UIController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }



    abstract fun inject()

    fun getAppComponent(): AppComponent {
        return activity?.run {
            (application as BaseApplication).appComponent
        }?: throw Exception("AppComponent is null.")
    }

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
        setUIController()
    }

    fun setUIController(){

        // TEST: Set interface from mock
         // PRODUCTION: if no mock, get from context
            activity?.let {
                if(it is MainActivity){
                    try{
                        uiController = context as UIController
                    }catch (e: ClassCastException){
                        e.printStackTrace()
                    }

            }
        }
    }
}