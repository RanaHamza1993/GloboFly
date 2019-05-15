package com.example.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.globofly.R
import com.example.globofly.helpers.DestinationAdapter
import com.example.globofly.models.Destination
import com.example.globofly.services.DestinationService
import com.example.globofly.services.ServiceBuilder
import com.smartherd.globofly.helpers.SampleData
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_destiny_list.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DestinationListActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_list)

		setSupportActionBar(toolbar)
		toolbar.title = title

		fab.setOnClickListener {
			val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onResume() {
		super.onResume()

		loadDestinations()
	}

	private fun loadDestinations() {

        // To be replaced by retrofit code
		val destinationService: DestinationService =ServiceBuilder.buildService(DestinationService::class.java)
        val filter=HashMap<String,String>()
        filter["country"]="India"
		val requestCall: Call<List<Destination>> =destinationService.getDestinationList(filter)
		requestCall.enqueue(object:Callback, retrofit2.Callback<List<Destination>> {
			override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
			}

			override fun onResponse(call: Call<List<Destination>>, response: Response<List<Destination>>) {
				if(response.isSuccessful) {

					val result: List<Destination>? =response.body()
					destiny_recycler_view.adapter = DestinationAdapter(result!!)
				}else if(response.code()==400){
					Toasty.error(this@DestinationListActivity,"", Toast.LENGTH_SHORT,true).show()
				}

			}

		})
		//destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)
    }
}
