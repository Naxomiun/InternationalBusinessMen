package com.nramos.internationalbusinessmen.ui.screens.transaction



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nramos.internationalbusinessmen.R
import com.nramos.internationalbusinessmen.databinding.FragmentTransactionsBinding
import com.nramos.internationalbusinessmen.delegates.collectInLifeCycle
import com.nramos.internationalbusinessmen.delegates.launchInLifeCycle
import com.nramos.internationalbusinessmen.ui.common.adapters.TransactionsAdapter
import com.nramos.internationalbusinessmen.ui.gone
import com.nramos.internationalbusinessmen.ui.screens.transaction.viewmodel.*
import com.nramos.internationalbusinessmen.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null //TODO futurible: change to delegates
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private val navController by lazy {
        findNavController()
    }

    private val transactionsAdapter by lazy {
        TransactionsAdapter { sku ->
            viewModel.navigateToDetail(sku)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Transaction list"

        setupRecyclers()
        setupObservers()
    }

    private fun setupRecyclers() = with(binding) {
        rvTransactions.adapter = transactionsAdapter
    }

    private fun setupObservers() {
        viewModel.state.collectInLifeCycle(viewLifecycleOwner, ::renderState) //Collect states
        viewModel.event.onEach(::launchEvent).launchInLifeCycle(viewLifecycleOwner) //Collect events
    }

    private fun launchEvent(event: Event) {
        when (event) {
            is NavigateToDetail -> loadTransactionDetail()
        }
    }

    private fun renderState(state: State) {
        when (state) {
            is Loaded -> configLoadedState(state)
            is Loading -> configLoadingState()
            is Error -> configErrorState(state)
        }
    }

    private fun configLoadedState(state: Loaded) = with(binding) {
        transactionsAdapter.submitList(state.transactions)
        tvErrorMessage.gone()
        rvTransactions.visible()
        progressCircular.gone()
    }

    private fun configErrorState(state: Error) = with(binding) {
        tvErrorMessage.text = state.error.message
        tvErrorMessage.visible()
        rvTransactions.gone()
        progressCircular.gone()
    }

    private fun configLoadingState() = with(binding) {
        tvErrorMessage.gone()
        rvTransactions.gone()
        progressCircular.visible()
    }

    private fun loadTransactionDetail() {
        navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}