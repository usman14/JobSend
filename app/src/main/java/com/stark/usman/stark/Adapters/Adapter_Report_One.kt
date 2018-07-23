package com.stark.usman.stark.Adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stark.usman.stark.Main_Activities.Activity_Generate_Report
import com.stark.usman.stark.R
import kotlinx.android.synthetic.main.rv_layout_show_history_month.view.*

open class Adapter_Report_One(val context: Context, val list:List<String>):
        RecyclerView.Adapter<Adapter_Report_One.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Viewholder {
        val view=LayoutInflater.from(context).inflate(R.layout.rv_layout_show_history_month,parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {

return list.size
    }

    override fun onBindViewHolder(holder: Viewholder?, position: Int) {

        holder?.month?.text=list[position]
        holder?.month?.setOnClickListener(View.OnClickListener {
            var intent= Intent(context, Activity_Generate_Report::class.java)
            intent.putExtra("month",list[position])
            context.startActivity(intent)
        })

    }

    class Viewholder(view: View): RecyclerView.ViewHolder(view) {
        val month=view.btn_month_name
        val layout=view.ll_rv_history_month



    }

}