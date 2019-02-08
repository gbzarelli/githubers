package br.com.helpdev.githubers.di.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

/**
 * Interface para criar os Factory de cada Worker.
 * Interface to creates Factory of each Worker.
 *
 * Sample to use:
 *  class MyWorker(context,workerParameter,...): Worker(context,workerParameters){
 *      [...]
 *      class MyWorkerFactory([injectable parameters]):IWorkerFactory{
 *          //create worker
 *      }
 *  }
 */
interface IWorkerFactory<T : ListenableWorker> {
    fun create(appContext:Context, params: WorkerParameters): T
}