package br.com.dfn.starwarskotlin.core.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by diegonascimento on 18/12/17.
 */
@Entity(tableName = "films")
class Film(title: String, description: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var mId: Int? = null

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String = title

    @SerializedName("description")
    @ColumnInfo(name = "description")
    var description: String = description

    @SerializedName("characters")
    @Ignore
    var characters: List<String> = ArrayList()
    @Ignore
    var mCharacters: MutableList<Character> = mutableListOf()

}