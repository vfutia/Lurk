package com.vfutia.lurk.subreddit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfutia.lurk.data.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubredditViewModel @Inject constructor (
    private val redditRepository: RedditRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SubredditState())
    val state: StateFlow<SubredditState> = _state.asStateFlow()

    fun fetchPage(subreddit: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.update { current -> current.copy(
            currentSubreddit = subreddit,
            isLoadingFirstLoadPage = current.posts.isEmpty(),
            isLoadingNextPage = current.posts.isNotEmpty(),
            hasLoadError = false
        )}

        try {
            val page = redditRepository.fetchPosts(subreddit)


            _state.update { current ->
                val updatedPosts = current.posts.toMutableList().apply { addAll(page.posts) }

                current.copy(
                    isLoadingFirstLoadPage = false,
                    isLoadingNextPage = false,
                    hasLoadError = false,
                    posts = updatedPosts
                )
            }
        } catch (e: Exception) {
            _state.update { current -> current.copy(
                hasLoadError = true
            )}
        }
    }
}