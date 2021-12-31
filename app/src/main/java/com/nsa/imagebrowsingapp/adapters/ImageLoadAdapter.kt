package com.nsa.imagebrowsingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nsa.imagebrowsingapp.databinding.ImageLoadFooterBinding

class ImageLoadAdapter(private val retry: () -> Unit)
    :LoadStateAdapter<ImageLoadAdapter.LoadStateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ImageLoadFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

   inner class LoadStateViewHolder(private val binding: ImageLoadFooterBinding):
            RecyclerView.ViewHolder(binding.root){

        init {
            binding.buttonRetry.setOnClickListener {

                retry.invoke()
            }
        }
                fun bind(loadState: LoadState){
                    binding.apply {
                        footerProgress.isVisible = loadState is LoadState.Loading
                        buttonRetry.isVisible = loadState !is LoadState.Loading
                        textViewError.isVisible = loadState !is LoadState.Loading
                    }
                }
            }
}