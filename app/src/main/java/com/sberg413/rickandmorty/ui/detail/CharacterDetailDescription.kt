package com.sberg413.rickandmorty.ui.detail

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location


@Composable
fun CharacterDetailDescription(viewModel: DetailViewModel) {
    val character by viewModel.characterData.collectAsState()
    val location by viewModel.locationData.collectAsState()
    if (character != null && location != null) {
        CharacterDetailContent(characterData = character!!, locationData = location!!)
    }
}

@Composable
private fun CharacterDetailContent(characterData: Character, locationData: Location) {
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
//                color = MaterialTheme.colorScheme.surfaceVariant,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 18.dp)
            )


            // CharacterDetailRow(label = "Type", data = characterData.type)
            CharacterDetailRow(label = "Status", data = characterData.status)
            CharacterDetailRow(label = "Species", data = characterData.species)
            CharacterDetailRow(label = "Location", data = locationData.name)
            CharacterDetailRow(label = "Dimension", data = locationData.dimension)
            // CharacterDetailRow(label = "Number of residents", data = locationData.residentCount.toString())

        }
    }
}

@Composable
private fun CharacterDetailRow(label: String, data: String) {
    Row(
        Modifier
            .padding(horizontal = 60.dp, vertical = 15.dp)
            .fillMaxWidth()) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textAlign =  TextAlign.Start
        )

        Text(
            text = data,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textAlign =  TextAlign.End
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