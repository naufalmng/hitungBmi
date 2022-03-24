package org.d3if2146.hitungbmi

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import org.d3if2146.hitungbmi.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupBtnListeners()
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
        if(TextUtils.isEmpty(berat)){
            Toast.makeText(this, getString(R.string.berat_invalid), Toast.LENGTH_SHORT).show()
            return
        }
        val tinggi = binding.etTb.text.toString()
        if(TextUtils.isEmpty(berat)){
            Toast.makeText(this, getString(R.string.tinggi_invalid), Toast.LENGTH_SHORT).show()
            return
        }

        val cmTinggi = tinggi.toFloat() / 100
        val bmi = berat.toFloat() / (cmTinggi * cmTinggi)


        val selectedId = binding.rgGender.checkedRadioButtonId
        if(selectedId == -1){
            Toast.makeText(this, getString(R.string.gender_invalid), Toast.LENGTH_SHORT).show()
            return
        }
        val isMale = selectedId == R.id.rbPria
        val kategori = getKategori(bmi,isMale)

        setResult(bmi,kategori)

    }

    private fun reset() {
        binding.btnReset.visibility = View.GONE
        binding.tvBmi.visibility = View.GONE
        binding.tvKategori.visibility = View.GONE
        binding.divider.visibility = View.GONE
        binding.etBb.text.clear()
        binding.etTb.text.clear()
        binding.tvBmi.text = null
        binding.tvKategori.text = null
        binding.rgGender.clearCheck()
    }

    private fun setResult(bmi: Float,kategori: String) {
        binding.tvBmi.visibility = View.VISIBLE
        binding.tvKategori.visibility = View.VISIBLE
        binding.divider.visibility = View.VISIBLE
        binding.tvBmi.text = getString(R.string.bmi,bmi)
        binding.tvKategori.text = getString(R.string.kategori,kategori)
        binding.btnReset.visibility = View.VISIBLE
    }

    private fun getKategori(bmi: Float, isMale: Boolean): String {
        val stringRes = if (isMale) {
            when {
                bmi < 20.5 -> R.string.kurus
                bmi >= 27 -> R.string.gemuk
                else -> R.string.ideal
            }
        } else{
                when{
                    bmi < 18.5 -> R.string.kurus
                    bmi >= 25 -> R.string.gemuk
                    else -> R.string.ideal
                }
            }
        return getString(stringRes)
    }
}