package com.stark.usman.JobSend.Main_Activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.stark.usman.JobSend.R

import com.stark.usman.JobSend.Realm_Objects.Realm_User
import com.stark.usman.JobSend.Utilities.realm_functions
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_sign_up.*

open class Activity_Signup: AppCompatActivity() {

    lateinit var realm: Realm
    lateinit var database:realm_functions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btn_save_worker_dta.setOnClickListener(clickListener)
        Realm.init(this)
        try {
            realm = Realm.getDefaultInstance()

        } catch (e: Exception) {

            val config = RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build()
            realm = Realm.getInstance(config)

        }
        database = realm_functions()
        val extras = intent.extras
        if(extras!=null)
        {
            Set_Values()

        }
    }
    val clickListener = View.OnClickListener { view ->

        when (view.getId()) {
            R.id.btn_save_worker_dta -> Save_User_Data()
        }
    }

    private fun Save_User_Data() {
        if(et_company_name.text.toString()=="" || et_worker_name.text.toString()=="" )
        {
            Toast.makeText(this@Activity_Signup,"Please Enter Values",Toast.LENGTH_LONG).show()
        }
        else
        {
            val count=realm.where(Realm_User::class.java).count()
            if(count>0)
            {
                realm.executeTransaction(Realm.Transaction {
                    val list=realm.where(Realm_User::class.java).findAll()
                    list.deleteAllFromRealm()
                })


            }

            database.Save_User(realm,et_company_name.text.toString(),et_worker_name.text.toString(),et_manager_name.text.toString()
            ,et_your_id.text.toString(),et_your_vehicle_id.text.toString())
            Toast.makeText(this@Activity_Signup,"Data Saved",Toast.LENGTH_LONG).show()

            this.finish()

        }
    }
    private fun Set_Values() {
        val count=realm.where(Realm_User::class.java).count()
        if(count>0)
        {
            val list=realm.where(Realm_User::class.java).findFirst()
            et_company_name.setText(list.company_name.toString())
            et_your_id.setText(list.worker_id.toString())
            et_manager_name.setText(list.manager_name.toString())
            et_your_vehicle_id.setText(list.vehicle_id.toString())
            et_worker_name.setText(list.worker_name.toString())
        }

    }

}
