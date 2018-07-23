package com.stark.usman.stark.Main_Activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.stark.usman.stark.Realm_Objects.Realm_Project_unit
import io.realm.Realm
import io.realm.RealmConfiguration
import android.widget.Toast
import com.stark.usman.stark.Adapters.Adapter_Edit_Project
import com.stark.usman.stark.R
import io.realm.Sort
import kotlinx.android.synthetic.main.rv_simple.*

open class Activity_Edit_Project: AppCompatActivity()
{
    lateinit var realm:Realm
    var adapter: Adapter_Edit_Project?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_simple)
        setTitle("Add Project");

        Realm.init(this)
        try {
            realm=Realm.getDefaultInstance()

        }catch (e: Exception)
        {
            var config=RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            realm=Realm.getInstance(config)
        }

        Make_List()
    }

    private fun Make_List() {
        var count=realm.where(Realm_Project_unit::class.java).count()
        if(count!=0L)
        {
            val result=intent.getStringExtra("monthid")
            //val resultint:Int=Integer.parseInt(result)

            var list=realm.where(Realm_Project_unit::class.java).equalTo("month",result).findAllSorted("id", Sort.ASCENDING)
            rv_simple.layoutManager=LinearLayoutManager(this)
            adapter= Adapter_Edit_Project(this, list)
            rv_simple.adapter=adapter


        }
        else
        {
            Toast.makeText(this@Activity_Edit_Project, "No data", Toast.LENGTH_SHORT).show()

        }
        }

    override fun onResume() {
        super.onResume()

            adapter?.notifyDataSetChanged()



    }

    }
