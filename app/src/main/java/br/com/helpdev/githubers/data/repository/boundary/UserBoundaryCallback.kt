package br.com.helpdev.githubers.data.repository.boundary

import androidx.paging.PagedList
import br.com.helpdev.githubers.data.entity.UserWithFav
import br.com.helpdev.githubers.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserBoundaryCallback(
    private val userRepo: UserRepository
) : PagedList.BoundaryCallback<UserWithFav>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        CoroutineScope(Dispatchers.Main).launch {
            userRepo.loadUserListRemoteRepo()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: UserWithFav) {
        super.onItemAtEndLoaded(itemAtEnd)
        CoroutineScope(Dispatchers.Main).launch {
            userRepo.loadUserListRemoteRepo(itemAtEnd.user.id)
        }
    }
}