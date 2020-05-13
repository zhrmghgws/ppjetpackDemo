package lchplayer.com.lichao.ppjock.Base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

abstract class AbsViewModel<T> : ViewModel() {
    var dataSource :DataSource<Any?, T>?=null
    var factory=object :DataSource.Factory<Any?,T>(){
        override fun create(): DataSource<Any?, T> {
            dataSource=createDataSource()
            return dataSource!!
        }
    }
    var pageData:LiveData<PagedList<T>>?=null
    var bounaryPageData=MutableLiveData<Boolean>()
    var callback=object :PagedList.BoundaryCallback<T>(){
        override fun onItemAtEndLoaded(itemAtEnd: T) {
            bounaryPageData.postValue(false)
            //是否是空值,是否展示空布局
        }

        override fun onItemAtFrontLoaded(itemAtFront: T) {
            bounaryPageData.postValue(true)
            //当一条数据被加载时回调
        }

        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
            //当pagelist最后一天数据被加载时回调
        }
    }
    abstract fun createDataSource() :DataSource<Any?, T>
    init {
        var config=PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(12)
            .build()
      //      .setPrefetchDistance()这个默认是pagesize,既加载完10条会立马加载另外10条,所以初始化条数设置为12,不让他立马加载
        pageData=LivePagedListBuilder(factory,config)
                .setInitialLoadKey(0)
                .setBoundaryCallback(callback)
                .build()
    }

}