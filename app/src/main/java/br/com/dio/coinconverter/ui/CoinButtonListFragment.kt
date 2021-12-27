package br.com.dio.coinconverter.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.*
import br.com.dio.coinconverter.core.extensions.createProgressDialog
import br.com.dio.coinconverter.data.model.Coin
import br.com.dio.coinconverter.databinding.FragmentCoinButtonListBinding
import br.com.dio.coinconverter.presentation.CoinButtonListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinButtonListFragment : Fragment() {

    private var coinButtonListBinding: FragmentCoinButtonListBinding? = null
    private val coinButtonListViewModel by viewModel<CoinButtonListViewModel>()
    private val progress by lazy { layoutInflater.context.createProgressDialog() }
    private val coinButtonAdapter by lazy { CoinButtonAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        coinButtonListBinding = FragmentCoinButtonListBinding.inflate(
            inflater, container, false
        )
        return coinButtonListBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setViewModelObserver()
        coinButtonListViewModel.getCoinList()
    }

    private fun setRecyclerView() {
        coinButtonListBinding!!.rvCoinList.apply {
            adapter = coinButtonAdapter
        }
    }

    private fun setViewModelObserver() {
        coinButtonListViewModel.state.observe(viewLifecycleOwner){
            when(it){
                CoinButtonListViewModel.State.Empty -> {}
                CoinButtonListViewModel.State.Loading -> progress.show()
                is CoinButtonListViewModel.State.Error -> {
                    progress.dismiss()
                }
                is CoinButtonListViewModel.State.Success -> {
                    progress.dismiss()
                    coinButtonAdapter.submitList(it.coins)
                }
            }
        }
    }
    fun getSelectedButton():Coin{
        return coinButtonAdapter.getSelectedCoin()
    }
}