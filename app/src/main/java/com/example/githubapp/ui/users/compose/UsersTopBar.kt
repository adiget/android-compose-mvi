package com.example.githubapp.ui.users.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.users_screen_top_bar_title)) }
    )
}

@Preview(showBackground = true)
@Composable
fun ReposTopBarPreview() {
    UsersTopBar()
}