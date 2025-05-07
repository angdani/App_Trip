import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.apptrip.model.Trip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TripViewModel(private val repository: TripRepository) : ViewModel() {

    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchTrips() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _trips.value = repository.getAllTrips()
            } catch (e: Exception) {
                _error.value = "Error al cargar los viajes"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun addTrip(trip: Trip): Trip {
        return repository.addTrip(trip)
    }
}
