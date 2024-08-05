package com.vfutia.lurk.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfutia.lurk.data.RedditRepository
import com.vfutia.lurk.subreddit.SubredditState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val redditRepository: RedditRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> = _state.asStateFlow()

    fun addFavorite(subreddit: String) = viewModelScope.launch(Dispatchers.IO) {
        val favorites = redditRepository.addFavorite(subreddit)
        _state.update { current -> current.copy(favorites = favorites) }
    }

    fun deleteFavorite(subreddit: String) = viewModelScope.launch(Dispatchers.IO) {
        val favorites = redditRepository.deleteFavorite(subreddit)
        _state.update { current -> current.copy(favorites = favorites) }
    }

    fun fetchFavorites() = viewModelScope.launch(Dispatchers.IO) {
        val favorites = redditRepository.getFavorites()
        _state.update { current -> current.copy(favorites = favorites) }
    }
}