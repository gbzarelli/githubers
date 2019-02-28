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

package br.com.helpdev.githubers.di

import android.app.Application
import android.content.Context
import br.com.helpdev.githubers.GithubersApp
import br.com.helpdev.githubers.di.module.ActivitiesModule
import br.com.helpdev.githubers.di.module.AppModule
import br.com.helpdev.githubers.di.module.ProvidersModule
import br.com.helpdev.githubers.di.module.WorkersModule
import br.com.helpdev.githubers.di.worker.WorkerInjectorFactory
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Suppress("unused")
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivitiesModule::class,
        ProvidersModule::class,
        WorkersModule::class
        /* ADD YOUR MODULES HERE */
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(githubApp: GithubersApp)
}