package org.d3if2146.hitungbmi.ui.histori

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import org.d3if2146.hitungbmi.R
import org.d3if2146.hitungbmi.core.data.source.db.BmiDb
import org.d3if2146.hitungbmi.databinding.FragmentHistoryBinding
import org.d3if2146.hitungbmi.ui.hitung.HistoriViewModelFactory
import org.d3if2146.hitungbmi.ui.hitung.HitungViewModel
import org.d3if2146.hitungbmi.ui.hitung.HitungViewModelFactory


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historiViewModel: HistoriViewModel by lazy {
        val db = BmiDb.getInstance(requireContext())
        val factory = HistoriViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HistoriViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
     }

    private fun setupObservers() {
        with(historiViewModel){
            data.observe(viewLifecycleOwner){
                Log.d("HistoriFragment","Jumlah Data: ${it.size}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}