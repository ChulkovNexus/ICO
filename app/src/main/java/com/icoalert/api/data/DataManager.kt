package com.icoalert.api.data

import rx.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton


@Singleton

class DataManager{
    private val icos = ArrayList<ArrayList<IcoDto>>()
    val isosRequestedBehavour = BehaviorSubject.create(false)
    var searchRequest: String = ""
    var isPreico = false

    init {
        icos.add(ArrayList())
        icos.add(ArrayList())
    }

    fun getListByStatus(status:Int) : ArrayList<IcoDto> {
        return icos[status - 1]
    }

    companion object {
        val ICO_STATUS_ACTIVE = 1
        val ICO_STATUS_UPCOMING = 2
    }

}
