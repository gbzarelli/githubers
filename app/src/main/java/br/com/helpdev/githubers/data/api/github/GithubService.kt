package br.com.helpdev.githubers.data.api.github

import br.com.helpdev.githubers.data.entity.User
import br.com.helpdev.githubers.data.entity.UserRepo
import br.com.helpdev.githubers.data.model.SearchUsers
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface used by Retrofit: Here maping the calls of api
 *
 * -> Docs: https://developer.github.com/v3/
 * -> Addrs: https://api.github.com/
 *
 */
interface GithubService {

    @GET("users")
    fun listUsers(@Query("since") since: Int? = 0): Deferred<Response<List<User>>>

    @GET("search/users")
    fun findUsers(@Query("q") query: String): Call<SearchUsers>

    @GET("search/repositories")
    fun findRepos(@Query("q") query: String): Deferred<Response<List<UserRepo>>>

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Deferred<Response<User>>

    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user: String, @Query("page") page: Int? = 0): Deferred<Response<List<UserRepo>>>

}