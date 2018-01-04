package br.com.dfn.starwarskotlin.home.presenter

import android.content.Context
import br.com.dfn.starwarskotlin.core.model.Film
import br.com.dfn.starwarskotlin.core.model.source.local.FilmsLocalDataSource
import br.com.dfn.starwarskotlin.core.model.source.remote.FilmsRemoteDataSource
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by diegonascimento on 18/12/17.
 */
class HomePresenter(private var ctx: Context, private var mView: HomeContracts.View) : HomeContracts.Presenter {

    var repository = FilmsRemoteDataSource()
    var mCompositeDisposable = CompositeDisposable()

    //Test
    var filmsLocalDataSource = FilmsLocalDataSource(ctx)

    override fun loadFilms() {
        repository.onFilmsLoaded(mCompositeDisposable,
                { success -> mView.showSuccess(success) },
                { mView.showError("FALHOUUU !") })

        /*
            var film1 = Film("STAR WARS 1", "Descrição do filme do star wars 1")
            var film2 = Film("STAR WARS 2", "Descrição do filme do star wars 2")
            var film3 = Film("STAR WARS 3", "Descrição do filme do star wars 3")
            var film4 = Film("STAR WARS 4", "Descrição do filme do star wars 4")
            var film5 = Film("STAR WARS 5", "Descrição do filme do star wars 5")
            var film6 = Film("STAR WARS 6", "Descrição do filme do star wars 6")

            var films = listOf(film1, film2, film3, film4, film5, film6)

            films.forEach { item -> filmsLocalDataSource.insertFilm(item) }
        */

    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
    }
}