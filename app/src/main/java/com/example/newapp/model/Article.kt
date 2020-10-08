package com.example.newapp.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "article")
@Parcelize
data class Article (
    @PrimaryKey
    @SerializedName("title")
    var heading : String,
    @Embedded
    @SerializedName("source")
    val source : Source? = null,
    @SerializedName("publishedAt")
    val published : String? = null,
    @SerializedName("description")
    val description : String? = null,
    @SerializedName("urlToImage")
    val image : String? = null
) : Parcelable