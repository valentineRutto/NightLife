package com.valentinerutto.nightlife.di

import com.google.gson.Gson
import com.valentinerutto.nightlife.MyApplication
import com.valentinerutto.nightlife.data.EventRepository
import com.valentinerutto.nightlife.data.local.NightlifeDatabase
import com.valentinerutto.nightlife.data.network.NightlifeApiService
import com.valentinerutto.nightlife.data.network.RetrofitClient
import com.valentinerutto.nightlife.data.network.RetrofitClient.createOkClient
import com.valentinerutto.nightlife.ui.EventDetailsViewModel
import com.valentinerutto.nightlife.ui.EventListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module{

    single { MyApplication.INSTANCE }
    single { Gson() }
    viewModel { EventListViewModel(get()) }
    single { EventRepository(get(), get()) }
viewModel{ EventDetailsViewModel(get()) }
    single { NightlifeDatabase.getDatabase(context = androidContext()) }


}
val databaseModule = module {
    single { get<NightlifeDatabase>().eventDao() }
}

fun Scope.database() = get<NightlifeDatabase>()

val networkingModule = module{
    single { RetrofitClient.provideOkHttpClient() }
    single{ RetrofitClient.provideRetrofit(RetrofitClient.BASE_URL,get()) }

    single { createOkClient() }

    single {
        get<Retrofit>().create(NightlifeApiService::class.java)
    }

}
