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
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
    }
}