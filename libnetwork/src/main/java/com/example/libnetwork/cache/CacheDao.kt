package com.example.libnetwork.cache

import androidx.room.*

@Dao
abstract class BaseDao<T>{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    open abstract fun save(entry:T)
    @Update
    open abstract fun updata(entry: T)
    @Delete
    open abstract fun delete(entry:T)
}

@Dao
abstract class CacheDao : BaseDao<Cache>(){


    @Query("select *from cache where `key`=:key")
    abstract fun getCache(key:String):Cache




}