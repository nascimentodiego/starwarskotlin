package br.com.dfn.starwarskotlin.home.presenter

import br.com.dfn.starwarskotlin.core.model.Film
import br.com.dfn.starwarskotlin.base.presenter.BasePresenter

/**
 * Created by diegonascimento on 18/12/17.
 */
class HomeContracts {

    interface View {
        fun showError(message: String)
        fun showSuccess(films: List<Film>?)
    }

    interface Presenter : BasePresenter {
        fun loadFilms()
    }
}