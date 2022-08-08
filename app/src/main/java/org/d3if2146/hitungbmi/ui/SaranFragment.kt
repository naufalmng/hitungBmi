package org.d3if2146.hitungbmi.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import org.d3if2146.hitungbmi.R
import org.d3if2146.hitungbmi.core.data.source.model.KategoriBmi
import org.d3if2146.hitungbmi.databinding.FragmentSaranBinding
import java.lang.Exception

class SaranFragment : Fragment() {
    private var _binding: FragmentSaranBinding? = null
    private val binding get() = _binding!!
    private val args: SaranFragmentArgs by navArgs()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaranBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi(args.kategori)
     }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.saran_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_share -> {
                shareSaran()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareSaran() {

        val berat = binding.tvBb.text
        val tinggi = binding.tvTb.text
        val gender = binding.tvGender.text
        val saran = binding.textView.text

        val msg = getString(R.string.bagikan_saran_template,berat,tinggi,gender,"Kategori: ${args.kategori}\n","Saran:\n${saran}")
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

    private fun updateUi(kategori: KategoriBmi){
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when(kategori){
            KategoriBmi.KURUS -> {
                actionBar?.title = getString(R.string.judul_kurus)
                binding.imageView.setImageResource(R.drawable.kurus)
                binding.textView.text = getString(R.string.saran_kurus)
            }
            KategoriBmi.GEMUK -> {
                actionBar?.title = getString(R.string.judul_gemuk)
                binding.imageView.setImageResource(R.drawable.gemuk)
                binding.textView.text = getString(R.string.saran_gemuk)
            }
            KategoriBmi.IDEAL -> {
                actionBar?.title = getString(R.string.judul_ideal)
                binding.imageView.setImageResource(R.drawable.ideal)
                binding.textView.text = getString(R.string.saran_ideal)
            }

        }
        binding.tvBb.text = getString(R.string.berat,args.userInput.berat)
        binding.tvTb.text =  getString(R.string.tinggi,args.userInput.tinggi)
        binding.tvGender.text =  getString(R.string.gender,args.userInput.gender)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}