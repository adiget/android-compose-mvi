package com.example.githubapp.ui.repos.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubapp.ui.repos.compose.UserRepoCard
import com.example.githubapp.ui.repos.compose.UserReposScreen
import com.example.githubapp.ui.repos.model.UiRepoData
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserReposScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun repoList_itemShown() {
        startRepoList()
        composeTestRule.onNodeWithText("repo1").assertIsDisplayed()
    }

    private fun startRepoList(onCardClick: (repoName: String) -> Unit = {}) {
        composeTestRule.setContent {
            UserReposScreen(
                repos = listOf(repoForTesting()),
                onCardClick = onCardClick,
                contentPadding = PaddingValues(all = 8.dp),
                modifier = Modifier
                )
        }
    }

    @Test
    fun repoCard_itemShown() {
        composeTestRule.setContent {
            UserRepoCard(
                userRepo = repoForTesting(),
                onCardClick = {}
            )
        }

        composeTestRule.onNodeWithText("repo1").assertIsDisplayed()
        composeTestRule.onNodeWithText("repo1 desc").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("branch1").assertDoesNotExist()
    }
}

@Composable
internal fun repoForTesting() =
    UiRepoData(
        "repo1",
        "repo1 desc",
        1,
        1,
        "branch1"
    )