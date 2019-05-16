package com.example.globofly.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.globofly.R
import com.example.globofly.models.Destination
import com.example.globofly.services.DestinationService
import com.example.globofly.services.ServiceBuilder
import com.smartherd.globofly.helpers.SampleData
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_destiny_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_create)

		setSupportActionBar(toolbar)
		val context = this

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		btn_add.setOnClickListener {
			val newDestination = Destination()
			newDestination.city = et_city.text.toString()
			newDestination.description = et_description.text.toString()
			newDestination.country = et_country.text.toString()

			val destinationService: DestinationService =ServiceBuilder.buildService(DestinationService::class.java)
			val callDestination=destinationService.addDestination(newDestination)
			callDestination.enqueue(object : Callback<Destination> {
				override fun onFailure(call: Call<Destination>, t: Throwable) {

				}

				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
					if(response.isSuccessful)
						finish()
					Toasty.success(this@DestinationCreateActivity,"Destination added successfully", Toast.LENGTH_SHORT,true).show()
				}

			})
			// To be replaced by retrofit code
			SampleData.addDestination(newDestination)
            finish() // Move back to DestinationListActivity
		}
	}
}
