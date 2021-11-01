package com.nramos.internationalbusinessmen.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nramos.domain.model.Transaction
import com.nramos.internationalbusinessmen.databinding.ItemTransactionBinding

class TransactionsAdapter(private val callback: ((String) -> Unit)? = null) :
    ListAdapter<Transaction, TransactionsAdapter.TransactionViewHolder>(
        TransactionDiffCallback()) {

    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction, callback: ((String) -> Unit)?) = with(binding) {
            tvTransactionCurrency.text = transaction.currency
            tvTransactionAmount.text = transaction.amount.toString()
            tvTransactionSku.text = transaction.sku
            callback?.let {
                binding.root.setOnClickListener {
                    callback(transaction.sku)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TransactionViewHolder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction, callback)
    }

    class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem //We cant use SKU; many items shared it, we could have create and assign individual IDs.
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }

}