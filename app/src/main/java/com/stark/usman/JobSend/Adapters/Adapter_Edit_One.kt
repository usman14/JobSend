package com.stark.usman.JobSend.Adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stark.usman.JobSend.Main_Activities.Activity_Edit_Project
import com.stark.usman.JobSend.R
import kotlinx.android.synthetic.main.rv_layout_show_history_month.view.*

open class Adapter_Edit_One(val context: Context, val list:List<String>):
        RecyclerView.Adapter<Adapter_Edit_One.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Viewholder {
        val view=LayoutInflater.from(context).inflate(R.layout.rv_layout_show_history_month,parent, false)
        val asdf=null;
        val asdf1=null;
        return Viewholder(view)
    }

    override fun getItemCount(): Int {

return list.size
    }

    override fun onBindViewHolder(holder: Viewholder?, position: Int) {

        holder?.month?.text=list[position]
        holder?.month?.setOnClickListener(View.OnClickListener {
            var intent= Intent(context, Activity_Edit_Project::class.java)
            intent.putExtra("monthid",list[position])
            context.startActivity(intent)
        })

    }

    class Viewholder(view: View): RecyclerView.ViewHolder(view) {
        val month=view.btn_month_name
        val layout=view.ll_rv_history_month



    }

}