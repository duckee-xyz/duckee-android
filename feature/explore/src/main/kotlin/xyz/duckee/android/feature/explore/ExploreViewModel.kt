package xyz.duckee.android.feature.explore

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
internal class ExploreViewModel @Inject constructor() : ViewModel(), ContainerHost<Unit, Unit> {

    override val container = container<Unit, Unit>(Unit)

}
