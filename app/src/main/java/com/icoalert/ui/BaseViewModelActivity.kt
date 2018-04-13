package com.icoalert.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.icoalert.ui.viewmodel.BaseViewModel


abstract class BaseViewModelActivity : AppCompatActivity() {

    lateinit var viewModel : BaseViewModel
    abstract fun createViewModel() : BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}