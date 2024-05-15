package com.example.omdbapi

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class ShowEvent : BaseActivity() {
    private lateinit var event: EventsItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_event)


        val txtTitle: TextView = findViewById(R.id.edtTitle)
        val txtYear: TextView = findViewById(R.id.edtYear)
        val txtRated: TextView = findViewById(R.id.edtRated)
        val txtReleased: TextView = findViewById(R.id.edtReleased)
        val txtLanguage: TextView = findViewById(R.id.edtLanguage)
        val txtImdbID: TextView = findViewById(R.id.edtImdbID)
        val txtRating: TextView = findViewById(R.id.edtRating)
        val txtDirector: TextView = findViewById(R.id.edtDirector)
        val txtPlot: TextView = findViewById(R.id.edtPlot)

        val imgPoster: ImageView = findViewById(R.id.edtImage)

        val extras = intent.extras
        if (extras != null) {
            currentRecord = extras.getInt("currentPosition")
        }
        event = eventsList[currentRecord]

        val imdbID = event.imdbID.toString()


        val apiURL = "https://www.omdbapi.com/?i=$imdbID&apikey=ab1fc252"


        val queue = Volley.newRequestQueue(this)

        val stringRequest = JsonObjectRequest(
            Request.Method.GET,
            apiURL,
            null,
            { response ->

                txtTitle.text = response.getString("Title")
                txtYear.text = response.getString("Year")
                txtRated.text = response.getString("Rated")
                txtReleased.text = response.getString("Released")
                txtLanguage.text = response.getString("Language")
                txtImdbID.text = response.getString("imdbID")
                txtRating.text = response.getString("imdbRating")
                txtDirector.text = response.getString("Director")
                txtPlot.text = response.getString("Plot")

                Log.i("OMDBApi", "Response is: ${response.toString()}")
            },

            {
                Log.i("OMDBApi", "Doesnt work - ${it.message}")
            }

        )
        //stringRequest.setShouldCache(false)
        queue.add(stringRequest)


        val imageLink = event.poster
        val imageRequest = ImageRequest(
            imageLink,
            Response.Listener<Bitmap> { loadedImage ->
                imgPoster.setImageBitmap(loadedImage)
            },
            0,
            0,
            ImageView.ScaleType.CENTER_INSIDE,
            Bitmap.Config.RGB_565,
            Response.ErrorListener { error ->
                Log.i("OMDBApi", "Invalid link - ${error.message}")
            }
        )

      //  imageRequest.setShouldCache(false)

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(imageRequest)

    }


    fun showAllEventsOnClick(v: View) {
        //eventsList.clear()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}