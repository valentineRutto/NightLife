package com.valentinerutto.nightlife.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.valentinerutto.nightlife.data.network.NightlifeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepository(
    private val api: NightlifeApiService
) {

}