package ru.tech.cookhelper.presentation.app.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.close
import ru.tech.cookhelper.presentation.ui.utils.provider.currentDialog


@Composable
fun AboutAppDialog() {
    val dialogController = LocalDialogController.current

    AlertDialog(
        title = { Text(stringResource(R.string.about_app)) },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Plank(
                    url = "https://github.com/T8RIN",
                    image = "https://avatars.githubusercontent.com/u/52178347?v=4",
                    text = "T8RIN - mobile app developer"
                )
                Spacer(Modifier.height(20.dp))
                Plank(
                    url = "https://github.com/Tannec",
                    image = "https://avatars.githubusercontent.com/u/74925839?v=4",
                    text = "Tannec - website and api developer"
                )
            }
        },
        onDismissRequest = { dialogController.close() },
        icon = { Icon(dialogController.currentDialog.icon, null) },
        confirmButton = {
            TextButton(onClick = { dialogController.close() }) {
                Text(stringResource(R.string.okay))
            }
        }
    )
}

@Composable
private fun Plank(url: String, image: String, text: String) {
    val context = LocalContext.current

    Row(
        Modifier
            .clip(CircleShape)
            .clickable {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url)
                    )
                )
            }, verticalAlignment = Alignment.CenterVertically
    ) {

        Picture(
            modifier = Modifier.size(50.dp),
            model = image
        )

        Spacer(Modifier.weight(1f))
        Text(text, textAlign = TextAlign.Center, modifier = Modifier.padding(end = 10.dp))
    }
}