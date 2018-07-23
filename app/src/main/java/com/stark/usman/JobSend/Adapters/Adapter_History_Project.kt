

package com.stark.usman.JobSend.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stark.usman.JobSend.Realm_Objects.Realm_Project_unit
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.stark.usman.JobSend.R
import kotlinx.android.synthetic.main.rv_layout_show_history.view.*

open class Adapter_History_Project(val context: Context, val list:List<Realm_Project_unit>):
        RecyclerView.Adapter<Adapter_History_Project.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view=(LayoutInflater.from(context).inflate(R.layout.rv_layout_show_history, parent, false))
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.project_name?.text=list.get(position).project_name
        holder?.project_date?.text=list.get(position).project_date
        holder?.project_start_time?.text=list.get(position).start_time
        holder?.project_finish_time?.text=list.get(position).finish_time
        if(list[position].remarks!="")
        {
            holder?.project_remarks?.text=list.get(position).remarks
        }

        Log.d("remarkds",list[position].remarks)

    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val project_date = view.tv_date_rv_show_history
        val project_name = view.tv_name_rv_show_history
        val project_start_time=view.tv_start_time_rv_show_history
        val project_finish_time=view.tv_finish_time_rv_show_history
        val project_remarks=view.tv_remarks_rv_show_history

    }


}



