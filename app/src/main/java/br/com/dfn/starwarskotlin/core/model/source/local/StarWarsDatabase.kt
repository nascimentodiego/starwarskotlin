package br.com.dfn.starwarskotlin.core.model.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import br.com.dfn.starwarskotlin.core.model.Film

/**
 * Created by diegonascimento on 20/12/17.
 */
@Database(entities = arrayOf(Film::class), version = 1, exportSchema = false)
abstract class StarWarsDatabase : RoomDatabase() {

    /*//////////////////////////////////////////////////////////
   // ABSTRACT METHODS
   *///////////////////////////////////////////////////////////
    abstract fun filmsDao(): FilmsDao

    /*//////////////////////////////////////////////////////////
    // COMPANION OBJECT
    *///////////////////////////////////////////////////////////
    companion object {
        val DB_NAME = "starwars_db"
        var dbInstance: StarWarsDatabase? = null

        fun getDatabase(context: Context): StarWarsDatabase? {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder<StarWarsDatabase>(context.applicationContext, StarWarsDatabase::class.java, DB_NAME).build()
            }
            return dbInstance
        }
    }
}