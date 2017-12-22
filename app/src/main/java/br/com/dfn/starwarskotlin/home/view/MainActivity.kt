package br.com.dfn.starwarskotlin.home.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import br.com.dfn.starwarskotlin.R
import br.com.dfn.starwarskotlin.core.model.Film
import br.com.dfn.starwarskotlin.home.presenter.HomeContracts
import br.com.dfn.starwarskotlin.home.presenter.HomePresenter
import br.com.dfn.starwarskotlin.util.loadImage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), HomeContracts.View {

    private var mPresenter: HomeContracts.Presenter = HomePresenter(applicationContext, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView.loadImage(applicationContext, "https://cdn-images-1.medium.com/max/1089/1*GNAMTEPpaVZM6S06p3xPBg.jpeg")
    }

    override fun onResume() {
        super.onResume()
        mPresenter.loadFilms()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun showSuccess(films: List<Film>?) {
        films?.forEach {
            Log.d("Main", "Film: " + it.title)
        }

    }

    override fun showError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }


}
