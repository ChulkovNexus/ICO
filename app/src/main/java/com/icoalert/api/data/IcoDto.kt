package com.icoalert.api.data

import android.databinding.BaseObservable
import java.io.Serializable

class IcoDto : BaseObservable(), Serializable {

    var days_left : String = ""
    var name: String = ""
    var token: String = ""
    var about: String = ""
    var ico_status: Int = 0
    var pre_ico_status : Int = 0
    var rating : Float = 0f
    var url : String = ""
    var logo : String = ""
    var country : String = ""


    fun getDaysLeft() : String {
        return days_left
    }

    class IcosList : ArrayList<ArrayList<IcoDto>>()
    class IcosListByStatus : ArrayList<IcoDto>()
}