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
import java.util.regex.Pattern

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
            if(validate()) {
                viewModel.createTrack(
                    binding.name.text.toString(),
                    binding.duration.text.toString(),
                    args.albumId
                )
            }
        }
    }

    private fun validateName(): Boolean {
        return if (binding.name.text.toString().isEmpty() ) {
            binding.name.error="Debe ingresar el nombre"
            false
        } else if (binding.name.text.length >= 50 ) {
            binding.name.error="El nombre no debe superar los 50 caracteres"
            false
        } else {
            binding.name.error=null
            true
        }
    }

    private fun validateDuration() : Boolean {
        //Recuperamos el contenido del textInputLayout
        val duration = binding.duration.text.toString()

        // Patrón con expresiones regulares
        val passwordRegex = Pattern.compile(
            "^" +
                    "([01]?[0-9]|2[0-3]):[0-5][0-9]"
        )

        return if (duration.isEmpty()){
            binding.duration.error = "Debe ingresar una duración"
            false
        }else if (!passwordRegex.matcher(duration).matches()){
            binding.duration.error = "La duracion no cumple con el formato 00:00"
            false
        }else{
            binding.duration.error = null
            true
        }
    }

    private fun validate() :Boolean {
        val result = arrayOf(validateDuration(), validateName())

        if (false in result){
            return false
        }

        Toast.makeText(activity, "Track Creado", Toast.LENGTH_SHORT).show()
        return true
    }

}
