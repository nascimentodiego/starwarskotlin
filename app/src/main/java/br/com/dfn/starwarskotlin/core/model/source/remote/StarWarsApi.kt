package br.com.dfn.starwarskotlin.core.model.source.remote

import br.com.dfn.starwarskotlin.core.model.Character
import br.com.dfn.starwarskotlin.core.model.ResultFilms
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by diegonascimento on 18/12/17.
 */
interface StarWarsApi {
    @GET("films")
    fun getFilms(): Observable<ResultFilms>

    @GET("people/{id}")
    fun getCharacter(@Path("id") id: Int): Observable<Character>
}