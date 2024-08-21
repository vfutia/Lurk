package com.vfutia.lurk.subreddit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfutia.lurk.data.RedditRepository
import com.vfutia.lurk.model.ListingType
import com.vfutia.lurk.model.Subreddit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SubredditViewModel @Inject constructor (
    private val redditRepository: RedditRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SubredditState())
    val state: StateFlow<SubredditState> = _state.asStateFlow()

    fun fetchSubreddit(subreddit: String) = viewModelScope.launch(Dispatchers.Main) {
        val currentSubreddit = withContext(Dispatchers.IO) {
            redditRepository.fetchSubreddit(subreddit)
        }

        _state.update { current -> current.copy(
            subreddit = currentSubreddit,
        )}
    }

    fun fetchPage(subreddit: String?, refresh: Boolean = false) = viewModelScope.launch(Dispatchers.Main) {
        _state.update { current -> current.copy(
            isLoadingFirstLoadPage = current.posts.isEmpty(),
            isLoadingNextPage = current.posts.isNotEmpty(),
            hasLoadError = false,
            isRefreshing = refresh
        )}

        try {
            val page = withContext(Dispatchers.IO) {
                val after: String? = if (refresh) {
                    null
                } else {
                    state.value.after
                }

                if (subreddit == null) {
                    redditRepository.fetchFrontPage(listingType = ListingType.Hot, after = after)
                } else {
                    redditRepository.fetchPosts(subreddit, after = after)
                }
            }

            _state.update { current ->
                val updatedPosts = current.posts.toMutableList().apply {
                    if (refresh) { clear() }
                    addAll(page.posts)
                }

                current.copy(
                    isLoadingFirstLoadPage = false,
                    isLoadingNextPage = false,
                    hasLoadError = false,
                    isRefreshing = false,
                    posts = updatedPosts,
                    after = page.after
                )
            }
        } catch (e: Exception) {
            _state.update { current -> current.copy(hasLoadError = true)}
        }
    }
}