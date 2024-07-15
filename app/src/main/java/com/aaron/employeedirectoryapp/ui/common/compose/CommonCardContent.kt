package com.aaron.employeedirectoryapp.ui.common.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.aaron.employeedirectoryapp.R
import com.aaron.employeedirectoryapp.ui.theme.CustomTheme
import com.aaron.employeedirectoryapp.utilities.constants.AppConstants

@Composable
fun CommonCardContent(
    fullName: String = AppConstants.EMPTY_STRING,
    team: String = AppConstants.EMPTY_STRING,
    biography: String,
    cardImage: String = AppConstants.EMPTY_STRING,
) {

    CustomTheme {
        Column {
            CommonImageHeaderDescriptionCard(
                fullName = fullName,
                team = team,
                biography = biography,
                cardImage = cardImage
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CustomTheme.spaces.dp16)
            )
        }
    }

}

@Composable
@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
private fun PreviewCommonCardContent() {
    CustomTheme {
        CommonCardContent(
            fullName = stringResource(id = R.string.app_logo),
            biography = stringResource(id = R.string.app_header_text),
        )
    }
}