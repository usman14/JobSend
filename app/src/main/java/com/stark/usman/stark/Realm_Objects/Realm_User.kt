package com.stark.usman.stark.Realm_Objects

import io.realm.RealmObject

open class Realm_User: RealmObject()
{
    var worker_name: String?=null
    var worker_id: String?=null
    var manager_name: String?=null
    var company_name: String?=null
    var vehicle_id: String?=null
}