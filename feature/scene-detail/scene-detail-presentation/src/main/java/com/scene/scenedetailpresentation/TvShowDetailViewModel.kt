package com.scene.scenedetailpresentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scene.scenedetaildomain.usecase.FetchTvShowDetailUseCase
import com.scene.util.espresso.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val tvShowDetailUseCase: FetchTvShowDetailUseCase
) : ViewModel() {

    private val _tvShowName = MutableLiveData<String>()
    val tvShowName: LiveData<String> = _tvShowName

    fun getTvShowDetail(tvShowId: Int) = viewModelScope.launch {
        runCatching {
            wrapEspressoIdlingResource {
                tvShowDetailUseCase.fetchTvShowDetail(tvShowId)
            }
        }
            .onSuccess {
                _tvShowName.value = it.name
            }
            .onFailure {
                _tvShowName.value = it.message
            }
    }
}
