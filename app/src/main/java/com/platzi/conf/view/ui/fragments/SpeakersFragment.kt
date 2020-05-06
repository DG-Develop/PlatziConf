package com.platzi.conf.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.platzi.conf.R
import com.platzi.conf.model.Speaker
import com.platzi.conf.view.adapter.SpeakerAdapter
import com.platzi.conf.view.adapter.SpeakerListener
import com.platzi.conf.viewmodel.SpeakerViewModel
import kotlinx.android.synthetic.main.fragment_speakers.*

/**
 * A simple [Fragment] subclass.
 */
class SpeakersFragment : Fragment(), SpeakerListener {
    private lateinit var speakerAdapter: SpeakerAdapter
    private lateinit var viewModel: SpeakerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speakers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SpeakerViewModel::class.java)
        viewModel.refresh()

        speakerAdapter = SpeakerAdapter(this)

        rvSpeakers.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = speakerAdapter
        }
        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.listSchedule.observe(viewLifecycleOwner, Observer<List<Speaker>>{ schedule ->
            speakerAdapter.updateData(schedule)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean>{
            if (it != null)
                rlBaseSpeaker.visibility = View.INVISIBLE
        })
    }

    override fun onSpeakerClicked(speaker: Speaker, position: Int) {
        val bundle = bundleOf("speaker" to speaker)

        findNavController().navigate(R.id.speakersDetailFragmnetDialog, bundle)
    }

}
