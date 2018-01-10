package br.com.dfn.starwarskotlin.core.model.source.remote

import android.content.Context
import android.net.Uri
import android.util.Log
import br.com.dfn.starwarskotlin.core.model.Character
import br.com.dfn.starwarskotlin.core.model.Film
import br.com.dfn.starwarskotlin.core.model.source.FilmsDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import br.com.dfn.starwarskotlin.core.model.source.cache.CacheProviders
import io.rx_cache2.DynamicKey
import io.victoralbertos.jolyglot.GsonSpeaker
import io.rx_cache2.internal.RxCache


/**
 * Created by diegonascimento on 18/12/17.
 */
class FilmsRemoteDataSource(var mContext: Context) : DataSource(), FilmsDataSource<List<Film>> {

    private val webService = ServiceClient.retrofit!!.create(StarWarsApi::class.java)


    override fun onFilmsLoaded(compositeDisposable: CompositeDisposable, success: (p: List<Film>) -> Unit, fail: () -> Unit) {


        var cacheProviders = RxCache.Builder()
                .setMaxMBPersistenceCache(50)
                .persistence(mContext.getFilesDir(), GsonSpeaker())
                .using(CacheProviders::class.java)



        compositeDisposable.add(


                cacheProviders.getFilms(webService.getFilms())
                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap { filmResults -> Observable.fromIterable(filmResults.results) }
                        .flatMap { film: Film ->
                            val movieObj = Movie(film.title, ArrayList())

                            Observable.zip(
                                    Observable.just(movieObj),
                                    Observable.fromIterable(film.characters)
                                            .flatMap { characterUrl ->
                                                cacheProviders.getCharacter(webService.getCharacter(Uri.parse(characterUrl).lastPathSegment), DynamicKey(Uri.parse(characterUrl).lastPathSegment))
                                            }.toList().toObservable(),
                                    BiFunction<Movie, List<Character>, Movie> { movie, characters ->
                                        movie.apply {
                                            this.characters.addAll(characters)
                                            //2 - Planetas
                                            //3 - 
                                        }
                                    }
                            )
                        }.subscribe(
                        { movie ->
                            Log.d("Main", "Film: " + movie.title)
                            movie.characters.forEach { Log.d("Main", "Actor " + it.name) }
//                    success(films.results)
                        },
                        { t: Throwable -> this.handleError(t) },
                        { Log.d("Main", "OnComplete") })
        )
    }

    class MeuNumber(var number: Int, var msg: String)

    class Movie(var title: String, var characters: ArrayList<Character>)

    override fun handleError(t: Throwable) {
        super.handleError(t)
        Log.d("Main", "handleError : Internal")
    }
}