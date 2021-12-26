package br.com.dio.coinconverter.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.databinding.ItemCoinButtonBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CoinButtonAdapter : ListAdapter<Coin, CoinButtonAdapter.ViewHolder>(DiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinButtonBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemCoinButtonBinding
        ):RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: Coin){
            Glide.with(binding.root.context)
                .load(Uri.parse(coin.iconUrl))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivIcon)
            binding.tvAbbrName.text = coin.name
        }
    }
}
class DiffCallBack : DiffUtil.ItemCallback<Coin>(){
    override fun areItemsTheSame(
        oldItem: Coin,
        newItem: Coin
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Coin,
        newItem: Coin
    ): Boolean {
        return oldItem == newItem
    }

}