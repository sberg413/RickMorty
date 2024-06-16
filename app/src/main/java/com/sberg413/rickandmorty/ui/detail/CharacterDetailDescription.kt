package com.sberg413.rickandmorty.ui.detail

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import com.sberg413.rickandmorty.ui.LoadingScreen
import com.sberg413.rickandmorty.utils.findActivity


@Composable
fun CharacterDetailDescription() {

    val viewModel: DetailViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is CharacterDetailUiState.Loading -> {
            LoadingScreen()

        }

        is CharacterDetailUiState.Success -> {
            val character = (uiState as CharacterDetailUiState.Success).character
            val location = (uiState as CharacterDetailUiState.Success).location

            val context = LocalContext.current.findActivity()
            LaunchedEffect(Unit) {
                (context as? AppCompatActivity)?.supportActionBar?.title = character.name
            }

            CharacterDetailContent(
                characterData = character,
                locationData = location
            )
        }

        is CharacterDetailUiState.Error -> {
            ShowErrorStateToast((uiState as CharacterDetailUiState.Error).message)
        }
    }
}

@Composable
fun CharacterDetailContent(characterData: Character, locationData: Location?) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CharacterImage(url = characterData.image, name = characterData.name)

            Text(
                text = characterData.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 18.dp)
                    .testTag("CharacterName")
            )



            // CharacterDetailRow(label = "Type", data = characterData.type)
            CharacterDetailRow(label = R.string.status, data = characterData.status)
            CharacterDetailRow(label = R.string.species, data = characterData.species)
            locationData?.let { location ->
                CharacterDetailRow(label = R.string.location, data = location.name)
                CharacterDetailRow(label = R.string.dimension, data = location.dimension)
            }
            // CharacterDetailRow(label = "Number of residents", data = locationData.residentCount.toString())

        }
    }
}

@Composable
private fun CharacterDetailRow(modifier: Modifier =  Modifier, @StringRes label: Int, data: String) {
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 15.dp)
            .fillMaxWidth()) {
        Text(
            text = stringResource(id = label),
            modifier = Modifier
                .weight(1f),
            textAlign =  TextAlign.End
        )
        Text(
            text = ":",
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )

        Text(
            text = data,
            modifier = Modifier
                .weight(1f)
                .testTag(stringResource(label)),
            textAlign =  TextAlign.Start
        )
    }

}

@Preview
@Composable
private fun CharacterDetailContentPreview() {
    val character = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "blah",
        gender = "Male",
        originId = "1",
        locationId = "1",
        image = "http://imageurl.com"
    )
    val location = Location(
        id = 1,
        name = "Earth",
        type = "Planet",
        dimension = "ea19",
        residents = null,
        url = "http://somelocation.com",
        created = "01/01/2023"

    )
    MaterialTheme {
        CharacterDetailContent(character, location)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CharacterImage(url: String, name: String) {
    Box(Modifier.fillMaxWidth()) {
        GlideImage(
            model = url,
            contentDescription = stringResource(R.string.character_img_content_description, name),
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .padding(10.dp)
                .align(Alignment.Center),
            loading = placeholder(R.drawable.avatar_placeholder)
        )
    }
}

@Composable
fun ShowErrorStateToast(errMsg: String) {
    val context = LocalContext.current
    errMsg.let {
        Toast.makeText(
            context,
            "ERROR: ${it}",
            Toast.LENGTH_SHORT
        ).show()
    }
}