package ir.sina.moviemaktab.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.sina.moviemaktab.data.local.db.MovieDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) =
        Room.databaseBuilder(application, MovieDatabase::class.java, "movie_database")
            /**
             * this make us allow to access main thread but not necessary!!!!!!!
             */
//            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase)=movieDatabase.movieDao()

}