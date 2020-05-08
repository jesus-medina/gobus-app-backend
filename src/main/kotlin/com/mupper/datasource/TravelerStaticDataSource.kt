package com.mupper.datasource

import com.mupper.data.source.local.TravelerLocalDataSource
import com.mupper.domain.traveler.Traveler
import com.mupper.sharedtestcode.fakeTraveler

class TravelerStaticDataSource : TravelerLocalDataSource {
    override suspend fun getTravelerCount(): Int = 1

    override suspend fun insertTraveler(travelingPath: String, traveler: Traveler) {
        TODO("Not yet implemented")
    }

    override suspend fun findTravelerByEmail(email: String): Traveler? = fakeTraveler

    override suspend fun shareActualLocation(traveler: Traveler) {
        TODO("Not yet implemented")
    }

}