package br.com.dfn.starwarskotlin.core.model.source.remote

import android.util.Log
import br.com.dfn.starwarskotlin.core.model.Film
import br.com.dfn.starwarskotlin.core.model.source.FilmsDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by diegonascimento on 18/12/17.
 */
class FilmsRemoteDataSource : DataSource(), FilmsDataSource<List<Film>> {

    private val webService = ServiceClient.retrofit!!.create(StarWarsApi::class.java)

    override fun onFilmsLoaded(compositeDisposable: CompositeDisposable, success: (p: List<Film>) -> Unit, fail: () -> Unit) {
        compositeDisposable.add(
                webService.getFilms()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { films ->
                                    films.results.forEach { Log.d("Main", "onFilmsLoaded/Films: " + it.title) }
                                    success(films.results)
                                },
                                { t: Throwable -> this.handleError(t) },
                                { Log.d("Main", "OnComplete") }))
    }

    override fun handleError(t: Throwable) {
        super.handleError(t)
        Log.d("Main", "handleError : Internal")
    }
}