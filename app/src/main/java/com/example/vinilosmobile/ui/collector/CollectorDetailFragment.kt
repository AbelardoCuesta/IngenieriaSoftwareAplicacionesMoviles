package com.example.vinilosmobile.ui.collector

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
import com.example.vinilosmobile.databinding.CollectorDetailFragmentBinding
import com.example.vinilosmobile.models.Collector
import com.squareup.picasso.Picasso

class CollectorDetailFragment : Fragment() {
    companion object {
        fun newInstance() = CollectorDetailFragment()
    }

    private var _binding: CollectorDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorDetailViewModel


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
        _binding = CollectorDetailFragmentBinding.inflate(inflater, container, false)
        _binding!!.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_comments)
        val args: CollectorDetailFragmentArgs by navArgs()
        Log.d("Args", args.collectorId.toString())
        viewModel = ViewModelProvider(this, CollectorDetailViewModel.Factory(activity.application, args.collectorId)).get(CollectorDetailViewModel::class.java)
        viewModel.collector.observe(viewLifecycleOwner, Observer<Collector> {
            it.apply {
                binding.collector = this

//                Picasso.get()
//                    .load(it.cover)
//                    .placeholder(R.drawable.ic_menu_camera)
//                    .error(R.drawable.ic_menu_camera)
//                    .into(binding.avatar);

            }
        })

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
        // TODO: Use the ViewModel
    }

}
