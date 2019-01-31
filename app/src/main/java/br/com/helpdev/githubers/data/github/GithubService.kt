package br.com.helpdev.githubers.data.github

import br.com.helpdev.githubers.data.entities.UserRepo
import br.com.helpdev.githubers.data.entities.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users")
    fun listUsers(): Call<List<User>>

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Call<User>

    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user: String): Call<List<UserRepo>>

}