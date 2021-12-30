package br.com.dio.coinconverter.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.coinconverter.R
import br.com.dio.coinconverter.core.extensions.format
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.data.model.ExchangeResponseValue
import br.com.dio.coinconverter.databinding.ItemExchangeRateBinding
import com.bumptech.glide.Glide
import kotlin.properties.Delegates

class ExchangeRateItemAdapter :
    ListAdapter<ExchangeResponseValue, ExchangeRateItemAdapter.ViewHolder>(DiffCallBack()) {

    private val changeCoinListeners = mutableListOf< ()->Unit >()
    private var valueToConvert: Double by Delegates.observable(0.0) {
        _,_,_ -> changeCoinListeners.forEach { it() }
    }

    fun convertExchange(value: Double){
        valueToConvert = value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExchangeRateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemExchangeRateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExchangeResponseValue) {
            binding.tvAbbrName.text = item.codein
            binding.tvName.text = item.name.split("/").last()
            setConvertedValue(item)
            setExchangeRate(item)
            setFlag(item)
            changeCoinListeners.add {
                setConvertedValue(item)
            }
        }
        private fun setFlag(item: ExchangeResponseValue){
            Glide.with(binding.root.context)
                .load(Uri.parse(Coin.getByName(item.codein).iconUrl))
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivIcon)
        }
        private fun setConvertedValue(item: ExchangeResponseValue) {
            val convertedValue = item.bid * valueToConvert
            binding.tvConvertedValue.text = "${convertedValue.format(2)} ${item.codein}"
        }

        private fun setExchangeRate(item: ExchangeResponseValue) {
            val text: String = binding.root.context.getString(
                R.string.exchange_rate_formated,
                item.code,
                item.codein,
                item.bid.toString()
            )
            binding.tvRateValue.text = text
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<ExchangeResponseValue>() {
        override fun areItemsTheSame(
            oldItem: ExchangeResponseValue,
            newItem: ExchangeResponseValue
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ExchangeResponseValue,
            newItem: ExchangeResponseValue
        ): Boolean {
            return oldItem.codein == newItem.codein &&
                    oldItem.code == newItem.code &&
                    oldItem.createDate == newItem.createDate
        }

    }
}