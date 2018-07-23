package com.stark.usman.stark.Realm_Objects

import io.realm.RealmObject

open class Realm_Project_unit: RealmObject()
{
    var project_name: String?=null
    var project_address: String?=null
    var project_date: String?=null
    var start_time: String?=null
    var finish_time: String?=null
    var remarks: String?=null
    var month:  String?=null
    var id: Int?=null
    var workhours:String?=null
    var monthid:Long?=null
}