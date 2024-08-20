package com.vfutia.lurk.splash

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfutia.lurk.data.RedditRepository
import com.vfutia.lurk.data.network.RedditClient
import com.vfutia.lurk.extension.putToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import kotlin.io.path.Path

@HiltViewModel
class SplashViewModel @Inject constructor (
    private val redditRepository: RedditRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    suspend fun fetchAccessToken() = viewModelScope.async(Dispatchers.Main) {
        _state.update { current -> current.copy (
            hasError = false,
            fetchSuccess = false
        )}

        try {
            val response = withContext(Dispatchers.IO) {
                redditRepository.fetchAccessToken(deviceId = UUID.randomUUID().toString())
            }

            sharedPreferences.putToken(response.accessToken)
            _state.update { current -> current.copy(fetchSuccess = true) }
        } catch (e: Exception) {
            _state.update { current -> current.copy(hasError = true) }
        }
    }
}