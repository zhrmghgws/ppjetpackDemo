package lchplayer.com.lichao.ppjock.Base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libcommon.EmptyView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import lchplayer.com.lichao.ppjock.R
import lchplayer.com.lichao.ppjock.databinding.LayoutRefreshViewBinding
import java.lang.reflect.ParameterizedType

abstract class AbsListFragment<T,M: AbsViewModel<T>> : Fragment(), OnRefreshListener,
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
        mRecyclerView.adapter= adapter

        mRecyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        mRecyclerView.itemAnimator=null

        var decoration=DividerItemDecoration(context,LinearLayoutManager.VERTICAL)
        decoration.setDrawable(context!!.getDrawable(R.drawable.list_divider)!!)
        mRecyclerView.addItemDecoration(decoration)

        afterCreateView()
        return binding.root
    }

    abstract fun afterCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        if(arguments.size>1){
            var argument=arguments[1]
            val modelClaz = (argument as Class<*>).asSubclass(AbsViewModel::class.java)
            mViewModel=ViewModelProviders.of(this).get(modelClaz) as M
            //mViewModel=ViewModelProvider.NewInstanceFactory().create(modelClaz) as M
            println("AbsListFragment::::::onViewCreated11111")
            mViewModel.pageData?.observe(viewLifecycleOwner,
                Observer<PagedList<T>> { t ->
                    println("ovsever:::T:::${t.size}")
                    adapter.submitList(t)

                })
            mViewModel.bounaryPageData.observe(viewLifecycleOwner,
                Observer<Boolean> { t -> finishRefresh(t) })
        }
    }

    abstract fun getMAdapter():PagedListAdapter<T,RecyclerView.ViewHolder>

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