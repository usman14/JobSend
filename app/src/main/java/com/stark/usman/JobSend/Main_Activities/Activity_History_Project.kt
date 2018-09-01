package com.stark.usman.JobSend.Main_Activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.stark.usman.JobSend.Realm_Objects.Realm_Project_unit
import io.realm.Realm
import io.realm.RealmConfiguration
import com.stark.usman.JobSend.Adapters.Adapter_History_Project
import com.stark.usman.JobSend.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.internal.operators.observable.ObservableFromArray
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.rv_simple.*
import java.util.*

open class Activity_History_Project: AppCompatActivity()
{
    lateinit var realm: Realm
    lateinit var adapter: Adapter_History_Project


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
            adapter= Adapter_History_Project(this, list)
            rv_simple.adapter=adapter
            //var adapter=Adapter_Edit_Project(this,list,object:Listener)

        }
        else
        {
            Toast.makeText(this@Activity_History_Project, "No data", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()

    }

}
