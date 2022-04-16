package org.d3if2146.hitungbmi.ui.histori

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.d3if2146.hitungbmi.R
import org.d3if2146.hitungbmi.core.data.source.db.BmiDb
import org.d3if2146.hitungbmi.core.data.source.db.BmiEntity
import org.d3if2146.hitungbmi.databinding.FragmentDialogUpdateBinding
import org.d3if2146.hitungbmi.databinding.FragmentHistoryBinding
import org.d3if2146.hitungbmi.setupDialogTheme
import org.d3if2146.hitungbmi.ui.hitung.HistoriViewModelFactory


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var bmiTempData: BmiEntity? = null
    private lateinit var dialogBinding: FragmentDialogUpdateBinding
    private lateinit var dialog: AlertDialog
    private val historiAdapter: HistoriAdapter by lazy {
        HistoriAdapter()
    }

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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()
        onOptionMenuClick()
     }


    private suspend fun updateData() {
        val berat = dialogBinding.etBb.text.toString()
        val tinggi = dialogBinding.etTb.text.toString()
        val selectedId = dialogBinding.rgGender.rgGender.checkedRadioButtonId
        val id = historiViewModel.dataBmi?.id
        val date = historiViewModel.dataBmi?.tanggal

        if (id != null && date!= null) {
            historiViewModel.updateData(id = id, date = date,berat.toFloat(),tinggi.toFloat(),selectedId == R.id.rbPria)
        }
    }

    private fun sanityCheck(): Boolean {
        if(TextUtils.isEmpty(dialogBinding.etBb.text.toString())){
            Toast.makeText(context, getString(R.string.berat_invalid), Toast.LENGTH_SHORT).show()
            return false
        }
        if(TextUtils.isEmpty(dialogBinding.etTb.text.toString())){
            Toast.makeText(context, getString(R.string.tinggi_invalid), Toast.LENGTH_SHORT).show()
            return false
        }
        if(dialogBinding.rgGender.rgGender.checkedRadioButtonId == -1){
            Toast.makeText(context, getString(R.string.gender_invalid), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun onOptionMenuClick() {
        historiAdapter.setItemViewListener(object : ItemViewListener{
            override fun onItemClick(id: Int, dataBmi: BmiEntity) {
                historiViewModel.dataBmi = dataBmi
                when(id){
                    R.id.update_menu -> {
                        handleFragmentUpdateDialogView()
                    }
                    R.id.delete_menu -> {
                        showDialogHapusData(dataBmi)
                    }
                }
            }
        })
    }

    fun handleFragmentUpdateDialogView(){
        dialogBinding = FragmentDialogUpdateBinding.inflate(layoutInflater)
        DialogFragment().setupDialogTheme()
        dialog = AlertDialog.Builder(requireContext())
            .apply {
                setView(dialogBinding.root)
            }.create()
        dialog.show()

        with(dialogBinding){
            btnBatal.setOnClickListener{
                dialog.dismiss()
            }
            btnUpdate.setOnClickListener{
                viewLifecycleOwner.lifecycleScope.launch {
                    if (sanityCheck()){
                        updateData()
                        Toast.makeText(requireContext(), getString(R.string.data_berhasil_di_update), Toast.LENGTH_SHORT).show()
                        historiViewModel.dataBmi = null
                        dialog.dismiss()
                    }

                }
            }
        }
    }


    fun showDialogHapusData(dataBmi: BmiEntity){
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.konfirmasi_hapus_data))
            .setPositiveButton(getString(R.string.hapus)){ _,_ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    historiViewModel.hapusData(dataBmi)
                    Toast.makeText(requireContext(), getString(R.string.data_berhasil_di_hapus), Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.batal)){dialog,_->
                dialog.cancel()
            }.show()
    }
    fun showDialogUpdateData(dataBmi: BmiEntity){
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.konfirmasi_hapus_data))
            .setPositiveButton(getString(R.string.hapus)){ _,_ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    historiViewModel.hapusData(dataBmi)
                }
            }
            .setNegativeButton(getString(R.string.batal)){dialog,_->
                dialog.cancel()
            }.show()
    }



    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            this.adapter = historiAdapter
            setHasFixedSize(true)
        }
    }


    private fun setupObservers() {
        with(historiViewModel){
            listDataBmi.observe(viewLifecycleOwner){
                historiViewModel.setIsLoading(false)
                binding.emptyView.visibility = if(it.isEmpty())View.VISIBLE else View.GONE
                historiAdapter.submitList(it)
            }
            isLoading.observe(viewLifecycleOwner){
                if(!it){
                    binding.progressIndicator.visibility = View.GONE
                }else{
                    binding.progressIndicator.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_hapus -> {
                hapusSemuaData()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusSemuaData() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus_semua_data)
            .setPositiveButton(getString(R.string.hapus)){ _,_ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    historiViewModel.clearAllData()
                }
            }
            .setNegativeButton(getString(R.string.batal)){dialog,_->
                dialog.cancel()
            }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.histori_menu,menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}