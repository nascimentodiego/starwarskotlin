package br.com.dfn.starwarskotlin.core.model.source.remote

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


/**
 * Created by diegonascimento on 18/12/17.
 */
class FilmsRemoteDataSource : DataSource(), FilmsDataSource<List<Film>> {

    private val webService = ServiceClient.retrofit!!.create(StarWarsApi::class.java)

/*    fun loadMoviesFull(): Observable<Movie> {
        return service.listMovies()
                .flatMap { filmResults -> Observable.from(filmResults.results) }
                .flatMap { film ->
                    val movieObj = Movie(film.title, film.episodeId, ArrayList<Character>())
                    Observable.zip(
                            Observable.just(movieObj),
                            Observable.from(film.personUrls)
                                    .flatMap { personUrl ->
                                        Observable.concat(
                                                getCache(personUrl),
                                                service.loadPerson(Uri.parse(personUrl).lastPathSegment)
                                                        .doOnNext { person ->
                                                            peopleCache.put(personUrl, person)
                                                        }
                                        ).first()
                                    }
                                    .map { person ->
                                        Character(person!!.name, person.gender)
                                    }.toList(),
                            { movie, characters ->
                                movie.characters.addAll(characters)
                                movie
                            })
                }
    }*/



    override fun onFilmsLoaded(compositeDisposable: CompositeDisposable, success: (p: List<Film>) -> Unit, fail: () -> Unit) {

        /*Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .flatMap {t -> Observable.just(t)}
                .map { t -> MeuNumber(t, " È Par ") }
//                .subscribe( ->   })
                .subscribe({ t: MeuNumber -> Log.d("Main", "T = " + t.number + " " + t.msg) }, { t: Throwable -> this.handleError(t) }, { Log.d("Main", "OnComplete") })
        // .flatMap({ t -> Observable.just(t) })*/

        compositeDisposable.add(
                webService.getFilms()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap { filmResults -> Observable.fromIterable(filmResults.results) }
                        .flatMap { film: Film ->
                            val movieObj = Movie(film.title, ArrayList())
                            Observable.zip(
                                    Observable.just(movieObj),
                                    Observable.fromIterable(film.characters)
                                            .flatMap {characterUrl ->
                                                webService.getCharacter(Uri.parse(characterUrl).lastPathSegment)}.toList(),
                                    { movie, characters ->
                                        movie.characters = characters
                                        movie
                                    }
                                    )
                        }

                /*.subscribe(
                        { films ->
                            films.results.forEach { Log.d("Main", "onFilmsLoaded/Films: " + it.title) }
                            success(films.results)
                        },
                        { t: Throwable -> this.handleError(t) },
                        { Log.d("Main", "OnComplete") })*/

        )
    }

    class MeuNumber(var number: Int, var msg: String)

    class Movie(var title: String, var characters: ArrayList<Person>)
    class Person(var name: String, var gender:String)

    override fun handleError(t: Throwable) {
        super.handleError(t)
        Log.d("Main", "handleError : Internal")
    }
}