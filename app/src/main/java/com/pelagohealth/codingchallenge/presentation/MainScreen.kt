package com.pelagohealth.codingchallenge.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pelagohealth.codingchallenge.R
import com.pelagohealth.codingchallenge.ui.theme.Pink80
import com.pelagohealth.codingchallenge.ui.theme.Purple40
import com.pelagohealth.codingchallenge.ui.theme.Purple80
import com.pelagohealth.codingchallenge.ui.theme.PurpleGrey80

// TODO: Add UI tests using Compose Testing:
// - Test loading state shows CircularProgressIndicator
// - Test error state displays error message
// - Test fact displayed when loaded
// - Test "More facts!" button triggers fetchNewFact()
// - Test buttons disabled during loading
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    val rotation by animateFloatAsState(
        targetValue = if (state.loading) 360f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "rotation"
    )

    LaunchedEffect(navController) {
        viewModel.attachNavController(navController)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Purple80.copy(alpha = 0.3f), Pink80.copy(alpha = 0.1f))
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        // Lightbulb icon with background
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Purple40),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Purple80
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.random_fact),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Purple40
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Fact card with animation
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(PurpleGrey80.copy(alpha = 0.5f))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = state,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = "fact_animation"
            ) { currentState ->
                when {
                    currentState.loading -> {
                        CircularProgressIndicator(
                            color = Purple40,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    currentState.error != null -> {
                        Text(
                            text = currentState.error ?: "",
                            color = Purple40,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                    currentState.current != null -> {
                        Text(
                            text = "\"${currentState.current?.text ?: ""}\"",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Purple40,
                            lineHeight = 26.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Main button
        Button(
            onClick = { viewModel.fetchNewFact() },
            enabled = !state.loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple40)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .rotate(rotation)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.more_facts),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // History button
        TextButton(
            onClick = { viewModel.navigateToSecondScreen() },
            enabled = !state.loading
        ) {
            Text(
                text = stringResource(R.string.show_history),
                color = Purple40,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

