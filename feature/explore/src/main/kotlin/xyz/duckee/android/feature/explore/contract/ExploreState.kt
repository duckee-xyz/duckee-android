package xyz.duckee.android.feature.explore.contract

import androidx.compose.runtime.Immutable
import java.util.UUID
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Immutable
internal data class ExploreState(
    val isLoading: Boolean = true,
    val searchValue: String = "",
    val randomImages: ImmutableList<String> = List(100) {
        val uuid = UUID.randomUUID().toString().replace("-", "").lowercase()
        "https://picsum.photos/400/600?random=$uuid"
    }.toPersistentList()
)
