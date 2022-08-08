package org.d3if2146.hitungbmi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.d3if2146.hitungbmi.R
import org.d3if2146.hitungbmi.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {
   private var _binding: FragmentAboutBinding? = null
   private val binding get() = _binding!!

   override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View {
       _binding = FragmentAboutBinding.inflate(inflater, container, false)
       setHasOptionsMenu(true)
       return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
    }

   override fun onDestroyView() {
       super.onDestroyView()
       _binding = null
   }
}