package br.com.helpdev.githubers.ui.frags.repo

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repository.UserRepository
import javax.inject.Inject

class RepoViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {


}