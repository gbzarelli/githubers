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


import br.com.helpdev.githubers.data.provider.UserLoginSuggestionProvider
import br.com.helpdev.githubers.ui.GithubersActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ProvidersModule {

    /**********************************************
     * ADICIONAR OS PROVIDERS AQUI!
     *********************************************/

    @ContributesAndroidInjector
    abstract fun contributeUserLoginSuggestion(): UserLoginSuggestionProvider
}