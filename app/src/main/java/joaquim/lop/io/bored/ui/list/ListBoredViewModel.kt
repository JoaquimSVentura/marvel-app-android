package joaquim.lop.io.bored.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import joaquim.lop.io.bored.data.model.character.BoredModel
import joaquim.lop.io.bored.repository.BoredRepository
import joaquim.lop.io.bored.ui.state.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListBoredViewModel @Inject constructor(
    private val repository: BoredRepository
) : ViewModel() {

    private val _boredObject =
        MutableStateFlow<ResourceState<BoredModel>>(ResourceState.Loading())
    val boredObject: StateFlow<ResourceState<BoredModel>> = _boredObject

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            val response = repository.list()
            _boredObject.value = handleResponse(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _boredObject.value =
                    ResourceState.Error("Erro de conexão com a internet")
                else -> _boredObject.value = ResourceState.Error("Falha na conversão de dados")
            }
        }
    }

    fun reload() {
        fetch()
    }

    private fun handleResponse(response: Response<BoredModel>): ResourceState<BoredModel> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}