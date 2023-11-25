package com.example.githubapp.ui.prs.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubapp.R
import com.example.githubapp.presentation.views.views.UserView
import com.example.githubapp.ui.common.CircularProgressBar
import com.example.githubapp.ui.common.GitHubAppTopAppBar
import com.example.githubapp.ui.prs.PrsContract
import com.example.githubapp.ui.prs.PrsViewModel
import com.example.githubapp.ui.prs.model.UiPrData
import com.example.githubapp.ui.repos.compose.UserReposScreen
import com.example.githubapp.ui.theme.GithubAppTheme

@Composable
fun PullRequestsScreen(
    viewModel: PrsViewModel = hiltViewModel(),
    onNavUp: () -> Unit,
    modifier: Modifier
) {
    val prsUiState: PrsContract.PrsUiState by viewModel.viewState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            GitHubAppTopAppBar(
                topAppBarText = stringResource(id = R.string.pull_requests),
                onNavUp = onNavUp,
            )
        },
        content = { contentPadding ->
            when(prsUiState){
                PrsContract.PrsUiState.Loading -> CircularProgressBar()

                PrsContract.PrsUiState.Error -> {}

                is PrsContract.PrsUiState.Success ->
                    PullRequestsScreen(
                        pullRequests = (prsUiState as PrsContract.PrsUiState.Success).prs,
                        contentPadding = contentPadding,
                        modifier = modifier.safeDrawingPadding()
                    )
            }
        }
    )
}

@Composable
fun PullRequestsScreen(
    pullRequests: List<UiPrData>,
    contentPadding: PaddingValues,
    modifier: Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(pullRequests){ pullRequest: UiPrData ->
            PullRequestCard(
                modifier = Modifier.padding(vertical = 8.dp),
                pullRequest = pullRequest
            )
        }
    }
}

@Composable
fun PullRequestCard(
    pullRequest: UiPrData,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = modifier.fillMaxWidth(),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avtar(imageUrl = pullRequest.user.profilePic)

                Text(
                    text = pullRequest.prTitle,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = pullRequest.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                )
            }

            Row(
                modifier = modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painterResource(id = R.drawable.git_pull_request ),
                    contentDescription = "Image",
                    modifier = Modifier
                )

                Text(
                    text = pullRequest.closedAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    modifier = modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = pullRequest.user.userName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
private fun Avtar(
    imageUrl: String,
    lightTheme: Boolean = LocalContentColor.current.luminance() < 0.5f,
) {
    val assetId = if (lightTheme) {
        R.drawable.baseline_person_24
    } else {
        R.drawable.ic_github_logo_dark
    }

    CoilImage(
        imageUrl,
        assetId,
        R.string.user_profile_photo
    )
}

@Composable
fun CoilImage(
    imageUrl: String,
    defaultImageRes: Int,
    contentDescription: Int
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(defaultImageRes),
        contentDescription = stringResource(contentDescription),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
    )
}

@Preview
@Composable
fun PullRequestCardPreview(){
    GithubAppTheme {
        PullRequestCard(
            UiPrData(
                id = -1,
                prTitle = "Title",
                prDesc = "Description",
                user = UserView(
                    userName = "User Name"
                ),
                closedAt = "01 Aug 2023",
                createdAt = "25 Jul 2023"
            )
        )
    }
}

@Preview
@Composable
fun PullRequestsScreenPreview() {
    GithubAppTheme {
        UserReposScreen(
            onNavUp = {},
            onCardClick = {},
            modifier = Modifier
        )
    }
}