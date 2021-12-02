package com.example.vinilosmobile.ui.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vinilosmobile.databinding.FragmentCreateAlbumBinding
import java.util.regex.Pattern
import androidx.navigation.fragment.findNavController


class AlbumCreateFragment : Fragment() {
    companion object {
        fun newInstance() = AlbumCreateFragment()
    }

    private var _binding: FragmentCreateAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumCreateViewModel


    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
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

        viewModel = ViewModelProvider(this, AlbumCreateViewModel.Factory(activity.application)).get(
            AlbumCreateViewModel::class.java
        )
        binding.saveAlbum.setOnClickListener {
            if (validate()) {
                val createDate = binding.createDate.text.toString() + "T00:00:00-05:00"
                Log.d("Fecha", createDate)
                viewModel.createAlbum(
                    binding.nombre.text.toString(),
                    binding.cover.text.toString(),
                    binding.genre.text.toString(),
                    binding.record.text.toString(),
                    createDate,
                    binding.description.text.toString()
                )
                Toast.makeText(activity, "Álbum Creado", Toast.LENGTH_SHORT).show()
                val action = AlbumCreateFragmentDirections.actionNavCreateAlbumToNavAlbums()
                findNavController().navigate(action)
            }
        }
    }

    private fun validateName(): Boolean {
        return if (binding.nombre.text.toString().isEmpty()) {
            binding.inputName.error = "Debe ingresar el nombre"
            false
        } else if (binding.nombre.text.length >= 50) {
            binding.inputName.error = "El nombre no debe superar los 50 caracteres"
            false
        } else {
            binding.inputName.error = null
            true
        }
    }

    private fun validateCover(): Boolean {

        val passwordRegex =
            Pattern.compile("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
        val cover = binding.cover.text.toString()

        return if (binding.cover.text.toString().isEmpty()) {
            binding.inputCover.error = "Debe ingresar la caratula"
            false
        } else if (binding.cover.text.length >= 500) {
            binding.inputCover.error = "La caratula"
            false
        } else if (!passwordRegex.matcher(cover).matches()) {
            binding.inputCover.error = "La caratula no cumple con el formato URL"
            false
        } else {
            binding.inputCover.error = null
            true
        }
    }

    private fun validateGenre(): Boolean {
        return if (binding.genre.text.toString().isEmpty()) {
            binding.inputGenre.error = "Debe ingresar el genero"
            false
        } else if (binding.genre.text.length >= 50) {
            binding.inputGenre.error = "El genero no debe superar los 50 caracteres"
            false
        } else {
            binding.inputGenre.error = null
            true
        }
    }

    private fun validateRecord(): Boolean {
        return if (binding.record.text.toString().isEmpty()) {
            binding.inputRecord.error = "Debe ingresar la casa disquera"
            false
        } else if (binding.record.text.length >= 50) {
            binding.inputRecord.error = "La casa disquera no debe superar los 50 caracteres"
            false
        } else {
            binding.inputRecord.error = null
            true
        }
    }

    private fun validateDescription(): Boolean {
        return if (binding.description.text.toString().isEmpty()) {
            binding.inputDescription.error = "Debe ingresar la descripción"
            false
        } else if (binding.description.text.length >= 200) {
            binding.inputDescription.error = "La descripción no debe superar los 50 caracteres"
            false
        } else {
            binding.inputDescription.error = null
            true
        }
    }

    private fun validateFecha(): Boolean {
        //Recuperamos el contenido del textInputLayout
        val createDate = binding.createDate.text.toString()

        // Patrón con expresiones regulares
        val passwordRegex =
            Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])\$")

        return if (createDate.isEmpty()) {
            binding.inputCreateDate.error = "Debe ingresar una fecha de creación"
            false
        } else if (!passwordRegex.matcher(createDate).matches()) {
            binding.inputCreateDate.error = "YYYY-MM-DD"
            false
        } else {
            binding.inputCreateDate.error = null
            true
        }
    }

    private fun validate(): Boolean {
        val result = arrayOf(
            validateName(),
            validateDescription(),
            validateFecha(),
            validateGenre(),
            validateCover(),
            validateRecord(),
        )

        if (false in result) {
            return false
        }

        return true
    }
}