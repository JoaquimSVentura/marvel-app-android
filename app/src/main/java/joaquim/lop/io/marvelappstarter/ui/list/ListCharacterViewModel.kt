package joaquim.lop.io.marvelappstarter.ui.list

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import joaquim.lop.io.marvelappstarter.data.model.character.ActivityModelResponse
import joaquim.lop.io.marvelappstarter.repository.MarvelRepository
import joaquim.lop.io.marvelappstarter.ui.state.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListCharacterViewModel @Inject constructor(
    private val repository: MarvelRepository
) : ViewModel() {

    private val _objectAtivity =
        MutableStateFlow<ResourceState<ActivityModelResponse>>(ResourceState.Loading())
    val objectAtivity: StateFlow<ResourceState<ActivityModelResponse>> = _objectAtivity

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            val response = repository.list()
            _objectAtivity.value = handleResponse(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _objectAtivity.value =
                    ResourceState.Error("Erro de conexão com a internet")
                else -> _objectAtivity.value = ResourceState.Error("Falha na conversão de dados")
            }
        }
    }

    private fun handleResponse(response: Response<ActivityModelResponse>): ResourceState<ActivityModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}