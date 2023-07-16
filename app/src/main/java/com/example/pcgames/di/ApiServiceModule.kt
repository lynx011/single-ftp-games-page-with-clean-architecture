package com.example.pcgames.di

import com.example.pcgames.data.data_source.api_service.GameApiService
import com.example.pcgames.data.repository_impl.GameRepositoryImpl
import com.example.pcgames.domain.repository.GameRepository
import com.example.pcgames.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    @Named("GameService")
    fun provideHttpService() : OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(1,TimeUnit.MINUTES)
        .readTimeout(1,TimeUnit.MINUTES)
        .writeTimeout(1,TimeUnit.MINUTES)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(@Named("GameService")okHttpClient: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGameApiService(retrofit: Retrofit) : GameApiService =
        retrofit.create(GameApiService::class.java)

    @Provides
    @Singleton
    fun provideGameRepository(apiService: GameApiService) : GameRepository{
        return GameRepositoryImpl(apiService)
    }
}