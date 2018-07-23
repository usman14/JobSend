

package com.stark.usman.stark.Adapter_Setter_Activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.stark.usman.stark.Adapters.Adapter_Edit_One
import com.stark.usman.stark.R
import com.stark.usman.stark.Realm_Objects.Realm_Project_unit
import com.stark.usman.stark.Utilities.realm_functions
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import kotlinx.android.synthetic.main.rv_simple.*

open class Activity_One_Edit: AppCompatActivity()
{
    lateinit var realm: Realm
    lateinit var realm_functions: realm_functions

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
        Set_Values()
    }

    private fun Set_Values() {
        var list=realm.where(Realm_Project_unit::class.java) .findAllSorted("monthid", Sort.DESCENDING);
        rv_simple.layoutManager= LinearLayoutManager(this)
        rv_simple.adapter= Adapter_Edit_One(this, realm_functions.remove_duplicate(list))

    }
}