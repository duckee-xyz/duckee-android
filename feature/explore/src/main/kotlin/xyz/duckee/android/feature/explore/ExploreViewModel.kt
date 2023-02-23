package xyz.duckee.android.feature.explore

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import xyz.duckee.android.feature.explore.contract.ExploreState

@HiltViewModel
internal class ExploreViewModel @Inject constructor(

) : ViewModel(), ContainerHost<ExploreState, Unit> {

    override val container = container<ExploreState, Unit>(ExploreState())

    @OptIn(OrbitExperimental::class)
    fun onSearchValueChanged(value: String) = blockingIntent {
        reduce { state.copy(searchValue = value) }
    }
}
