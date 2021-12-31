package com.nsa.imagebrowsingapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nsa.imagebrowsingapp.R
import com.nsa.imagebrowsingapp.databinding.ImageHomeRvItemOneBinding
import com.nsa.imagebrowsingapp.databinding.ImageHomeRvItemTwoBinding
import com.nsa.imagebrowsingapp.adapters.OnClickListener
import com.nsa.imagebrowsingapp.models.image_model

class HomeImagesAdapter(private val listener: OnClickListener): PagingDataAdapter<image_model,RecyclerView.ViewHolder>(
    IMAGE_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == item_1){
            val binding = ImageHomeRvItemOneBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ImageViewHolderOne(binding)
        }else{
            val binding = ImageHomeRvItemTwoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ImageViewHolderTwo(binding)
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem=getItem(position)
        if(currentItem != null){
            if(getItemViewType(position)== item_1){
                (holder as ImageViewHolderOne).bind(currentItem)
            }else{
                (holder as ImageViewHolderTwo).bind(currentItem)
            }

        }
    }


    override fun getItemViewType(position: Int): Int {


            if((position+1)%3!=0){
                return item_1
            }else{
                if((position+1)==3 || (position+1)%12==0){
                return item_2
                }else{
                    return item_1
                }
                }

    }

    private var hdquality:Boolean=false
    fun quality(quality: Boolean) {
        this.hdquality=quality
    }


    inner class ImageViewHolderOne(private val binding: ImageHomeRvItemOneBinding) :
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val position=bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item =getItem(position)
                    if(item!=null){
                        listener.onClick(item)
                    }
                }
            }
        }

        fun bind(imageModel: image_model) {

            var url=""
            if(hdquality){
                url=imageModel.largeImageURL
            }else{
                url=imageModel.previewURL
            }

            binding.apply {
                Glide.with(itemView)
                    .load(url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
            }
        }

    }
   inner class ImageViewHolderTwo(private val binding: ImageHomeRvItemTwoBinding) :
        RecyclerView.ViewHolder(binding.root){

       init {
           binding.root.setOnClickListener {
               val position=bindingAdapterPosition
               if(position != RecyclerView.NO_POSITION){
                   val item =getItem(position)
                   if(item!=null){
                       listener.onClick(item)
                   }
               }
           }
       }

        fun bind(imageModel: image_model) {
            var url=""
            if(hdquality){
                url=imageModel.largeImageURL
            }else{
                url=imageModel.previewURL
            }
           binding.apply {
                Glide.with(itemView)
                    .load(url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
            }
        }

    }
    companion object{

        private const val item_1=101
        private const val item_2=102
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<image_model>(){
            override fun areItemsTheSame(oldItem: image_model, newItem: image_model)=
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: image_model, newItem: image_model)=
                oldItem == newItem
        }
    }
}