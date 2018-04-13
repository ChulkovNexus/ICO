package com.icoalert.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.icoalert.ui.viewmodel.BaseViewModel


abstract class BaseViewModelFragment : Fragment() {

    lateinit var viewModel : BaseViewModel

    abstract fun createViewModelandBind(): BaseViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =  createViewModelandBind()
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