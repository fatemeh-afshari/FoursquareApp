package com.example.cafebazaartest.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venues")
data class VenueCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "lat")
    var lat: Double,

    @ColumnInfo(name = "lon")
    var lon: Double,

    @ColumnInfo(name = "phone")
    var phone: String?,
    @ColumnInfo(name = "address")
    var address: String
) {


    companion object {

        fun nullTitleError(): String {
            return "You must enter a title."
        }

        fun nullIdError(): String {
            return "NoteEntity object has a null id. This should not be possible. Check local database."
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VenueCacheEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (lat != other.lat) return false
        if (lon != other.lon) return false
        if (phone != other.phone) return false
        if (address != other.address) return false


        return true
    }

}
