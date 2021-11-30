package com.example.vinilosmobile.ui.track

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.vinilosmobile.databinding.TrackFragmentBinding

class TrackCreateFragment  : Fragment() {
    companion object {
        fun newInstance() = TrackCreateFragment()
    }

    private var _binding: TrackFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TrackCreateViewModel


    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TrackFragmentBinding.inflate(inflater, container, false)
        _binding!!.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: TrackCreateFragmentArgs by navArgs()

        viewModel = ViewModelProvider(this, TrackCreateViewModel.Factory(activity.application)).get(TrackCreateViewModel::class.java)
        binding.saveTrack.setOnClickListener {
            viewModel.createTrack(binding.name.text.toString(),binding.duration.text.toString(), args.albumId)
        }
    }

}
