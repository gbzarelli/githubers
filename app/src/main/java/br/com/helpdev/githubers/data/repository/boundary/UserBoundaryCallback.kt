package br.com.helpdev.githubers.data.repository.boundary

import androidx.paging.PagedList
import br.com.helpdev.githubers.data.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserBoundaryCallback(
    private val coroutineScope: CoroutineScope,
    private val userRepo: UserRepository
) : PagedList.BoundaryCallback<UserWithFav>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        coroutineScope.launch {
            userRepo.loadUserListRemoteRepo()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: UserWithFav) {
        super.onItemAtEndLoaded(itemAtEnd)
        coroutineScope.launch {
            userRepo.loadUserListRemoteRepo(itemAtEnd.user.id)
        }
    }
}