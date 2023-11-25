package com.example.githubapp.ui.users.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubapp.ui.common.RoundedImage
import com.example.githubapp.R
import com.example.githubapp.ui.users.model.UiUserData
import com.example.githubapp.ui.users.model.buildUiUserDataPreview

@Composable
fun UsersListItem(
    user: UiUserData,
    onItemClick: (UiUserData) -> Unit
) {
    val paddingXXSmall = dimensionResource(id = R.dimen.padding_xxsmall)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val avatarSize = dimensionResource(id = R.dimen.avatar_size_medium)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(user)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            RoundedImage(
                url = user.avatarUrl,
                placeholder = R.drawable.avatar_placeholder,
                modifier = Modifier
                    .size(avatarSize)
                    .padding(end = paddingMedium)
            )
            Column {
                Text(
                    text = user.userId,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(paddingXXSmall))

                Text(
                    text = user.htmlUrl,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Divider(
            modifier = Modifier.padding(start = paddingMedium, end = paddingMedium)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UsersListItemPreview() {
    UsersListItem(user = buildUiUserDataPreview(), onItemClick = {})
}