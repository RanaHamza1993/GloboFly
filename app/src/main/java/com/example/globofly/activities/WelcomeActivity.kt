package com.example.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.globofly.R
import com.example.globofly.services.DestinationService
import com.example.globofly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_welcome.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_welcome)

		// To be replaced by retrofit code
		val messageService=ServiceBuilder.buildService(DestinationService::class.java)
		val call: Call<String> =messageService.getMessages()
		call.enqueue(object : Callback<String>{
			override fun onFailure(call: Call<String>, t: Throwable) {

			}

			override fun onResponse(call: Call<String>, response: Response<String>) {
				message.text = response.body()
			}


		})

	}

	fun getStarted(view: View) {
		val intent = Intent(this, DestinationListActivity::class.java)
		startActivity(intent)
		finish()
	}
}
