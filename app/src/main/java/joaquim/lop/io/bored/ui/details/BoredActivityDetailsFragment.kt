package joaquim.lop.io.bored.ui.details

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import joaquim.lop.io.bored.data.model.character.BoredModel
import joaquim.lop.io.bored.databinding.FragmentBoredActivityDetailsBinding
import joaquim.lop.io.bored.ui.base.BaseFragment
import joaquim.lop.io.bored.ui.list.ListBoredViewModel
import joaquim.lop.io.bored.util.currencyFormat

class BoredActivityDetailsFragment :
    BaseFragment<FragmentBoredActivityDetailsBinding, ListBoredViewModel>() {

    override val viewModel: ListBoredViewModel by viewModels()

    //private val args: DetailsCharacterFragmentArgs by navArgs()
    private val args: BoredActivityDetailsFragmentArgs by navArgs()
    private var boredModel: BoredModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boredModel = args.model
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        boredModel?.let { setTextCard(it) }
        setUpListeners()
        startTimer()
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
        btExit.setOnClickListener{ findNavController().popBackStack() }
        tvLink.setOnClickListener { openWebPage(tvLink.text.toString()) }
        cvContentComplete.setOnClickListener {
            checkBoxComplete.isChecked = true
            configureLayout()
        }
        cvContentGiveUp.setOnClickListener {
            checkBoxGiveUp.isChecked = true
            configureLayout()
        }
    }

    private fun startTimer() {
        binding.chTimer.start()
    }

    private fun configureLayout() = binding.run {
        cvContentGiveUp.isEnabled = false
        checkBoxRunning.isChecked = false
        cvContentComplete.isEnabled = false
        cvContentComplete.setCardBackgroundColor(Color.GRAY)
        cvContentGiveUp.setCardBackgroundColor(Color.GRAY)
        chTimer.stop()
    }

    private fun openWebPage(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentBoredActivityDetailsBinding =
        FragmentBoredActivityDetailsBinding.inflate(inflater, container, false)
}
