package com.nramos.internationalbusinessmen.ui.screens.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.nramos.internationalbusinessmen.R
import com.nramos.internationalbusinessmen.databinding.FragmentTransactionDetailBinding
import com.nramos.internationalbusinessmen.delegates.collectInLifeCycle
import com.nramos.internationalbusinessmen.ui.common.adapters.TransactionsAdapter
import com.nramos.internationalbusinessmen.ui.screens.detail.viewmodel.TransactionDetailViewModel
import com.nramos.internationalbusinessmen.ui.screens.detail.viewmodel.Error
import com.nramos.internationalbusinessmen.ui.screens.detail.viewmodel.Loaded
import com.nramos.internationalbusinessmen.ui.screens.detail.viewmodel.Loading
import com.nramos.internationalbusinessmen.ui.screens.detail.viewmodel.State
import com.nramos.internationalbusinessmen.ui.screens.transaction.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.StringFormat

@AndroidEntryPoint
class TransactionDetailFragment : Fragment() {

    private var _binding: FragmentTransactionDetailBinding? =
        null //TODO futurible: change to delegates
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels() //Activity lifecycle shared viewModel
    private val transactionsDetailViewModel: TransactionDetailViewModel by viewModels() //Fragment lifecycle viewModel

    private val transactionsAdapter by lazy {
        TransactionsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupUI()
    }

    private fun setupObservers() {
        transactionsDetailViewModel.state.collectInLifeCycle(viewLifecycleOwner, ::renderState) //Collect states
    }

    private fun setupUI() = with(binding) {
        val filteredList = mainViewModel.getFilteredList()
        activity?.title = getString(R.string.transaction_detail_title, filteredList.first().sku)

        rvTransactions.adapter = transactionsAdapter
        transactionsAdapter.submitList(filteredList)
        transactionsDetailViewModel.getTotalAmountInEur(filteredList)
    }

    private fun renderState(state: State) {
        when (state) {
            is Error -> configErrorState(state)
            is Loaded -> configLoadedState(state)
            is Loading -> configLoadingState(state)
        }
    }

    private fun configLoadedState(state: Loaded) = with(binding) {
        tvTotalAmount.text = String.format("%.2f EUR", state.totalAmount)
    }

    private fun configLoadingState(state: Loading) = with(binding) {
        tvTotalAmount.text =  getString(R.string.calculation_loading_string)
    }

    private fun configErrorState(state: Error) = with(binding) {
        tvTotalAmount.text = getString(R.string.calculation_error_string)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}