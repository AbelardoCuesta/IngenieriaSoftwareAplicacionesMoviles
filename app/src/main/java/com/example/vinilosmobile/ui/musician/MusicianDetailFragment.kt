package com.example.vinilosmobile.ui.musician

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.vinilosmobile.R
import com.example.vinilosmobile.databinding.MusicianDetailFragmentBinding
import com.example.vinilosmobile.models.Musician
import com.squareup.picasso.Picasso

class MusicianDetailFragment : Fragment() {
    companion object {
        fun newInstance() = MusicianDetailFragment()
    }

    private var _binding: MusicianDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MusicianDetailViewModel


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
        _binding = MusicianDetailFragmentBinding.inflate(inflater, container, false)
        _binding!!.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_musicians)
        val args: MusicianDetailFragmentArgs by navArgs()
        Log.d("Args musico", args.musicianId.toString())
        viewModel = ViewModelProvider(this, MusicianDetailViewModel.Factory(activity.application, args.musicianId)).get(
            MusicianDetailViewModel::class.java)
        viewModel.musician.observe(viewLifecycleOwner, Observer<Musician> {
            it.apply {
                binding.musician = this
                Picasso.get()
                    .load(it.image)
                    .placeholder(R.drawable.ic_menu_camera)
                    .error(R.drawable.ic_menu_camera)
                    .into(binding.avatar);
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
        // TODO: Use the ViewModel
    }

}
