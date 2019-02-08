package br.com.helpdev.githubers.ui.frags.user

import androidx.lifecycle.ViewModel
import br.com.helpdev.githubers.data.repository.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

}