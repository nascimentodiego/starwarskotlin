package br.com.dfn.starwarskotlin.core.model.source.local

import android.content.Context
import android.util.Log
import br.com.dfn.starwarskotlin.core.model.Film
import br.com.dfn.starwarskotlin.core.model.source.FilmsDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by diegonascimento on 20/12/17.
 */
class FilmsLocalDataSource(var ctx: Context) : FilmsDataSource<List<Film>> {

    override fun onFilmsLoaded(compositeDisposable: CompositeDisposable, success: (p: List<Film>) -> Unit, fail: () -> Unit) {

        StarWarsDatabase.getDatabase(ctx.applicationContext)!!.filmsDao()
                .getFilms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { films ->
                            films.forEach { Log.d("Main", "onFilmsLoaded/Films: " + it.title) }
                            success(films)
                        },
                        { t: Throwable -> Log.d("Main", "Error: " + t.message) },
                        { Log.d("Main", "OnComplete") })
    }

    fun insertFilm(film: Film) {
        StarWarsDatabase.getDatabase(ctx.applicationContext)!!.filmsDao().insertFilm(film)
    }
}