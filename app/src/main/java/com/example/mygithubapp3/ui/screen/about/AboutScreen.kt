package com.example.mygithubapp3.ui.screen.about

import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mygithubapp3.R
import com.example.mygithubapp3.data.Dummy.userDetail
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (username: String) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = modifier.padding(horizontal = 64.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.about_profile_picture),
                contentDescription = userDetail.username,
                modifier = Modifier
                    .clip(CircleShape)
            )
        }
        Text(
            text = stringResource(R.string.about_name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable {
                onClickItem(context, "mailto:", R.string.about_email)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
            )
            Text(
                text = stringResource(R.string.about_email),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable {
                onClickItem(context, "https://linkedin.com/in/", R.string.about_linkedin)
            }
        ) {
            Icon(
                painter = painterResource(
                    if (isSystemInDarkTheme()) R.drawable.linkedin_logo_dark
                    else R.drawable.linkedin_logo
                ),
                contentDescription = "LinkedIn",
                modifier = modifier.scale(0.8f)
            )
            Text(
                text = stringResource(R.string.about_linkedin),
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(start = 8.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable {
                navigateToDetail(context.getString(R.string.about_github))
            }
        ) {
            Icon(
                painter = painterResource(
                    if (isSystemInDarkTheme()) R.drawable.github_logo_dark
                    else R.drawable.github_logo
                ),
                contentDescription = "GitHub",
                modifier = modifier.scale(0.8f)
            )
            Text(
                text = stringResource(R.string.about_github),
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(start = 8.dp)
            )
        }
    }
}

private fun onClickItem(
    context: Context,
    uriString: String,
    @StringRes res: Int,
) {
    val newUriString = uriString + context.getString(res)
    val uri = Uri.parse(newUriString)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}


//------------------------------------------[ preview ]---------------------------------------------

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    MyGithubApp3Theme {
        AboutScreen(navigateToDetail = {})
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun AboutScreenPreviewDark() {
    MyGithubApp3Theme {
        Surface {
            AboutScreen(navigateToDetail = {})
        }
    }
}
