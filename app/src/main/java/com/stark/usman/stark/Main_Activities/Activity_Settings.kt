package com.stark.usman.stark.Main_Activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.stark.usman.stark.R
import com.stark.usman.stark.Realm_Objects.Realm_User
import com.stark.usman.stark.Utilities.realm_functions
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.widget.CompoundButton
import com.stark.usman.stark.AlarmReceiver
import java.util.*


open class Activity_Settings: AppCompatActivity()
{
    lateinit var realm: Realm
    lateinit var database: realm_functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setTitle("Edit Personal Settings");
////
        try {
            realm= Realm.getDefaultInstance()

        }catch (e: Exception)
        {
            val config=RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            realm=Realm.getInstance(config)
        }
        database=realm_functions()
        btn_activity_settings_change_user_data.setOnClickListener(View.OnClickListener {
            val intent= Intent(this@Activity_Settings,Activity_Signup::class.java)
            intent.putExtra("user","user")
            startActivity(intent)
        })
        switch_notification.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
            {
                start_notification(true)
            }
            else
            {
                start_notification(false)

            }
            // do something, the isChecked will be
            // true if the switch is in the On position
        })    }
    private fun Data_set() {
        val count=realm.where(Realm_User::class.java).count()
        Log.d("count",count.toString())
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
    private fun Update_data() {

        if(et_company_name.text.toString()=="" || et_worker_name.text.toString()=="" )
        {
            Toast.makeText(this@Activity_Settings,"Please Enter Values", Toast.LENGTH_LONG).show()
        }
        else
        {
            realm.executeTransaction(Realm.Transaction {
                val count=realm.where(Realm_User::class.java).findAll()
                count.deleteAllFromRealm()
            })
            database.Save_User(realm,et_company_name.text.toString(),et_worker_name.text.toString(),et_manager_name.text.toString()
                    ,et_your_id.text.toString(),et_your_vehicle_id.text.toString())
            Toast.makeText(this@Activity_Settings,"Data Saved", Toast.LENGTH_LONG).show()

            this.finish()

        }

    }

    fun start_notification(on: Boolean)  {

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val dat = Date()
        val cal_alarm = Calendar.getInstance()
        cal_alarm.time = dat
        cal_alarm.set(Calendar.HOUR_OF_DAY, 19)
        cal_alarm.set(Calendar.MINUTE, 48)
        cal_alarm.set(Calendar.SECOND, 0)

        val myIntent = Intent(applicationContext, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, myIntent, 0)

        if(on)
        {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() , 5000, pendingIntent)
            Toast.makeText(this@Activity_Settings,"Notifications Activated",Toast.LENGTH_LONG).show()
        }
        else
        {
            manager.cancel(pendingIntent)
            Toast.makeText(this@Activity_Settings,"Notifications Deactivated",Toast.LENGTH_LONG).show()

        }
    }
}