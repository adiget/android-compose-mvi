package com.example.githubapp.ui.users.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubapp.ui.users.model.UiUserData
import com.example.githubapp.ui.users.model.buildUiUserDataPreview
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersList(
    contentPadding: PaddingValues,
    users: List<UiUserData>,
    onRefresh: () -> Unit,
    onItemClick: (UiUserData) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val isRefreshing by remember {
            mutableStateOf(false)
        }
        val pullRefreshState = rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = { onRefresh() }
        )

        LazyColumn(
            contentPadding = contentPadding,
            modifier = Modifier.pullRefresh(pullRefreshState)
        ) {
            item {
                UsersListHeader()
            }
            items(users) { user ->
                UsersListItem(user = user, onItemClick = onItemClick)
            }
        }

        PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UsersListPreview() {
    val users = List(3) { buildUiUserDataPreview() }
    UsersList(
        users = users,
        contentPadding = PaddingValues(all = 16.dp),
        onRefresh = {},
        onItemClick = {}
    )
}