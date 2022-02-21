package com.cotters.spacexapp.launches.domain.model

data class LaunchDomainModel(
    val name: String,
    val dateUnix: Long,
    val rocket: RocketDomainModel,
    val success: Boolean?,
    val links: LaunchLinksDomainModel,
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is LaunchDomainModel -> {
                this.name == other.name
                        && this.dateUnix == other.dateUnix
                        && this.success == other.success
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + dateUnix.hashCode()
        return result
    }
}
