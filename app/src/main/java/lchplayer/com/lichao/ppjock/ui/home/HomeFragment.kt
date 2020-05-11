package lchplayer.com.lichao.ppjock.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import lchplayer.com.lichao.libnavannotation.FragmentDestination
import lchplayer.com.lichao.ppjock.FixFragmentNavigator
import lchplayer.com.lichao.ppjock.R
import lchplayer.com.lichao.ppjock.model.Feed
import lchplayer.com.lichao.ppjock.view.AbsListFragment

@FragmentDestination("main/tabs/home",false,true)
class HomeFragment : AbsListFragment<Feed,HomeViewModel>() {

    private lateinit var homeViewModel: HomeViewModel


    override fun <T> getMAdapter(): PagedListAdapter<T, RecyclerView.ViewHolder> {
        var feedType=if(arguments==null) "all" else arguments!!.getString("feedType")
        return FeedAdapter(context!!,feedType!!) as PagedListAdapter<T, RecyclerView.ViewHolder>
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {

    }


    override fun afterCreateView() {
    }
}