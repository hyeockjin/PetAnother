package com.lx.project5

import android.service.autofill.UserData
import com.lx.data.WriteRegisterResponse


class AppData {
    companion object {
        var userdata: String?=null
        var careData: CareData?=null
        var filepath: String?=null
        var loginData: LoginData?=null
        var selectedItem: PetData?=null
        var memberData: MemberData?=null
        var dogData: PetData? = null
        var point: Int? = 0
        var lat: String? = null
        var lng: String? = null
        var choiceRegisterItem: ChoiceRegisterData?=null

        var careImage: String?=null
        var goIndex: Int?=null


    }
    //유진바보
}