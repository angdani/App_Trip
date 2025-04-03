package com.danielpons.aplicacionviajes.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielpons.aplicacionviajes.data.model.Trip
import com.danielpons.aplicacionviajes.data.repository.TripRepository

class TripViewModel : ViewModel() {

    private val _trips = mutableListOf<Trip>()

    val trips : List<Trip> get() = _trips


    fun addTrip(trip: Trip) {
        _trips.add(trip)
    }

    var allTrips: List<Trip>
        get() = _trips
        set(value)  {
            _trips.clear()
            _trips.addAll(value)
        }


    }

