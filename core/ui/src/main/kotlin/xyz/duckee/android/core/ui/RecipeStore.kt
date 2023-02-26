package xyz.duckee.android.core.ui

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Singleton
class RecipeStore @Inject constructor() {

    private val _recipeState = MutableStateFlow<Map<String, Any?>>(emptyMap())
    val recipeState = _recipeState.asStateFlow()

    fun saveTemporaryRecipeProperty(property: Map<String, Any?>) {
        _recipeState.update { property }
    }

    fun clearTemporaryRecipeProperty() {
        _recipeState.update { emptyMap() }
    }
}
