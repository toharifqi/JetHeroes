package com.toharifqi.jetheroes.data

import com.toharifqi.jetheroes.model.Hero
import com.toharifqi.jetheroes.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero> {
        return HeroesData.heroes
    }
}
