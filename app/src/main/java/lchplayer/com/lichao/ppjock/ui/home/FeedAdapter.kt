package lchplayer.com.lichao.ppjock.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import lchplayer.com.lichao.ppjock.databinding.LayoutFeedTypeImageBinding
import lchplayer.com.lichao.ppjock.databinding.LayoutFeedTypeVideoBinding
import lchplayer.com.lichao.ppjock.model.Feed

class FeedAdapter(context:Context,var category:String) :PagedListAdapter<Feed,FeedAdapter.ViewHolder>(object :DiffUtil.ItemCallback<Feed>(){
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        TODO("Not yet implemented")
    }

}) {
    var inflater:LayoutInflater

    init {
        inflater= LayoutInflater.from(context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding:ViewDataBinding
        if(viewType==1){
            binding=LayoutFeedTypeImageBinding.inflate(inflater)
        }else{
            binding=LayoutFeedTypeVideoBinding.inflate(inflater)
        }
        return ViewHolder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position)!!)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)!!.itemType
    }

    inner class ViewHolder(itemView: View, var binding: ViewDataBinding) : RecyclerView.ViewHolder(itemView) {
        fun bindData(item: Feed) {
            if(binding is LayoutFeedTypeImageBinding){
                var imageBinding= binding as LayoutFeedTypeImageBinding
                imageBinding.feed=item
                imageBinding.feedImage.bindData(item.width,item.height,16,imageUrl = item.cover)
            }else{
                var videoBinding= binding as LayoutFeedTypeVideoBinding
                videoBinding.feed=item
                videoBinding.listPlayView.bindData(category,item.width,item.height,item.cover,item.url)
            }
        }

    }
}