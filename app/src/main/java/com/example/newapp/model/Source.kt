package com.example.newapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "source")
@Parcelize
data class Source(
    @PrimaryKey(autoGenerate = true)
    var key : Int = 0,
    @SerializedName("id")
    val id:String? = null,
    @SerializedName("name")
    val name : String? = null
) : Parcelable