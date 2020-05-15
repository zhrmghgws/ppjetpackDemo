package lchplayer.com.lichao.ppjock.ui.home

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import lchplayer.com.lichao.libnavannotation.FragmentDestination
import lchplayer.com.lichao.ppjock.model.Feed
import lchplayer.com.lichao.ppjock.Base.AbsListFragment

@FragmentDestination("main/tabs/home",false,true)
class HomeFragment : AbsListFragment<Feed, HomeViewModel>() {



    override fun getMAdapter(): PagedListAdapter<Feed, RecyclerView.ViewHolder> {
        var feedType=if(arguments==null) "all" else arguments!!.getString("feedType")
        return FeedAdapter(context!!,feedType!!) as PagedListAdapter<Feed, RecyclerView.ViewHolder>
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {

    }

    override fun afterCreateView() {

    }
}