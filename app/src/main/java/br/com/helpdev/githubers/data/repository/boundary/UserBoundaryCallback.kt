package br.com.helpdev.githubers.data.repository.boundary

import androidx.paging.PagedList
import br.com.helpdev.githubers.data.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class UserBoundaryCallback(
    private val userRepo: UserRepository
) : PagedList.BoundaryCallback<UserWithFav>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        userRepo.loadUserListRemoteRepo(CoroutineScope(Dispatchers.Main))
    }

    override fun onItemAtEndLoaded(itemAtEnd: UserWithFav) {
        super.onItemAtEndLoaded(itemAtEnd)
        userRepo.loadUserListRemoteRepo(CoroutineScope(Dispatchers.Main), itemAtEnd.user.id)
    }
}