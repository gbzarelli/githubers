/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Sample from: https://github.com/googlesamples/android-architecture-components/tree/88747993139224a4bb6dbe985adf652d557de621/GithubBrowserSample/app/src/main/java/com/android/example/github/di
 */

package br.com.helpdev.githubers.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.*
import br.com.helpdev.githubers.data.api.github.GithubService
import br.com.helpdev.githubers.data.db.GithubDatabase
import br.com.helpdev.githubers.util.DATABASE_NAME
import br.com.helpdev.githubers.util.GITHUB_API_BASE_URL
import br.com.helpdev.githubers.util.MY_USER_GITHUB
import br.com.helpdev.githubers.util.gson.CalendarDeserializer
import br.com.helpdev.githubers.worker.GithubUsersWorker
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [ViewModelsModule::class])
class AppModule {


    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().apply {
        registerTypeAdapter(
            Calendar::class.java,
            CalendarDeserializer()
        )
    }.create()!!

    @Singleton
    @Provides
    fun provideGithubService(gson: Gson): GithubService {
        return Retrofit.Builder()
            .baseUrl(GITHUB_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDatabase = Room
        .databaseBuilder(app, GithubDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                WorkManager.getInstance()
                    .enqueueUniqueWork(
                        GithubUsersWorker::class.java.simpleName,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequestBuilder<GithubUsersWorker>()
                            .setInputData(Data.Builder().apply {
                                putString(GithubUsersWorker.DATA_LOAD_ONLY_USER, MY_USER_GITHUB)
                            }.build())
                            .setConstraints(
                                Constraints.Builder()
                                    .setRequiredNetworkType(NetworkType.CONNECTED)
                                    .build()
                            ).build()
                    )
            }
        })
        .build()

    @Singleton
    @Provides
    fun provideUserDao(db: GithubDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideRepoDao(db: GithubDatabase) = db.userRepoDao()

    @Singleton
    @Provides
    fun provideFavoritesDao(db: GithubDatabase) = db.favoriteDao()
}