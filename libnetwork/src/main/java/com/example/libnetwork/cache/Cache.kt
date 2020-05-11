package com.example.libnetwork.cache

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.libnetwork.uitls.PoKo
import kotlinx.android.parcel.Parcelize

@PoKo
@Entity(tableName = "cache")
data class Cache(@PrimaryKey(autoGenerate = false) var key:String, var data:ByteArray)