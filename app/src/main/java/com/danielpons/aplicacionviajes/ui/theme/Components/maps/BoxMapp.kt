package com.danielpons.aplicacionviajes.ui.theme.Components.maps

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.GoogleMap
import androidx.compose.ui.platform.LocalInspectionMode

@Composable
fun SearchPointsOfInterestMap(onZoneSelected: (latitude: Double, longitude: Double) -> Unit) {
    val isInPreview = LocalInspectionMode.current
    Box(modifier = Modifier.fillMaxSize()) {
        if (isInPreview) {
            // Marcador de posición para el preview
            Text(
                text = "Vista previa del mapa",
                modifier = Modifier.fillMaxSize(),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            // Mapa real
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                onMapClick = { latLng ->
                    onZoneSelected(latLng.latitude, latLng.longitude)
                }
            )
        }
    }
}


@Composable
fun SelectedZoneDetails(latitude: Double, longitude: Double, placeName: String?) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Zona seleccionada:", style = MaterialTheme.typography.titleMedium)
        Text(text = "Latitud: $latitude", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Longitud: $longitude", style = MaterialTheme.typography.bodyMedium)
        placeName?.let {
            Text(text = "Nombre del lugar: $it", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun SavePointsOfInterest(
    pointsOfInterest: MutableList<Pair<Double, Double>>,
    onSave: (latitude: Double, longitude: Double) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Puntos de interés guardados:", style = MaterialTheme.typography.titleMedium)
        pointsOfInterest.forEach { (latitude, longitude) ->
            Text(text = "Lat: $latitude, Lon: $longitude", style = MaterialTheme.typography.bodyMedium)
        }
        Button(onClick = {
            // Lógica para guardar un nuevo punto
            val newPoint = Pair(0.0, 0.0) // Reemplazar con datos reales
            pointsOfInterest.add(newPoint)
            onSave(newPoint.first, newPoint.second)
        }) {
            Text(text = "Guardar nuevo punto")
        }
    }
}

@Composable
fun PointsOfInterestScreen() {
    var selectedLatitude by remember { mutableStateOf<Double?>(null) }
    var selectedLongitude by remember { mutableStateOf<Double?>(null) }
    var placeName by remember { mutableStateOf<String?>(null) }
    val pointsOfInterest = remember { mutableStateListOf<Pair<Double, Double>>() }

    Column(modifier = Modifier.fillMaxSize()) {
        // Mapa para buscar puntos de interés
        SearchPointsOfInterestMap { latitude, longitude ->
            selectedLatitude = latitude
            selectedLongitude = longitude
            placeName = "Lugar seleccionado" // Aquí puedes integrar una API para obtener el nombre real
        }

        // Detalles de la zona seleccionada
        selectedLatitude?.let { lat ->
            selectedLongitude?.let { lon ->
                SelectedZoneDetails(latitude = lat, longitude = lon, placeName = placeName)
            }
        }

        // Guardar puntos de interés
        SavePointsOfInterest(pointsOfInterest) { latitude, longitude ->
            // Lógica adicional al guardar, si es necesario
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewPointsOfInterestScreen() {
    PointsOfInterestScreen()
}

