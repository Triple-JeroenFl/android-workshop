package com.wearetriple.exercises.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wearetriple.exercises.model.response.PokemonListItemResponse
import com.wearetriple.exercises.network.service.PokemonService

class PokedexPagingSource(
    private val service: PokemonService
) : PagingSource<Int, PokemonListItemResponse>() {

    companion object {
        const val PAGE_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonListItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_SIZE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListItemResponse> {
        val offset = params.key ?: 0
        return try {
            val response = service.getPokemonList(offset = offset, limit = PAGE_SIZE)
            LoadResult.Page(
                data = response.results,
                prevKey = if (response.previous != null) offset - PAGE_SIZE else null,
                nextKey = if (response.next != null) offset + PAGE_SIZE else null
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}