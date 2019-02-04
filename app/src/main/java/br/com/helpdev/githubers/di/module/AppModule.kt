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
import androidx.room.Room
import br.com.helpdev.githubers.data.api.github.GithubService
import br.com.helpdev.githubers.data.db.GithubDatabase
import br.com.helpdev.githubers.util.DATABASE_NAME
import br.com.helpdev.githubers.util.GITHUB_BASE_URL
import br.com.helpdev.githubers.util.gson.GsonFactory
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [ViewModelsModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideGson() = GsonFactory.getGson()

    @Singleton
    @Provides
    fun provideGithubService(gson: Gson): GithubService {
        return Retrofit.Builder()
            .baseUrl(GITHUB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDatabase = Room
        .databaseBuilder(app, GithubDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideUserDao(db: GithubDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideRepoDao(db: GithubDatabase) = db.userRepoDao()
}