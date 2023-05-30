package joaquim.lop.io.bored.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import joaquim.lop.io.bored.R
import joaquim.lop.io.bored.data.model.character.BoredModel
import joaquim.lop.io.bored.databinding.FragmentListBoredBinding
import joaquim.lop.io.bored.ui.base.BaseFragment
import joaquim.lop.io.bored.ui.state.ResourceState
import joaquim.lop.io.bored.util.*
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class ListBoredFragment : BaseFragment<FragmentListBoredBinding, ListBoredViewModel>() {

    override val viewModel: ListBoredViewModel by viewModels()
    private var boredModel: BoredModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserver()
        setUpListeners()
        viewModel.reload()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.boredObject.collect { resource ->
            binding.run {
                when (resource) {
                    is ResourceState.Success -> {
                        progressButtonLoader.hide()
                        tvLoad.text = "Próximo Item"
                        resource.data?.let { setTextCard(it) }
                        boredModel = resource.data
                    }

                    is ResourceState.Error -> {
                        progressButtonLoader.hide()
                        tvLoad.text = "Recarregar"
                        resource.message?.let { message ->
                            toast(getString(R.string.an_error_occurred))
                            Timber.tag("ListCharacterFragment").e("Error -> $message")
                        }
                    }

                    is ResourceState.Loading -> {
                        progressButtonLoader.show()
                        tvLoad.text = "Aguarde..."
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setTextCard(values: BoredModel) = binding.run {
        tvContentActivity.text = values.activity
        tvContentType.text = values.type
        tvContentParticipants.text = values.participants.toString()
        tvContentPrice.text = currencyFormat(values.price.toBigDecimal())
        tvContentLink.text = values.link
        tvContentKey.text = values.key
        tvContentAccessibility.text = values.accessibility.toString()
    }

    private fun setUpListeners() = binding.run {
        tvLink.setOnClickListener { startActivity(openWebPage(tvLink.text.toString())) }

        cardView.setOnClickListener {
            val action = ListBoredFragmentDirections
                .actionListCharacterFragmentToBoredActivityDetailsFragment(boredModel!!)
            findNavController().navigate(action)
        }

        cvContent.setOnClickListener {
            progressButtonLoader.show()
            tvLoad.text = "Aguarde..."
            viewModel.reload()
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListBoredBinding =
        FragmentListBoredBinding.inflate(inflater, container, false)
}
