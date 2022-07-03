package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainModalDrawerHeader(userState: UserState, onClick: () -> Unit) {
    Column(
        Modifier.clickable(
            interactionSource = MutableInteractionSource(),
            indication = null,
            onClick = onClick
        )
    ) {
        Picture(
            model = "https://sun9-76.userapi.com/s/v1/ig2/lbF4vZbkOi0zdhtU-5iXrF2YPHiwIVVZouCCGvQBb7MV7OKzhhPUg7KW4nyc7vr7SS7HVDDyV_kdPVeoPo7j8RHb.jpg?size=1620x2160&quality=95&type=album",
            modifier = Modifier
                .padding(start = 15.dp, top = 15.dp)
                .size(64.dp)
        )
        Spacer(Modifier.size(10.dp))
        Column(
            Modifier
                .height(52.dp)
                .padding(start = 15.dp)
        ) {
            Text(
                userState.user?.let { "${it.name} ${it.surname}" }.toString(),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.weight(1f))
            Text(
                "@${userState.user?.nickname}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.size(30.dp))
    }
}