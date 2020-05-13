package lchplayer.com.lichao.ppjock.ui.home

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.example.libnetwork.ApiResponse
import com.example.libnetwork.ApiService
import com.example.libnetwork.JsonCallback
import com.example.libnetwork.Request
import com.google.gson.Gson
import lchplayer.com.lichao.ppjock.Base.AbsViewModel
import lchplayer.com.lichao.ppjock.model.Feed
import java.util.*
import com.google.gson.reflect.TypeToken

class HomeViewModel : AbsViewModel<Feed>() {

    @Volatile
    var witchCache:Boolean=true

    override fun createDataSource(): DataSource<Any?, Feed> {
        return mDataSource as DataSource<Any?, Feed>

    }

    var mDataSource=object :ItemKeyedDataSource<Int,Feed>(){
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Feed>
        ) {
            //加载初始化数据的
            loadData(0,callback)
            witchCache=false
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Feed>) {
            //加载分页数据的
            loadData(params.key,callback)
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Feed>) {
            callback.onResult(Collections.emptyList())
            //向前加载数据
        }

        override fun getKey(item: Feed): Int {
            return item.id
        }

    }

    private fun loadData(key: Int, callback: ItemKeyedDataSource.LoadCallback<Feed>) {
        //pageding框架的回调已经在子线程
        var request=ApiService.get<List<Feed>>("/feeds/queryHotFeedsList")
            .addParam("feedType", "")
            .addParam("userId", 0)
            .addParam("feedId", key)
            .addParam("pageCount", 10)
            .responseType(object :TypeToken<List<Feed>>(){}.type)

        if(witchCache){
            request.cacheStrategy(Request.CACHE_ONLY)
            request.execute(object : JsonCallback<List<Feed>>(){
                override fun onCacheSuccess(response: ApiResponse<List<Feed>>?){
                    Log.e("onCacheSuccess","onCacheSuccess:::"+response?.body?.size)
                }
            })
        }
        var newRequest=if(witchCache) { request.clone() } else request
        newRequest.cacheStrategy(if(key==0) Request.NET_CACHE else Request.NET_ONLY)
        val response = newRequest.execute()
        println("response.body:::::${Gson().toJson(response.body)}")
        var data=if(response.body==null) Collections.emptyList() else response.body
        callback.onResult(data!!)
        println("data.size:::::${data.size}")
        if(key>0){
            bounaryPageData.postValue(data.size>0)
        }
    }
}