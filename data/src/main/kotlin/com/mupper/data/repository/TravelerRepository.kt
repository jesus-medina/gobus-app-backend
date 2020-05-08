package com.mupper.data.repository

import com.mupper.data.source.local.TravelerLocalDataSource
import com.mupper.data.source.remote.TravelerRemoteDataSource
import com.mupper.domain.LatLng
import com.mupper.domain.traveler.Traveler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface TravelerRepository {
    suspend fun retrieveActualTraveler(travelingPath: String): Traveler?
    suspend fun findTravelerByEmail(email: String): Traveler?
    suspend fun shareActualLocation(travelerInBus: Traveler)
}

@Deprecated("This implementation was created for Android projects")
private class TravelerRepositoryImpl(
        private val actualEmail: String,
        private val travelerLocalDataSource: TravelerLocalDataSource,
        private val travelerRemoteDataSource: TravelerRemoteDataSource,
        private val dispatcher: CoroutineDispatcher
) : TravelerRepository {
    override suspend fun retrieveActualTraveler(travelingPath: String): Traveler? {
        val travelerCount = withContext(dispatcher) { travelerLocalDataSource.getTravelerCount() }
        if (travelerCount == 0) {
            val remoteTraveler = withContext(dispatcher) {
                travelerRemoteDataSource.findTravelerByEmail(actualEmail)
                        ?: addStaticTraveler()
            }

            withContext(dispatcher) { travelerLocalDataSource.insertTraveler(travelingPath, remoteTraveler) }
        }
        return withContext(dispatcher) { travelerLocalDataSource.findTravelerByEmail(actualEmail) }
    }

    override suspend fun findTravelerByEmail(email: String): Traveler? {
        TODO("Not yet implemented")
    }

    override suspend fun shareActualLocation(travelerInBus: Traveler) {
        withContext(dispatcher) { travelerLocalDataSource.shareActualLocation(travelerInBus) }
        withContext(dispatcher) { travelerRemoteDataSource.shareActualLocation(travelerInBus) }
    }

    private suspend fun addStaticTraveler(): Traveler = travelerRemoteDataSource.addTraveler(
            Traveler(
                    actualEmail,
                    LatLng(
                            0.0,
                            0.0
                    ),
                    false
            )
    )
}

class TravelerRepositoryDerived(
        actualEmail: String,
        travelerLocalDataSource: TravelerLocalDataSource,
        travelerRemoteDataSource: TravelerRemoteDataSource,
        dispatcher: CoroutineDispatcher
) : TravelerRepository by TravelerRepositoryImpl(
        actualEmail,
        travelerLocalDataSource,
        travelerRemoteDataSource,
        dispatcher
)

private class TravelerRepositoryNewImpl(
        private val travelerLocalDataSource: TravelerLocalDataSource,
        private val dispatcher: CoroutineDispatcher
) : TravelerRepository {
    override suspend fun retrieveActualTraveler(travelingPath: String): Traveler? {
        TODO("Not yet implemented")
    }

    override suspend fun findTravelerByEmail(email: String): Traveler? = withContext(dispatcher) {
        travelerLocalDataSource.findTravelerByEmail(email)
    }

    override suspend fun shareActualLocation(travelerInBus: Traveler) {
        TODO("Not yet implemented")
    }

}

class TravelerRepositoryNewDerived(
        travelerLocalDataSource: TravelerLocalDataSource,
        dispatcher: CoroutineDispatcher
) : TravelerRepository by TravelerRepositoryNewImpl(
        travelerLocalDataSource,
        dispatcher
)