package ir.sina.moviemaktab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.sina.moviemaktab.data.remote.MovieApiService
import ir.sina.moviemaktab.util.API_KEY
import ir.sina.moviemaktab.util.BASE_URL
import ir.sina.moviemaktab.util.provideApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor() = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkHttpLog(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit) = provideApi<MovieApiService>(retrofit)

}

