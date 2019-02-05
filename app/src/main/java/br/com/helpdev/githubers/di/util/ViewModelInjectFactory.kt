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
 * Source: https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/viewmodel/GithubViewModelFactory.kt
 */

package br.com.helpdev.githubers.di.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Factory para os ViewModel que utilizam DI.
 *  A necessidade de um factory para os ViewModel é devido ao seus contrutores que não podem conter argumentos,
 * porem com a injeção os mesmo devem conter para injetar seus valores.
 *
 *  O Factory verifica o Map de ViewModel definido pela notação {@link ViewModelKey}
 * no Modulo de {@link ViewModelsModule} para criar uma instancia do ViewModel injetável.
 *
 *  Deve sempre utilizar esse Factory quando se tem ViewModels com @Inject, lembre-se de inserir os
 * ViewsModels no modulo {@link ViewModelsModule} do projeto
 *
 * ---
 *
 * Factory for ViewModel that use DI.
 *  The need for a factory for the ViewModel is due to its constructor that can not contain arguments,
 * however with the injection the same must contain to inject their values.
 *
 *  Factory checks the ViewModel Map defined by the {@link ViewModelKey} notation
 * in the {@link ViewModelsModule} Module to create an Injection ViewModel instance.
 *
 *  You should always use this Factory when you have ViewModels with @Inject, be sure to insert the
 * viewsModels in the {@link ViewModelsModule} module of the project
 *
 * -----
 * Source: https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/viewmodel/GithubViewModelFactory.kt
 */
@Singleton
class ViewModelInjectFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST") return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}