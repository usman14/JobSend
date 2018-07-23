package com.stark.usman.stark.Main_Activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.stark.usman.stark.Realm_Objects.Realm_Project_unit
import io.realm.Realm
import io.realm.RealmConfiguration
import com.stark.usman.stark.Adapters.Adapter_History_Project
import com.stark.usman.stark.R
import io.realm.Sort
import kotlinx.android.synthetic.main.rv_simple.*

open class Activity_History_Project: AppCompatActivity()
{
    lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_simple)
        Realm.init(this)
        try {
            realm= Realm.getDefaultInstance()

        }catch (e: Exception)
        {
            var config= RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            realm= Realm.getInstance(config)
        }
        val extras = intent.extras
        if (extras != null) {

            Make_List()

            //The key argument here must match that used in the other activity
        }
    }

    private fun Make_List() {
        var count=realm.where(Realm_Project_unit::class.java).count()
        if(count!=0L)
        {
            val result=intent.getStringExtra("monthid")
                    //val resultint:Int=Integer.parseInt(result)

            var list=realm.where(Realm_Project_unit::class.java).equalTo("month",result).findAllSorted("id",Sort.ASCENDING)
            rv_simple.layoutManager= LinearLayoutManager(this)
            rv_simple.adapter= Adapter_History_Project(this, list)
            //var adapter=Adapter_Edit_Project(this,list,object:Listener)

        }
        else
        {
            //Toast.makeText(this@Activity_History_Project, "No data", Toast.LENGTH_SHORT).show()

        }
    }


}
