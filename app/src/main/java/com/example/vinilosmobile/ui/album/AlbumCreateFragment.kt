package com.example.vinilosmobile.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vinilosmobile.databinding.FragmentCreateAlbumBinding
import java.util.regex.Pattern


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
           if(validate()) {
               viewModel.createAlbum(
                   binding.nombre.text.toString(),
                   binding.cover.text.toString(),
                   binding.genre.text.toString(),
                   binding.record.text.toString(),
                   binding.createDate.text.toString(),
                   binding.description.text.toString()
               )
           }
        }
    }

    private fun validateName(): Boolean {
        return if (binding.nombre.text.toString().isEmpty() ) {
            binding.nombre.error="Debe ingresar el nombre"
            false
        } else if (binding.nombre.text.length >= 50 ) {
            binding.nombre.error="El nombre no debe superar los 50 caracteres"
            false
        } else {
            binding.nombre.error=null
            true
        }
    }

    private fun validateCover(): Boolean {
        return if (binding.cover.text.toString().isEmpty() ) {
            binding.cover.error = "Debe ingresar la caratula"
            false
        } else if (binding.cover.text.length >= 50 ) {
            binding.cover.error = "El cover no debe superar los 50 caracteres"
            false
        } else {
            binding.cover.error = null
            true
        }
    }

    private fun validateGenre( ): Boolean {
        return if (binding.genre.text.toString().isEmpty() ) {
            binding.genre.error = "Debe ingresar el genero"
            false
        } else if (binding.genre.text.length >= 50 ) {
            binding.genre.error = "El genero no debe superar los 50 caracteres"
            false
        } else {
            binding.genre.error = null
            true
        }
    }

    private fun validateRecord( ): Boolean {
        return if (binding.record.text.toString().isEmpty() ) {
            binding.record.error = "Debe ingresar la casa disquera"
            false
        } else if (binding.record.text.length >= 50 ) {
            binding.record.error = "La casa disquera no debe superar los 50 caracteres"
            false
        } else {
            binding.record.error = null
            true
        }
    }

    private fun validateDescription( ): Boolean {
        return if (binding.description.text.toString().isEmpty() ) {
            binding.description.error = "Debe ingresar la descripción"
            false
        } else if (binding.description.text.length >= 200 ) {
            binding.description.error = "La descripción no debe superar los 50 caracteres"
            false
        } else {
            binding.description.error = null
            true
        }
    }

    private fun validateFecha() : Boolean {
        //Recuperamos el contenido del textInputLayout
        val createDate = binding.createDate.text.toString()

        // Patrón con expresiones regulares
        val passwordRegex = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])\$")

        return if (createDate.isEmpty()){
            binding.createDate.error = "Debe ingresar una fecha de creación"
            false
        }else if (!passwordRegex.matcher(createDate).matches()){
            binding.createDate.error = "La fecha de creación no cumple con el formato 1900-00-00"
            false
        }else{
            binding.createDate.error = null
            true
        }
    }

    private fun validate() :Boolean {
        val result = arrayOf( validateName(), validateCover(), validateGenre(), validateRecord(), validateDescription(), validateFecha())

        if (false in result){
            return false
        }

        Toast.makeText(activity, "Álbum Creado", Toast.LENGTH_SHORT).show()
        return true
    }
}
