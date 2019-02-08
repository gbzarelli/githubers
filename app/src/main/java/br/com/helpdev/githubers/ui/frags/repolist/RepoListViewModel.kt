package br.com.helpdev.githubers.ui.frags.repolist

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repository.UserRepository
import javax.inject.Inject

class RepoListViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {


}