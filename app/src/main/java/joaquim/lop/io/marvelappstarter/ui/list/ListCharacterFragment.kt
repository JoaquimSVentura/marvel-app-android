package joaquim.lop.io.marvelappstarter.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import joaquim.lop.io.marvelappstarter.R
import joaquim.lop.io.marvelappstarter.databinding.FragmentListCharacterBinding
import joaquim.lop.io.marvelappstarter.ui.base.BaseFragment
import joaquim.lop.io.marvelappstarter.ui.state.ResourceState
import joaquim.lop.io.marvelappstarter.util.hide
import joaquim.lop.io.marvelappstarter.util.show
import joaquim.lop.io.marvelappstarter.util.toast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ListCharacterFragment : BaseFragment<FragmentListCharacterBinding, ListCharacterViewModel>() {

    override val viewModel: ListCharacterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserver()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.objectAtivity.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    resource.data?.let { values ->
                        binding.progressCircular.hide()
                        //TODO
                    }
                }

                is ResourceState.Error -> {
                    binding.progressCircular.hide()
                    resource.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("ListCharacterFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressCircular.show()
                }
                else -> {
                }
            }
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListCharacterBinding =
        FragmentListCharacterBinding.inflate(inflater, container, false)
}