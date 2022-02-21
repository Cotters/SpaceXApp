package com.cotters.spacexapp.launches.domain.model

data class RocketDomainModel(
    val name: String,
    val type: String,
) {
    override fun equals(other: Any?): Boolean {
        return when(other) {
            is RocketDomainModel -> { name == other.name && type == other.type }
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
