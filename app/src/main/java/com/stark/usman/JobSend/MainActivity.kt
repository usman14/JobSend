package com.stark.usman.JobSend

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.stark.usman.JobSend.Adapter_Setter_Activities.Activity_One_Edit
import com.stark.usman.JobSend.Adapter_Setter_Activities.Activity_One_History
import com.stark.usman.JobSend.Adapter_Setter_Activities.Activity_One_Report
import com.stark.usman.JobSend.Main_Activities.Activity_Add_Project
import com.stark.usman.JobSend.Main_Activities.Activity_Settings
import com.stark.usman.JobSend.Main_Activities.Activity_Signup
import com.stark.usman.JobSend.Realm_Objects.Realm_User
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {
        lateinit var realm: Realm
    var clicklistener= View.OnClickListener { view ->
        when(view.id)
        {
            R.id.et_project_name -> Add_Project()
            R.id.btn_project_date -> Edit_Project()
            R.id.btn_start_time -> Show_History()
            R.id.btn_finish_time -> Generate_Report()
            R.id.btn_Settings-> Settings_function()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)
        try {
            realm = Realm.getDefaultInstance()

        } catch (e: Exception) {

            val config = RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build()
            realm = Realm.getInstance(config)

        }
        val count = realm.where(Realm_User::class.java).count()
        if(count==0L)
        {
            val intent = Intent(this, Activity_Signup::class.java)
            startActivity(intent)
        }

        et_project_name.setOnClickListener(clicklistener)
        btn_project_date.setOnClickListener(clicklistener)
        btn_start_time.setOnClickListener(clicklistener)
        btn_finish_time.setOnClickListener(clicklistener)
        btn_start_time.setOnClickListener(clicklistener)
        btn_Settings.setOnClickListener(clicklistener)



    }
    private fun Generate_Report() {
        val intent = Intent(this, Activity_One_Report::class.java)
        startActivity(intent)
    }

    private fun Show_History() {
        val intent = Intent(this, Activity_One_History::class.java)
        startActivity(intent)
    }

    private fun Edit_Project() {
        val intent = Intent(this, Activity_One_Edit::class.java)
        startActivity(intent)
    }

    private fun Add_Project() {
        val intent = Intent(this, Activity_Add_Project::class.java)
        startActivity(intent)
    }
    private fun Settings_function() {
        val intent = Intent(this, Activity_Settings::class.java)
        startActivity(intent)
    }

}
