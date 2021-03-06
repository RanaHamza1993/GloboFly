package com.example.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.example.globofly.R
import com.example.globofly.models.Destination
import com.example.globofly.services.DestinationService
import com.example.globofly.services.ServiceBuilder
import com.smartherd.globofly.helpers.SampleData
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_destiny_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destiny_detail)

        setSupportActionBar(detail_toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            initDeleteButton(id)
        }
    }

    private fun loadDetails(id: Int) {


        // To be replaced by retrofit code
        val destination = ServiceBuilder.buildService(DestinationService::class.java)
        val call: Call<Destination> = destination.getDestination(id)
        call.enqueue(object : Callback<Destination> {
            override fun onFailure(call: Call<Destination>, t: Throwable) {

            }

            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                val destination = response.body()
                destination?.let {
                    et_city.setText(destination.city)
                    et_description.setText(destination.description)
                    et_country.setText(destination.country)

                    (collapsing_toolbar).title = destination.city
                }
            }

        })
//		val destination = SampleData.getDestinationById(id)
//
//		destination?.let {
//			et_city.setText(destination.city)
//			et_description.setText(destination.description)
//			et_country.setText(destination.country)
//
//			(collapsing_toolbar).title = destination.city
//		}
    }

    private fun initUpdateButton(id: Int) {

        btn_update.setOnClickListener {

            val city = et_city.text.toString()
            val description = et_description.text.toString()
            val country = et_country.text.toString()

            // To be replaced by retrofit code
            val updateService=ServiceBuilder.buildService(DestinationService::class.java)

//            val destination = Destination()
//            destination.id = id
//            destination.city = city
//            destination.description = description
//            destination.country = country
           val updateCall: Call<Destination> = updateService.updateDestination(id,city,description,country)
            updateCall.enqueue(object : Callback<Destination> {
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                   if(response.isSuccessful)
                    finish()
                    Toasty.success(this@DestinationDetailActivity,"Destination updated successfully", Toast.LENGTH_SHORT,true).show()
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                }

            })
           // SampleData.updateDestination(destination);
           // Move back to DestinationListActivity
        }
    }

    private fun initDeleteButton(id: Int) {

        btn_delete.setOnClickListener {

            // To be replaced by retrofit code
            val deleteService=ServiceBuilder.buildService(DestinationService::class.java)
            val deleteRequest=deleteService.deleteDestination(id)
            deleteRequest.enqueue(object :Callback<Unit>{
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {

                    if(response.isSuccessful)
                        Toasty.success(this@DestinationDetailActivity,"Destination deleted successfully",Toasty.LENGTH_SHORT,true).show()
                    finish()
                }

            })
            //SampleData.deleteDestination(id)
            finish() // Move back to DestinationListActivity
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}
