package com.icoalert.ui.fragmenticoslist

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icoalert.R
import com.icoalert.databinding.IcosListFragmentBinding
import com.icoalert.ui.BaseViewModelFragment
import com.icoalert.ui.viewmodel.BaseViewModel


class IcosListFragment : BaseViewModelFragment() {

    companion object {
        val STATUS_OF_FRAGMENT = "status_of_fragment"

        fun getInstance(fragmentType: Int) : IcosListFragment {
            val fragment = IcosListFragment()
            val args = Bundle ()
            args.putInt(STATUS_OF_FRAGMENT, fragmentType)
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var listViewModel : IcosListViewModel
    lateinit var binding : IcosListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.icos_list_fragment, container, false)
        return binding.root
    }

    override fun createViewModelandBind() : BaseViewModel {
        val fragmentType = arguments.getInt(STATUS_OF_FRAGMENT)
        listViewModel = IcosListViewModel(activity.baseContext, fragmentType)
        binding.viewModel = listViewModel
        return listViewModel
    }

}