package br.com.dfn.starwarskotlin.core.model.source.cache

import br.com.dfn.starwarskotlin.core.model.ResultFilms
import br.com.dfn.starwarskotlin.core.model.Character
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.LifeCache
import java.util.concurrent.TimeUnit


interface CacheProviders {

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    fun getFilms(observable: Observable<ResultFilms>): Observable<ResultFilms>

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    fun getCharacter(characters: Observable<Character>, key: DynamicKey): Observable<Character>

}