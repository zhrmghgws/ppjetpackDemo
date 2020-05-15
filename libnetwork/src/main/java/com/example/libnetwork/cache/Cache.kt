package com.example.libnetwork.cache

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.libcommon.PoKo
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cache")
data class Cache(@PrimaryKey(autoGenerate = false) var key:String, var data:ByteArray)