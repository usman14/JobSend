

package com.stark.usman.JobSend.Adapter_Setter_Activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.stark.usman.JobSend.Adapters.Adapter_Report_One
import com.stark.usman.JobSend.R
import com.stark.usman.JobSend.Realm_Objects.Realm_Project_unit
import com.stark.usman.JobSend.Utilities.realm_functions
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import kotlinx.android.synthetic.main.rv_simple.*

open class Activity_One_Report: AppCompatActivity()
{
    lateinit var realm: Realm
    lateinit var realm_function: realm_functions
    lateinit var adapter: Adapter_Report_One

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_simple)
        setTitle(" Generate Report");

        try
        {
            realm= Realm.getDefaultInstance()
        }catch (e:Exception)
        {
            var config= RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            realm= Realm.getInstance(config)
        }
        realm_function= realm_functions()

        Set_Values()
    }

    private fun Set_Values() {
        var list=realm.where(Realm_Project_unit::class.java) .findAllSorted("monthid", Sort.DESCENDING);
        if(list.size==0)
        {
            Toast.makeText(this@Activity_One_Report, "No data to Show", Toast.LENGTH_LONG).show()
            this.finish()
        }
        else
        {

        rv_simple.layoutManager= LinearLayoutManager(this)
        adapter= Adapter_Report_One(this, realm_function.remove_duplicate(list))
            rv_simple.adapter=adapter

        }   }

    override fun onResume() {
        super.onResume()

        adapter?.notifyDataSetChanged()



    }
}