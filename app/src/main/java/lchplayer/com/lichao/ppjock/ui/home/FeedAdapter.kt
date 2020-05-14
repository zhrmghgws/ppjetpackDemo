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
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean= oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean =oldItem == newItem

}) {
    var inflater:LayoutInflater

    init {
        println("FeedAdapter::::init")
        inflater= LayoutInflater.from(context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding:ViewDataBinding
        println("viewType::::${viewType}")
        if(viewType==1){
            binding=LayoutFeedTypeImageBinding.inflate(inflater)
            println("viewType11111::::${viewType}")
        }else{
            binding=LayoutFeedTypeVideoBinding.inflate(inflater)
            println("viewType22222::::${viewType}")
        }
        return ViewHolder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("viewType::::author::::${getItem(position)?.author}")
        getItem(position)?.let { holder.bindData(it) }
    }

    override fun getItemViewType(position: Int): Int = getItem(position)!!.itemType

    inner class ViewHolder(itemView: View, var binding: ViewDataBinding) : RecyclerView.ViewHolder(itemView) {
        fun bindData(item: Feed) {
            println("FeedAdapter:::ViewHolder:bindData")
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