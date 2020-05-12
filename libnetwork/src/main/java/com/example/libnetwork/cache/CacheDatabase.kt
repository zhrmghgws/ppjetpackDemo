package com.example.libnetwork.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.libcommon.AppGlobals

@Database(entities = [Cache::class],version=1,exportSchema =true)
abstract class CacheDatabase : RoomDatabase() {
    companion object{
        private var database:CacheDatabase?=null
//        init {
//            database= Room.databaseBuilder(
//                AppGlobals.getApplication(),
//                CacheDatabase::class.java,
//                "ppjoke_cache"
//            )
//                .allowMainThreadQueries()
//                //.addCallback()
//                //设置查询的线程池
//                //.setQueryExecutor()
//                //.openHelperFactory()
//                //room的日志模式
//                //.setJournalMode()
//                //数据库升级异常之后的回滚
//                //.fallbackToDestructiveMigration()
//                //数据库升级异常后根据指定版本进行回滚
//                //.fallbackToDestructiveMigrationFrom()
//                //数据库升级的时候必备,不然会删除旧库,并重建
//                // .addMigrations(CacheDatabase.sMigration)
//                .build()
//        }
        fun getInstance():CacheDatabase{
            if(database==null){
                synchronized(CacheDatabase::class.java){
                    database=Room.databaseBuilder(AppGlobals.getApplication()
                        ,CacheDatabase::class.java
                        ,"ppjoke_cache")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return database!!
        }

//        var sMigrations=object :Migration(1,3){
//            override fun migrate(database: SupportSQLiteDatabase) {
//                TODO("Not yet implemented")
//            }
//
//        }


    }
    abstract fun cacheDao():CacheDao

}