package com.toharifqi.jetheroes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toharifqi.jetheroes.data.HeroRepository
import com.toharifqi.jetheroes.model.Hero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetHeroesViewModel(private val repository: HeroRepository) : ViewModel() {
    private val mutableGroupedHeroes = MutableStateFlow(
        repository.getHeroes()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupedHeroes: StateFlow<Map<Char, List<Hero>>> get() = mutableGroupedHeroes

    private val mutableQuery = mutableStateOf("")
    val query: State<String> get() = mutableQuery

    fun search(newQuery: String) {
        mutableQuery.value = newQuery
        mutableGroupedHeroes.value = repository.searchHeroes(mutableQuery.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}

class ViewModelFactory(private val repository: HeroRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetHeroesViewModel::class.java)) {
            return JetHeroesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
