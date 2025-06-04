import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

data class PieChartData(
    val value: Float,
    val color: Color,
    val label: String
)

@Composable
fun PieChart(
    data: List<PieChartData>,
    radius: Dp = 100.dp,
    modifier: Modifier = Modifier,
    showLabels: Boolean = true
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val total = data.sumOf { it.value.toDouble() }.toFloat()
        var startAngle = 0f

        Canvas(modifier = Modifier.size(radius * 2f)) {
            data.forEach { item ->
                val sweepAngle = 360f * (item.value / total)
                drawPieSlice(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    radius = min(size.width, size.height) / 2f
                )
                startAngle += sweepAngle
            }
        }

        if (showLabels) {
            PieChartLabels(data = data, radius = radius)
        }
    }
}

private fun DrawScope.drawPieSlice(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    radius: Float
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
    )
}

@Composable
private fun PieChartLabels(
    data: List<PieChartData>,
    radius: Dp
) {
    val total = data.sumOf { it.value.toDouble() }.toFloat()
    var startAngle = 0f

    data.forEach { item ->
        val sweepAngle = 360f * (item.value / total)
        val middleAngle = startAngle + sweepAngle / 2
        PieChartLabel(
            label = item.label,
            percentage = (item.value / total * 100).toInt(),
            angle = middleAngle,
            radius = radius,
            color = item.color
        )
        startAngle += sweepAngle
    }
}

@Composable
private fun PieChartLabel(
    label: String,
    percentage: Int,
    angle: Float,
    radius: Dp,
    color: Color,
    modifier: Modifier = Modifier
) {
    val radiusPx = with(LocalDensity.current) { radius.toPx() }
    val angleRad = Math.toRadians(angle.toDouble())
    val x = (radiusPx * 0.7 * kotlin.math.cos(angleRad)).toFloat()
    val y = (radiusPx * 0.7 * kotlin.math.sin(angleRad)).toFloat()

    Box(
        modifier = modifier
            .offset(x = x.dp, y = y.dp)
            .padding(4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                color = color,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = "$percentage%",
                color = color,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PieChartPreview() {
    MaterialTheme {
        val data = listOf(
            PieChartData(8f, Color(0xFF4CAF50), "Green"),
            PieChartData(45f, Color(0xFF2196F3), "Blue"),
            PieChartData(16f, Color(0xFFFFEB3B), "Yellow"),
            PieChartData(31f, Color(0xFFF44336), "Red")
        )

        PieChart(
            data = data,
            radius = 50.dp,
            modifier = Modifier.fillMaxSize()
        )
    }
}