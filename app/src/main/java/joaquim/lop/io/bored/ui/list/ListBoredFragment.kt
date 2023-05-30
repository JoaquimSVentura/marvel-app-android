package joaquim.lop.io.bored.ui.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import joaquim.lop.io.bored.R
import joaquim.lop.io.bored.data.model.character.BoredModel
import joaquim.lop.io.bored.databinding.FragmentListBoredBinding
import joaquim.lop.io.bored.ui.base.BaseFragment
import joaquim.lop.io.bored.ui.state.ResourceState
import joaquim.lop.io.bored.util.currencyFormat
import joaquim.lop.io.bored.util.hide
import joaquim.lop.io.bored.util.show
import joaquim.lop.io.bored.util.toast
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class ListBoredFragment : BaseFragment<FragmentListBoredBinding, ListBoredViewModel>() {

    override val viewModel: ListBoredViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserver()
        setUpListeners()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.boredObject.collect { resource ->
            binding.run {
                when (resource) {
                    is ResourceState.Success -> {
                        progressButtonLoader.hide()
                        tvLoad.text = "Carregar"
                        resource.data?.let { setTextCard(it) }
                    }

                    is ResourceState.Error -> {
                        progressCircular.hide()
                        progressButtonLoader.hide()
                        tvLoad.text = "Carregar"
                        resource.message?.let { message ->
                            toast(getString(R.string.an_error_occurred))
                            Timber.tag("ListCharacterFragment").e("Error -> $message")
                        }
                    }

                    is ResourceState.Loading -> {
                        progressCircular.show()
                        progressButtonLoader.show()
                        tvLoad.text = "Aguarde..."
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun setTextCard(values: BoredModel) = binding.run {
        progressCircular.hide()
        tvContentActivity.text = values.activity
        tvContentType.text = values.type
        tvContentParticipants.text = values.participants.toString()

        tvContentPrice.text = currencyFormat(values.price.toBigDecimal())
        tvContentLink.text = values.link
        tvContentKey.text = values.key
        tvContentAccessibility.text = values.accessibility.toString()
    }

    private fun setUpListeners() = binding.run {
        cardView.setOnClickListener { }
        tvLink.setOnClickListener { openWebPage(tvLink.text.toString()) }
        cvContent.setOnClickListener {
            progressButtonLoader.show()
            tvLoad.text = "Aguarde..."
            viewModel.reload()
        }
    }

    private fun openWebPage(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListBoredBinding =
        FragmentListBoredBinding.inflate(inflater, container, false)
}