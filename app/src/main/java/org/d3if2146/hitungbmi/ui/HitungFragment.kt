package org.d3if2146.hitungbmi.ui

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if2146.hitungbmi.R
import org.d3if2146.hitungbmi.core.data.source.model.HasilBmi
import org.d3if2146.hitungbmi.core.data.source.model.KategoriBmi
import org.d3if2146.hitungbmi.databinding.FragmentHitungBinding
import org.d3if2146.hitungbmi.setupBtnOnLongClickListener
import java.lang.Exception

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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnListeners()
        hitungViewModel = ViewModelProvider(requireActivity())[HitungViewModel::class.java]
        requireActivity().setupBtnOnLongClickListener(binding.btnHitung)
        requireActivity().setupBtnOnLongClickListener(binding.btnReset)
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_hitungFragment_to_aboutFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupObservers() {
        hitungViewModel.hasilBmi.observe(requireActivity()) {
            if (it != null) {
                showResult(it)
            }
        }
        hitungViewModel.navigasi.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            findNavController().navigate(
                HitungFragmentDirections.actionHitungFragmentToSaranFragment(it)
            )
            hitungViewModel.selesaiNavigasi()
        }
    }

    private fun setupBtnListeners() {
        binding.btnBagikan.setOnClickListener{
            shareData()
        }

        binding.btnLihatSaran.setOnClickListener{
            hitungViewModel.mulaiNavigasi()
        }

        binding.btnHitung.setOnClickListener{
            hitungBmi()
        }
        binding.btnReset.setOnClickListener{
            binding.etBb.clearFocus()
            binding.etTb.clearFocus()
            reset()
        }
    }

    private fun shareData() {
        val selectedId = binding.rgGender.checkedRadioButtonId
        val gender = if (selectedId == R.id.rbPria)
            getString(R.string.pria)
        else {
            getString(R.string.wanita)
        }
        val msg = getString(R.string.bagikan_template,binding.etBb.text,binding.etTb.text,gender,binding.tvBmi.text,binding.tvKategori.text)
        val shareIntent = Intent(Intent.ACTION_SEND)
        with(shareIntent){
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT,msg)
        }
        try {
            startActivity(shareIntent)
        }catch (e: Exception){
            Log.e("INTENT SEND", e.toString())
            return
        }
    }

    private fun hitungBmi() {
        val berat = binding.etBb.text.toString()
        val tinggi = binding.etTb.text.toString()
        val selectedId = binding.rgGender.checkedRadioButtonId

        if(TextUtils.isEmpty(berat)){
            Toast.makeText(context, getString(R.string.berat_invalid), Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(tinggi)){
            Toast.makeText(context, getString(R.string.tinggi_invalid), Toast.LENGTH_SHORT).show()
            return
        }
        if(selectedId == -1){
            Toast.makeText(context, getString(R.string.gender_invalid), Toast.LENGTH_SHORT).show()
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
        binding.buttonGroup.visibility = View.VISIBLE
    }

    private fun reset() {
        binding.btnReset.visibility = View.GONE
        binding.tvBmi.visibility = View.GONE
        binding.tvKategori.visibility = View.GONE
        binding.divider.visibility = View.GONE
        binding.buttonGroup.visibility = View.GONE
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