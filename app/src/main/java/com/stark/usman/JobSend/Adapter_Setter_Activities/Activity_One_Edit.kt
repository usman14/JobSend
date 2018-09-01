

package com.stark.usman.JobSend.Adapter_Setter_Activities

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.stark.usman.JobSend.Adapters.Adapter_Edit_One
import com.stark.usman.JobSend.Realm_Objects.Realm_Project_unit
import com.stark.usman.JobSend.Utilities.realm_functions
import com.stark.usman.JobSend.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.rv_simple.*
import java.util.*
import java.util.concurrent.TimeUnit

open class Activity_One_Edit: AppCompatActivity()
{
    lateinit var realm: Realm
    lateinit var realm_functions: realm_functions
    lateinit var adapter: Adapter_Edit_One

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Edit Project");

        setContentView(R.layout.rv_simple)
        try
        {
            realm= Realm.getDefaultInstance()
        }catch (e:Exception)
        {
            var config= RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
            realm= Realm.getInstance(config)
        }
        realm_functions= realm_functions()
        Set_Values_rx()
    }

    private fun Set_Values() {
        var list=realm.where(Realm_Project_unit::class.java) .findAllSorted("monthid", Sort.DESCENDING);
        Log.d("camesss",list.size.toString())
        if(list.size==0)
        {
            Toast.makeText(this@Activity_One_Edit, "No data to Show", Toast.LENGTH_LONG).show()
            this.finish()
        }
        else
        {
            rv_simple.layoutManager= LinearLayoutManager(this)
            adapter= Adapter_Edit_One(this, realm_functions.remove_duplicate(list))
            rv_simple.adapter=adapter
        }



    }
    private fun Set_Values_rx() {

        getobservable()
                .subscribe(getobserver())

    }

    private fun getobserver(): Observer<RealmResults<Realm_Project_unit>> {
        var a=this
        return object : Observer<RealmResults<Realm_Project_unit>>

        {
            override fun onSubscribe(d: Disposable) {

                Log.d("rx",d.isDisposed.toString())
            }

            override fun onError(e: Throwable) {
                Log.d("rx",e.toString())
            }

            override fun onComplete() {
                Log.d("rs", "oncomplete")

            }

            override fun onNext(t: RealmResults<Realm_Project_unit>) {

                Log.d("rs", "next")
                if(t.size==0)
                {
                    Toast.makeText(this@Activity_One_Edit, "No data to Show", Toast.LENGTH_LONG).show()
                    a.finish()
                }
                else
                {
                    Log.d("rs", "next big")

                    rv_simple.layoutManager= LinearLayoutManager(a)
                    adapter= Adapter_Edit_One(a, realm_functions.remove_duplicate(t))
                    rv_simple.adapter=adapter
                }

            }

        }

    }

    override fun onResume() {
        super.onResume()

        adapter?.notifyDataSetChanged()

    }

    open fun getobservable ():  Observable<RealmResults<Realm_Project_unit>>
    {
        return Observable.create<RealmResults<Realm_Project_unit>> { e ->
            var list=realm.where(Realm_Project_unit::class.java) .findAllSorted("monthid", Sort.DESCENDING);
            e.onNext(list)
            e.onComplete()
        };
    }
}