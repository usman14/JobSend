package com.stark.usman.JobSend.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stark.usman.JobSend.Realm_Objects.Realm_Project_unit
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.stark.usman.JobSend.Main_Activities.Activity_Add_Project
import com.stark.usman.JobSend.R
import kotlinx.android.synthetic.main.activity_edit_project.view.*

open class Adapter_Edit_Project(val context: Context, val list:List<Realm_Project_unit>):
        RecyclerView.Adapter<Adapter_Edit_Project.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
      val view=(LayoutInflater.from(context).inflate(R.layout.activity_edit_project, parent, false))
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.project_name?.text=list.get(position).project_name
        holder?.project_date?.text=list.get(position).project_date
        holder?.project_view?.setOnClickListener {
            (holder.project_view.setBackgroundColor(Color.RED))
            val intent = Intent(context, Activity_Add_Project::class.java)
            intent.putExtra("id",list[position].id.toString())
            Log.d("id",list[position].id.toString())
            context.startActivity(intent)
            Log.d("idnumber",list[position].id.toString())
        } // click event

    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val project_name = view.tv_project_name_rv
        val project_date = view.tv_project_date_rv
        val project_view=view.view_edit_project

    }


}



