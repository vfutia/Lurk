package com.vfutia.lurk.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vfutia.lurk.data.network.RedditClient
import com.vfutia.lurk.model.ListingType
import com.vfutia.lurk.model.PostWrapper
import javax.inject.Inject

class PostPagingSource @Inject constructor (
    private val client: RedditClient,
    private val subreddit: String? = null,
    private val type: ListingType = ListingType.Hot
) : PagingSource<String, PostWrapper>() {

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, PostWrapper> {
        try {
            val response = subreddit?.let {
                client.fetchPosts(it, type.value, params.key)
            } ?: client.fetchFrontPage(type.value, params.key)

            return LoadResult.Page(
                data = response.data.children,
                prevKey = params.key,
                nextKey = response.data.after
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, PostWrapper>) = null
}