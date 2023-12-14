package com.pascal.foodrecipescompose.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.foodrecipescompose.data.local.model.ProfileEntity
import com.pascal.foodrecipescompose.domain.usecase.ProfileUC
import com.pascal.foodrecipescompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUC: ProfileUC
): ViewModel() {

    private val _profile = MutableStateFlow<UiState<ProfileEntity>>(UiState.Loading)
    val profile: StateFlow<UiState<ProfileEntity>> = _profile

    suspend fun loadProfile() {
        viewModelScope.launch {
            _profile.value = UiState.Loading
            val result = profileUC.getProfile()
            result.collect {
                if (it.id.toString().isNotEmpty()) {
                    _profile.value = UiState.Empty
                } else {
                    _profile.value = UiState.Success(it)
                }
            }

            result.catch {
                _profile.value = UiState.Error(it.message.toString())
            }
        }
    }
    
    fun addProfile(item: ProfileEntity) {
        viewModelScope.launch {
            profileUC.addProfile(ProfileUC.Params(item))
        }
    }
}