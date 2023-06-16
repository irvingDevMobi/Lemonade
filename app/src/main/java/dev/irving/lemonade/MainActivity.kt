package dev.irving.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.irving.lemonade.ui.theme.Lemonade
import dev.irving.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeScreen()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LemonadeScreen(
    modifier: Modifier = Modifier
) {
    var taps by remember { mutableStateOf(0) }
    var minLemonTaps by remember {
        mutableStateOf((2..4).random())
    }
    val uiModel = getUiState(taps, minLemonTaps)
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            instructionResource = uiModel.instructionResource,
            imageResource = uiModel.imageResource,
            descriptionResource = uiModel.imageDescriptionResource
        ) {
            if (taps > 1 + minLemonTaps) {
                taps = 0
                minLemonTaps = (2..4).random()
            } else {
                taps++
            }
        }
    }
}

@Composable
fun Card(
    @StringRes instructionResource: Int,
    @DrawableRes imageResource: Int,
    @StringRes descriptionResource: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Lemonade)
        ) {
            Image(
                modifier = Modifier.padding(32.dp),
                painter = painterResource(id = imageResource),
                contentDescription = stringResource(id = descriptionResource),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = instructionResource),
            fontSize = 18.sp
        )
    }
}

private fun getUiState(
    totalTaps: Int,
    minLemonTaps: Int
): LemonadeUiModel = when (totalTaps) {
    in 1..minLemonTaps -> LemonadeUiModel(
        instructionResource = R.string.step_tap_lemon,
        imageResource = R.drawable.lemon_squeeze,
        imageDescriptionResource = R.string.lemon
    )
    minLemonTaps + 1 -> LemonadeUiModel(
        instructionResource = R.string.step_tap_lemonade,
        imageResource = R.drawable.lemon_drink,
        imageDescriptionResource = R.string.glass_of_lemonade
    )
    minLemonTaps + 2 -> LemonadeUiModel(
        instructionResource = R.string.step_tap_glass,
        imageResource = R.drawable.lemon_restart,
        imageDescriptionResource = R.string.empty_glass
    )
    else -> LemonadeUiModel(
        instructionResource = R.string.step_tap_lemon_tree,
        imageResource = R.drawable.lemon_tree,
        imageDescriptionResource = R.string.lemon_tree
    )
}
