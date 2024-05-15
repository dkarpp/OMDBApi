package com.example.omdbapi

class EventsItem (var title: String, var year: String, var imdbID: String, var type: String, var poster: String) {

    fun toCSV(): String {
        return "$title,$year,$imdbID,$type,$poster"
    }

    override fun toString(): String {
        return "$title : $imdbID"
    }
}