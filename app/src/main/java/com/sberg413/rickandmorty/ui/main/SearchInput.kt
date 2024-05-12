package com.sberg413.rickandmorty.ui.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sberg413.rickandmorty.R


@Composable
fun CharacterSearchInput(textState: String, modifier: Modifier = Modifier, onValChange: (String) -> Unit, onSearch: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField (
        value = textState,
        onValueChange = { onValChange(it) },
        label = { Text(stringResource(id = R.string.search_hint)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(textState)  // Invoke the search action with the current text
                keyboardController?.hide()  // Dismiss the keyboard
            }
        ),
        modifier = modifier.fillMaxWidth()
    )
}


@Preview(showBackground = true)
@Composable
fun CharacterSearchInputPreview() {
    CharacterSearchInput(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
        textState = "Sanchez",
        onValChange = {},
        onSearch = {})
}
