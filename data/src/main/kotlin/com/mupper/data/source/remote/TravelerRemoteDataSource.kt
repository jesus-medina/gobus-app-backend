package com.mupper.data.source.remote

import com.mupper.domain.traveler.Traveler

interface TravelerRemoteDataSource {
    suspend fun addTraveler(traveler: Traveler): Traveler

    suspend fun findTravelerByEmail(email: String): Traveler?

    suspend fun shareActualLocation(traveler: Traveler)
}