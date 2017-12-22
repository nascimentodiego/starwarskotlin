package br.com.dfn.starwarskotlin.core.model.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.com.dfn.starwarskotlin.core.model.Film
import io.reactivex.Flowable

/**
 * Created by diegonascimento on 20/12/17.
 */
@Dao
interface FilmsDao {
    /**
     * Select all films from the films table.
     *
     * @return all films.
     */
    @Query("SELECT * FROM films")
    fun getFilms(): Flowable<List<Film>>

    /**
     * Insert film.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: Film)

    /**
     * Delete all films.
     */
    @Query("DELETE FROM films")
    fun deleteFilms()
}