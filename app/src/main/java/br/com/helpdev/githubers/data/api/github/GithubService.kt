package br.com.helpdev.githubers.data.api.github

import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.data.entity.UserRepo
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface utilizada pelo Retrofit; Aqui mapeamos as chamadas da API
 * --
 * Interface used by Retrofit: Here maping the calls of api
 *
 * -> Docs: https://developer.github.com/v3/
 * -> Addrs: https://api.github.com/
 *
 */
interface GithubService {

    @GET("users")
    fun listUsers(@Query("since") since: Int? = 0): Deferred<Response<List<User>>>

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Deferred<Response<User>>

    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user: String, @Query("page") page: Int? = 0): Deferred<Response<List<UserRepo>>>

    /**
     *
     * TODO CREATE SEARCH METHODS:
     *  https://api.github.com/search/repositories?q=helpdev
     *  https://api.github.com/search/users?q=helpdeveloper
     *
     */

}