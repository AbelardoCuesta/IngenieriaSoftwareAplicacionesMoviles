package com.example.vinilosmobile.ui.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.vinilosmobile.databinding.TrackFragmentBinding
import android.widget.TimePicker
import com.example.vinilosmobile.ui.utils.TimePickerUtil
import androidx.navigation.fragment.findNavController


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
        val tpHourMin = binding.duration as TimePicker
        tpHourMin.setIs24HourView(true)


        viewModel = ViewModelProvider(this, TrackCreateViewModel.Factory(activity.application)).get(TrackCreateViewModel::class.java)
        binding.saveTrack.setOnClickListener {

           var durationValue = ( if (TimePickerUtil.getTimePickerHour(binding.duration)< 10) ("0"+TimePickerUtil.getTimePickerHour(binding.duration).toString()) else (TimePickerUtil.getTimePickerHour(binding.duration).toString()))+
                    ":" +(if (TimePickerUtil.getTimePickerMinute(binding.duration)< 10) ("0"+TimePickerUtil.getTimePickerMinute(binding.duration).toString()) else (TimePickerUtil.getTimePickerMinute(binding.duration).toString()))
            if(validate()) {
                viewModel.createTrack(
                    binding.name.text.toString(),
                    durationValue,
                    args.albumId
                )

                Toast.makeText(activity, "Track Creado", Toast.LENGTH_SHORT).show()

                val action = TrackCreateFragmentDirections.actionNavCreateTrackToNavAlbumDetail(args.albumId)
                findNavController().navigate(action)
            }
        }
    }

    private fun validateName(): Boolean {

        return if (binding.name.text.toString().isEmpty() ) {
            binding.inputName.error = "Debe ingresar el nombre"
            false
        } else if (binding.name.text.length >= 50 ) {
            binding.inputName.error ="El nombre no debe superar los 50 caracteres"
            false
        } else {
            binding.inputName.error=null
            true
        }
    }

    private fun validateDuration(): Boolean {
        return if (TimePickerUtil.getTimePickerMinute(binding.duration) == 0 && TimePickerUtil.getTimePickerHour(binding.duration) == 0 ) {
            binding.inputTime.error = "La duración debe ser diferente a 00:00!!"
            false
        } else {
            binding.inputTime.error= null
            true
        }
    }

    private fun validate() :Boolean {
        val result = arrayOf(validateName(), validateDuration())

        if (false in result){
            return false
        }

        return true
    }

}