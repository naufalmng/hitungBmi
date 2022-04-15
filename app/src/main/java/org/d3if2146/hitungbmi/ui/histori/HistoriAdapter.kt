package org.d3if2146.hitungbmi.ui.histori

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if2146.hitungbmi.R
import org.d3if2146.hitungbmi.core.data.source.db.BmiEntity
import org.d3if2146.hitungbmi.core.data.source.model.KategoriBmi
import org.d3if2146.hitungbmi.databinding.ItemHistoriBinding
import org.d3if2146.hitungbmi.hitungBmi
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter : ListAdapter<BmiEntity, HistoriAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(ItemHistoriBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HistoriAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(private val binding: ItemHistoriBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id","ID"))

        fun bind(item: BmiEntity) = with(binding) {
            val hasilBmi = item.hitungBmi()
            tvKategori.text = hasilBmi.kategori.toString().substring(0,1)
            val colorRes = when(hasilBmi.kategori){
                KategoriBmi.KURUS -> R.color.kurus
                KategoriBmi.IDEAL-> R.color.ideal
                else -> R.color.gemuk
            }
            val circleBg = tvKategori.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context,colorRes))


            tvTanggal.text = dateFormatter.format(Date(item.tanggal))
            val gender = root.context.getString(if(item.isMale)R.string.pria else R.string.wanita)
            tvData.text = root.context.getString(R.string.data_histori,item.berat,item.tinggi,gender)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<BmiEntity>() {
    override fun areItemsTheSame(oldItem: BmiEntity, newItem: BmiEntity): Boolean {
        return oldItem.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: BmiEntity, newItem: BmiEntity): Boolean {
       return oldItem == newItem
    }
}