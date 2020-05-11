package com.example.libnetwork.cache

import androidx.room.*

@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun sava(cache: Cache):Long

    @Query("select *from cache where `key`=:key")
    fun getCache(key:String):Cache

    @Delete
    fun delete(cache:Cache):Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cache: Cache):Int
}