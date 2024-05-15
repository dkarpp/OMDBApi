package com.example.omdbapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.omdbapi.R
import org.json.JSONException
import org.json.JSONObject

class MainActivity : BaseActivity() {
    lateinit var recyclerView : RecyclerView;
    private lateinit var eventsListAdapter : EventsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //hides keyboard after clicking search
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = currentFocus
        if (currentFocusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
    fun searchClicked(v: View) {
        eventsList.clear();
        val txtID: EditText = findViewById(R.id.edtSearchText)

        hideKeyboard();

        recyclerView = findViewById(R.id.eventsRecycler)
        eventsListAdapter = EventsAdapter(eventsList) { position ->
            toastIt("You selected position: $position")

            val intent = Intent(this, ShowEvent::class.java)
            intent.putExtra("currentPosition", position)
            startActivity(intent)

        }

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = eventsListAdapter


        eventsListAdapter.notifyDataSetChanged()

        val apiURL = "https://www.omdbapi.com/?s=" + txtID.text.toString() + "&apikey=ab1fc252"


            val queue = Volley.newRequestQueue(this)


            val stringRequest = JsonObjectRequest(
                Request.Method.GET,
                apiURL,
                null,
                { response ->
                    try {


                        val searchArray = response.getJSONArray("Search")

                        for (i in 0 until searchArray.length()) {
                            val event: JSONObject = searchArray.getJSONObject(i)

                            val title = event.getString("Title")
                            val year = event.getString("Year")
                            val imdbID = event.getString("imdbID")
                            val type = event.getString("Type")
                            val poster = event.getString("Poster")


                            val eventsItem = EventsItem(title, year, imdbID, type, poster)
                            eventsList.add(eventsItem)
                        }
                        eventsListAdapter.notifyDataSetChanged()


                        Log.i("OMDBApi", "Response is: ${response.toString()}")
                    }
                    catch (e: JSONException) {
                        Log.i("OMDBApi", "Response is: ${e.toString()}")
                        toastIt("No results found");
                    }
                },

                {
                    Log.i("OMDBApi", "Doesnt work - ${it.message}")
                }

            )
            stringRequest.setShouldCache(false)
            queue.add(stringRequest)

        }
}