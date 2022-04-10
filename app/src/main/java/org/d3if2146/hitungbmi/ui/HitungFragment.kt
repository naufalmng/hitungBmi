package org.d3if2146.hitungbmi.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if2146.hitungbmi.R
import org.d3if2146.hitungbmi.core.data.source.model.HasilBmi
import org.d3if2146.hitungbmi.core.data.source.model.KategoriBmi
import org.d3if2146.hitungbmi.databinding.FragmentHitungBinding
import org.d3if2146.hitungbmi.setupBtnOnLongClickListener

class HitungFragment : Fragment() {
    private var _binding: FragmentHitungBinding? = null
    private val binding get() = _binding!!
    private lateinit var hitungViewModel: HitungViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHitungBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnListeners()
        hitungViewModel = ViewModelProvider(this)[HitungViewModel::class.java]
        requireActivity().setupBtnOnLongClickListener(binding.btnHitung)
        requireActivity().setupBtnOnLongClickListener(binding.btnReset)
        setupObservers()
    }
    private fun setupObservers() {
        hitungViewModel.hasilBmi.observe(requireActivity()) {
            if (it != null) {
                showResult(it)
            }
        }
    }

    private fun setupBtnListeners() {
        binding.btnHitung.setOnClickListener{
            hitungBmi()
        }
        binding.btnReset.setOnClickListener{
            binding.etBb.clearFocus()
            binding.etTb.clearFocus()
            reset()
        }
    }

    private fun hitungBmi() {
        val berat = binding.etBb.text.toString()
        val tinggi = binding.etTb.text.toString()
        val selectedId = binding.rgGender.checkedRadioButtonId

        if(TextUtils.isEmpty(berat)){
            Toast.makeText(requireContext(), getString(R.string.berat_invalid), Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(tinggi)){
            Toast.makeText(requireContext(), getString(R.string.tinggi_invalid), Toast.LENGTH_SHORT).show()
            return
        }
        if(selectedId == -1){
            Toast.makeText(requireContext(), getString(R.string.gender_invalid), Toast.LENGTH_SHORT).show()
            return
        }
        hitungViewModel.hitungBmi(berat,tinggi,selectedId == R.id.rbPria)
    }

    private fun showResult(result: HasilBmi){
        binding.tvBmi.text = getString(R.string.bmi,result.bmi)
        binding.tvKategori.text = getString(R.string.kategori,getKategoriLabel(result.kategori))
        binding.tvBmi.visibility = View.VISIBLE
        binding.tvKategori.visibility = View.VISIBLE
        binding.divider.visibility = View.VISIBLE
        binding.btnReset.visibility = View.VISIBLE
    }

    private fun reset() {
        binding.btnReset.visibility = View.GONE
        binding.tvBmi.visibility = View.GONE
        binding.tvKategori.visibility = View.GONE
        binding.divider.visibility = View.GONE
        binding.etBb.text?.clear()
        binding.etTb.text?.clear()
        binding.tvBmi.text = null
        binding.tvKategori.text = null
        binding.rgGender.clearCheck()
    }

    private fun getKategoriLabel(kategori: KategoriBmi): String{
        val stringRes = when(kategori){
            KategoriBmi.KURUS -> R.string.kurus
            KategoriBmi.GEMUK -> R.string.gemuk
            KategoriBmi.IDEAL -> R.string.ideal
        }
        return getString(stringRes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}