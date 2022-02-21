package com.cotters.spacexapp.launches.domain.model

class LaunchLinksDomainModel(
    val patchImageUrl: String?,
    val article: String?,
    val wikipedia: String?,
    val webcast: String?,
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is LaunchLinksDomainModel -> {
                this.patchImageUrl == other.patchImageUrl
                        && this.article == other.article
                        && this.wikipedia == other.wikipedia
                        && this.webcast == other.webcast
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = patchImageUrl?.hashCode() ?: 0
        result = 31 * result + (article?.hashCode() ?: 0)
        result = 31 * result + (wikipedia?.hashCode() ?: 0)
        result = 31 * result + (webcast?.hashCode() ?: 0)
        return result
    }
}
