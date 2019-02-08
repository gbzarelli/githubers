package br.com.helpdev.githubers.di.worker

import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Cria a notação de um MapKey para mapear os Workers com DI
 * Create a MapKey annotation to map Workers with DI
 */
@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)