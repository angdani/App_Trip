import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.ui.theme.Blue
import com.danielpons.aplicacionviajes.ui.theme.DarkGray
import com.danielpons.apptrip.model.Trip

@Composable
fun TripItem(
    trip: Trip,
    modifier: Modifier = Modifier,
    onClick: (Trip) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Blue.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = trip.name,
                style = MaterialTheme.typography.bodyLarge,
                color = DarkGray
            )

            Row {
                Text(
                    text = "Desde: ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkGray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = trip.startDate.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkGray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hasta: ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkGray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = trip.endDate.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkGray
                )
            }
        }
    }
}