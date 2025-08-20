package com.noam.repos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.noam.repos.R
import com.noam.repos.model.TimeFrame
import com.noam.repos.ui.components.PrimaryButton

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier.padding(1.dp),
                    text = stringResource(R.string.welcome),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        letterSpacing = 0.5.sp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(R.string.intro_message),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(.3f)
                )
            }


            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = stringResource(R.string.image_git),
                    contentDescription = stringResource(id = R.string.welcome_screen_image)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                PrimaryButton(
                    text = stringResource(R.string.last_month),
                    onClickEvent = {
                        navController.navigate(Screens.MainScreen.route + "/" + TimeFrame.LastMonth.name)
//                        navController.navigate(Screens.ReposMainScreen.route + "/" + TimeFrame.LastMonth.name)
                    }
                )

                PrimaryButton(
                    text = stringResource(R.string.last_week),
                    onClickEvent = {
                        navController.navigate(Screens.MainScreen.route + "/" + TimeFrame.LastWeek.name)
//                        navController.navigate(Screens.ReposMainScreen.route + "/" + TimeFrame.LastWeek.name)
                    }
                )

                PrimaryButton(
                    text = stringResource(R.string.last_day),
                    onClickEvent = {
                        navController.navigate(Screens.MainScreen.route + "/" + TimeFrame.LastDay.name)
//                        navController.navigate(Screens.ReposMainScreen.route + "/" + TimeFrame.LastDay.name)
                    }
                )
            }
        }
    }
}