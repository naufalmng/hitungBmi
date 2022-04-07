package org.d3if2146.hitungbmi

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.d3if2146.hitungbmi.core.data.source.model.HasilBmi
import org.d3if2146.hitungbmi.core.data.source.model.KategoriBmi
import org.d3if2146.hitungbmi.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupBtnListeners()
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        Activity().setupBtnOnLongClickListener(binding.btnHitung)
        Activity().setupBtnOnLongClickListener(binding.btnReset)
        setupObservers()
    }

    private fun setupObservers() {
        mainViewModel.hasilBmi.observe(this) {
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
            Toast.makeText(this, getString(R.string.berat_invalid), Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(tinggi)){
            Toast.makeText(this, getString(R.string.tinggi_invalid), Toast.LENGTH_SHORT).show()
            return
        }
        if(selectedId == -1){
            Toast.makeText(this, getString(R.string.gender_invalid), Toast.LENGTH_SHORT).show()
            return
        }
        mainViewModel.hitungBmi(berat,tinggi,selectedId == R.id.rbPria)
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

}
