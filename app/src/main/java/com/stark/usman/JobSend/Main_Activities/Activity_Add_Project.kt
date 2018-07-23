package com.stark.usman.JobSend.Main_Activities

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.stark.usman.JobSend.Realm_Objects.Realm_Project_unit
import com.stark.usman.JobSend.Utilities.realm_functions
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_project_add.*
import java.util.*
import android.widget.Toast
import com.stark.usman.JobSend.R
import java.text.SimpleDateFormat




open class Activity_Add_Project: AppCompatActivity() {
    lateinit var realm: Realm
    lateinit var realm_functions: realm_functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Add Project");


        val extras = intent.extras

        setContentView(R.layout.activity_project_add)
        Set_Listeners()
        Realm.init(this)
        try {
            realm = Realm.getDefaultInstance()


        } catch (e: Exception) {
            val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            realm = Realm.getInstance(config)
        }
        realm_functions = realm_functions()
        if (extras != null) {

            Set_Values()
            btn_delete_project.visibility=View.VISIBLE

            //The key argument here must match that used in the other activity
        }

    }

    fun Set_Listeners() {

        btn_project_date.setOnClickListener(clickListener)
        btn_start_time.setOnClickListener(clickListener)
        btn_finish_time.setOnClickListener(clickListener)
        btn_save_project.setOnClickListener(clickListener)
        btn_delete_project.setOnClickListener(clickListener)
        btn_delete_project.visibility=View.GONE
    }

    val clickListener = View.OnClickListener { view ->
        when (view.id) {

            R.id.btn_project_date -> Date_Set()
            R.id.btn_start_time -> Start_Time_Set()
            R.id.btn_finish_time -> Finish_Time_Set()
            R.id.btn_save_project -> Save_Project()
            R.id.btn_delete_project -> Delete_Project()

        }
    }

    private fun Finish_Time_Set() {

        Log.d("asdf", "asddf")
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            val minute1 = String.format("%02d", selectedMinute)
            val hour1 = String.format("%02d", selectedHour)
            tv_finish_time.setText(hour1 + ":" + minute1)
        }, hour, minute, true)//Yes 24 hour timeMinuteDays
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

    private fun Start_Time_Set() {
        Log.d("asdf", "asddf")
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener
        { timePicker, selectedHour, selectedMinute ->
            val minute1 = String.format("%02d", selectedMinute)
            val hour1 = String.format("%02d", selectedHour)
            tv_start_time.setText(hour1 + ":" + minute1)

        }, hour, minute, true)//Yes 24 hour timeMinuteDays
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()

    }

    private fun Date_Set() {

        val calendar = Calendar.getInstance()
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(this, myDateListener, mYear, mMonth, mDay)
        datePickerDialog.show()

    }

    private fun Save_Project() {

        if(et_project_name.text.length==0 || tv_project_date.text.toString()=="Project Date" || tv_start_time.text.toString()
                =="Start Time" || tv_finish_time.text.toString()=="Finish Time")
        {

            Toast.makeText(this@Activity_Add_Project,"Please Complete Values",Toast.LENGTH_LONG).show()
        }
        else if(realm_functions.time_difference(tv_start_time.text.toString(),tv_finish_time.text.toString())==false)
        {
            Toast.makeText(this@Activity_Add_Project,"Please Correct time",Toast.LENGTH_LONG).show()

        }
        else
        {
            val extras = intent.extras
            if (extras != null) {
                val result=intent.getStringExtra("id")
                val resultint:Int=Integer.parseInt(result)
                val list=realm.where(Realm_Project_unit::class.java).equalTo("id",resultint).findFirst()
                realm_functions.Delete_Project_Unit(realm,list)
                //The key argument here must match that used in the other activity

            }
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val d = formatter.parse(tv_project_date.text.toString())
            formatter.applyPattern("MM-yyyy")
            val month_name=formatter.format(d)
            val monthid=d.time
           // val today = Calendar.getInstance().time
            realm_functions.Save_Project_Unit(realm, et_project_name.text.toString(), tv_project_date.text.toString(),
                    tv_start_time.text.toString(), tv_finish_time.text.toString(),realm_functions.work_hours(
                    tv_start_time.text.toString(),tv_finish_time.text.toString()) ,et_remarks.text.toString(),month_name,monthid)
            Toast.makeText(this@Activity_Add_Project,"Entry Added Successfully"+"  "+realm_functions.
                    work_hours(tv_start_time.text.toString(),tv_finish_time.text.toString()),Toast.LENGTH_LONG).show()

            this.finish()

        }

    }

    private val myDateListener = DatePickerDialog.OnDateSetListener { arg0, arg1, arg2, arg3 ->
        val cal = Calendar.getInstance()
        cal.set(arg1, arg2, arg3)
        val dateFormat = android.text.format.DateFormat.getDateFormat(this)
        tv_project_date.setText(dateFormat.format(cal.time))
    }
    private fun Set_Values() {
        val result=intent.getStringExtra("id")
        val resultint:Int=Integer.parseInt(result)
        val list=realm.where(Realm_Project_unit::class.java).equalTo("id",resultint).findFirst()
        et_project_name.setText(list.project_name)
        tv_project_date.text=list.project_date
        tv_start_time.text=list.start_time
        tv_finish_time.text=list.finish_time
        et_remarks.setText(list.remarks)
        et_project_address.setText(list.project_address)
    }


    private fun Delete_Project() {
        val alertDialog = AlertDialog.Builder(this@Activity_Add_Project).create()
        alertDialog.setTitle("Delete Entry")
        alertDialog.setMessage("Are You Sure ?")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK")
        { dialog, which ->

            val result=intent.getStringExtra("id")
            val resultint:Int=Integer.parseInt(result)
            val list=realm.where(Realm_Project_unit::class.java).equalTo("id",resultint).findFirst()
            realm_functions.Delete_Project_Unit(realm,list)



        this.finish()
        }
        alertDialog.show();

    }
}
