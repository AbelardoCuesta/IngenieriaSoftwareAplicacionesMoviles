package com.example.vinilosmobile.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vinilosmobile.databinding.FragmentCreateAlbumBinding


class AlbumCreateFragment : Fragment() {
    companion object {
        fun newInstance() = AlbumCreateFragment()
    }

    private var _binding: FragmentCreateAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumCreateViewModel


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
        _binding = FragmentCreateAlbumBinding.inflate(inflater, container, false)
        _binding!!.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        viewModel = ViewModelProvider(this, AlbumCreateViewModel.Factory(activity.application)).get(AlbumCreateViewModel::class.java)
        binding.saveAlbum.setOnClickListener {
            viewModel.createAlbum(binding.nombre.text.toString(),binding.cover.text.toString(),binding.genre.text.toString(),binding.record.text.toString(),binding.createDate.text.toString(),binding.description.text.toString() )
        }
    }

}
