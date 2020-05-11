package lchplayer.com.lichao.ppjock.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libcommon.EmptyView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import lchplayer.com.lichao.ppjock.AbsViewModel
import lchplayer.com.lichao.ppjock.R
import lchplayer.com.lichao.ppjock.databinding.LayoutRefreshViewBinding
import java.lang.reflect.ParameterizedType

abstract class AbsListFragment<T,M:AbsViewModel<T>> : Fragment(), OnRefreshListener,
    OnLoadMoreListener {
    lateinit var binding:LayoutRefreshViewBinding
    lateinit var mRecyclerView:RecyclerView
    lateinit var mRefreshLayout:SmartRefreshLayout
    lateinit var mEmptyView:EmptyView
    lateinit var adapter:PagedListAdapter<T,RecyclerView.ViewHolder>
    lateinit var mViewModel:M
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutRefreshViewBinding.inflate(inflater, container, false)
        mRecyclerView=binding.recyclerView
        mRefreshLayout=binding.refreshLayout
        mEmptyView=binding.emptyView

        mRefreshLayout.setEnableRefresh(true)
        mRefreshLayout.setEnableLoadMore(true)
        mRefreshLayout.setOnRefreshListener(this)
        mRefreshLayout.setOnLoadMoreListener(this)
        adapter= getMAdapter()
        mRecyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        mRecyclerView.itemAnimator=null
        var decoration=DividerItemDecoration(context,LinearLayoutManager.VERTICAL)
        decoration.setDrawable(context!!.getDrawable(R.drawable.list_divider)!!)
        mRecyclerView.addItemDecoration(decoration)
        mRecyclerView.adapter= adapter
        afterCreateView()
        return binding.root
    }

    abstract fun afterCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        if(arguments.size>0){
            var argument=arguments[1]
            val modelClaz = (argument as Class<*>).asSubclass(AbsViewModel::class.java)
            mViewModel=ViewModelProviders.of(this).get(modelClaz) as M
            mViewModel.pageData?.observe(this,
                Observer<PagedList<T>> { t -> adapter.submitList(t) })
            mViewModel.bounaryPageData.observe(this,
                Observer<Boolean> { t -> finishRefresh(t) })
        }
    }

    abstract fun <T> getMAdapter():PagedListAdapter<T,RecyclerView.ViewHolder>

    fun submitList(pagedList:PagedList<T>){
        if(pagedList.size>0){
            adapter.submitList(pagedList)
        }
        finishRefresh(pagedList.size>0)
    }

    fun finishRefresh(hasData:Boolean){
        val currentList = adapter.currentList
        var haveData = hasData || currentList !=null && currentList.size>0
        val state = mRefreshLayout.state
        if(state.isFooter && state.isOpening){
            mRefreshLayout.finishLoadMore()
        }else if(state.isHeader && state.isOpening){
            mRefreshLayout.finishRefresh()
        }

        if(haveData){
            mEmptyView.visibility=View.GONE
        }else{
            mEmptyView.visibility=View.VISIBLE
        }
    }
}