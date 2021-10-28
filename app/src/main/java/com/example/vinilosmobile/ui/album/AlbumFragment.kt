package com.example.vinilosmobile.ui.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response
import com.example.vinilosmobile.brokers.VolleyBroker
import com.example.vinilosmobile.databinding.FragmentGalleryBinding
import androidx.appcompat.app.AppCompatActivity
import com.example.vinilosmobile.R

class AlbumFragment : Fragment() {

    private lateinit var albumViewModel: AlbumViewModel
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var volleyBroker: VolleyBroker

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        albumViewModel =
            ViewModelProvider(this).get(AlbumViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        albumViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        /*val getResultTextView : TextView = findViewById(R.id.text_gallery)
        volleyBroker = VolleyBroker(this.applicationContext)
        volleyBroker.instance.add(VolleyBroker.getRequest("albums",
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                getResultTextView.text = "Response is: ${response.substring(0, 500)}"
            },
            Response.ErrorListener {
                Log.d("TAG", it.toString())
                getResultTextView.text = "That didn't work!"
            }
        ))*/

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}