package com.vfutia.lurk.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vfutia.lurk.subreddit.SubredditActivity
import com.vfutia.lurk.R
import com.vfutia.lurk.ui.theme.LurkInverse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchAccessToken()

        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            LurkInverse {
                Scaffold { padding ->
                    Box (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        val state: SplashState by viewModel.state.collectAsState()

                        when {
                            state.hasError -> {
                                LaunchedEffect(Unit) {
                                    snackbarHostState.showSnackbar(getString(R.string.reddit_comms_error))
                                }
                            }
                            state.fetchSuccess -> proceedToActivity()
                            else -> CircularProgressIndicator(modifier = Modifier
                                .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun fetchAccessToken() {
        scope.launch { withContext(Dispatchers.IO) { viewModel.fetchAccessToken() }.await() }
    }

    private fun proceedToActivity() {
        finish()
        startActivity(SubredditActivity.launchIntent(this@SplashActivity))
    }
}