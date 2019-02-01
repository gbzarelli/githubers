package br.com.helpdev.githubers.data.github

import br.com.helpdev.githubers.data.entities.User
import br.com.helpdev.githubers.data.entities.UserRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface utilizada pelo Retrofit; Aqui mapeamos as chamadas da API
 * --
 * Interface used by Retrofit: Here maping the calls of api
 *
 * -> Docs: https://developer.github.com/v3/
 * -> Addrs: https://api.github.com/
 */
interface GithubService {

    @GET("users")
    fun listUsers(): Call<List<User>>

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Call<User>

    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user: String): Call<List<UserRepo>>

}