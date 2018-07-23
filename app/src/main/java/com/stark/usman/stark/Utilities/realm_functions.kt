package com.stark.usman.stark.Utilities

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.stark.usman.stark.Realm_Objects.Realm_Project_unit
import com.stark.usman.stark.Realm_Objects.Realm_User
import io.realm.Realm
import io.realm.RealmConfiguration
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

open class realm_functions: Activity()
{
   open fun Save_User(realm: Realm,companyname:String,workername: String,managername:String, workerid:String,vehicleid:String)
    {
       var realm = realm
        try {
            realm= Realm.getDefaultInstance()

        }catch (e:Exception)
        {
            val config=RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            realm=Realm.getInstance(config)
        }
        realm.executeTransaction { realm ->
           val Realm_user=realm.createObject(Realm_User::class.java)
            Realm_user.worker_id=workerid
            Realm_user.worker_name=workername
            Realm_user.manager_name=managername
            Realm_user.company_name=companyname
            Realm_user.vehicle_id=vehicleid

        }
    }

    open fun Save_Project_Unit(realm: Realm,project_name:String,project_date: String,start_time: String, finish_time: String,
    workhours:String,remarks:String,month:String, monthid:Long)
    {
        var realm = realm
        realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm ->
            val realm_project_unit=realm.createObject(Realm_Project_unit::class.java)
            realm_project_unit.project_name=project_name
            realm_project_unit.project_date=project_date
            realm_project_unit.start_time=start_time
            realm_project_unit.finish_time=finish_time
            realm_project_unit.remarks=remarks
            realm_project_unit.month=month
            realm_project_unit.monthid=monthid
            realm_project_unit.workhours=workhours
            val currentIdNum = realm.where(Realm_Project_unit::class.java).max("id")
            var nextid:Int?=null
            if(currentIdNum==null)
            {
                nextid=1
            }
            else
            {
                nextid = currentIdNum.toInt() + 1
            }
            realm_project_unit.id=nextid
            Log.d("log","datasaved with id  "+nextid)

        }

    }
    open fun Delete_Project_Unit(realm: Realm, list:Realm_Project_unit)
    {
       var realm1=realm
        realm1=Realm.getDefaultInstance()
        realm1.executeTransaction{realm1 ->
            list.deleteFromRealm()
        }
    }

    open fun time_difference(start_time: String,finish_time: String):Boolean
    {
        val formatter = SimpleDateFormat("hh:mm")
        val start=formatter.parse(start_time)
        val finish=formatter.parse(finish_time)
        val diff=finish.time-start.time
        if(diff>0)
        {
            return true
        }
        else
        {
            return false
        }
    }
    open fun work_hours(start_time: String,finish_time: String):String
    {
        val formatter = SimpleDateFormat("hh:mm")
        val start=formatter.parse(start_time)
        val finish=formatter.parse(finish_time)
        val diff=finish.time-start.time
        val hours = (diff / (1000 * 60 * 60)) as Long
        val mins = (diff / (1000 * 60)) as Long % 60

        val minute1 = String.format("%02d", mins)
        val hour1 = String.format("%02d", hours)
        val timeinstring="Hours : "+hour1 + "  Min  :"+ minute1
        return timeinstring
    }
    open fun remove_duplicate(list: List<Realm_Project_unit>): ArrayList<String>
    {
        var liststring= ArrayList<String>()
        var duplicate_free_list = ArrayList<String>()


        var i = 0
        val n = list.size
        while (i < n) {
            val c:String?= list[i].month
            if(c!=null)
            {
                liststring.add(c)
                i++
            }
            for (abc in liststring) {
                if (!duplicate_free_list.contains(abc)) {
                    duplicate_free_list.add(abc)
                }
            }

        }
    return duplicate_free_list }
}