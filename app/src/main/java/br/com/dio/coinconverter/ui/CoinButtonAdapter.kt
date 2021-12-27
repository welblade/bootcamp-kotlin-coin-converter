package br.com.dio.coinconverter.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.databinding.ItemCoinButtonBinding
import com.bumptech.glide.Glide
import kotlin.properties.Delegates

class CoinButtonAdapter : ListAdapter<Coin, CoinButtonAdapter.ViewHolder>(DiffCallBack()) {
    private var selectedButtonId: Int = 0
    private var selectedViewHolder: CoinButtonAdapter.ViewHolder? = null
    private val changeCoinListeners = mutableListOf< (Coin)->Unit >()
    private var _selectedCoin: Coin by Delegates.observable(Coin.BRL){
            _,_, newCoin -> changeCoinListeners.forEach { it(newCoin) }
    }

    fun onChangeCoinListener(init:(Coin)->Unit){
        changeCoinListeners.add(init)
    }

    fun getSelectedCoin():Coin{
        return _selectedCoin
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinButtonBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = getItem(position)
        return holder.bind(coin, selectedButtonId == position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ViewHolder(
        private val binding: ItemCoinButtonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: Coin, isSelected: Boolean) {
            Glide.with(binding.root.context)
                .load(Uri.parse(coin.iconUrl))
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivIcon)
            binding.tvAbbrName.text = coin.name
            setChecked(isSelected)
            if(isSelected && selectedViewHolder == null)  makeThisButtonChecked(coin)
            binding.root.setOnClickListener {
                if(!it.isActivated) {
                    makeThisButtonChecked(coin)
                }
            }
        }
        private fun makeThisButtonChecked(coin: Coin){
            selectedButtonId = absoluteAdapterPosition
            _selectedCoin = coin
            selectedViewHolder?.setChecked(false)
            selectedViewHolder = this
            setChecked(true)
        }
        private fun setChecked(value: Boolean){
            binding.root.isChecked = value
            binding.root.isActivated = value
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Coin>() {
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
        return oldItem.name == newItem.name
    }

}
