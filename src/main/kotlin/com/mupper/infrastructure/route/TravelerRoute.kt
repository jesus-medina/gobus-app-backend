package com.mupper.infrastructure.route

import com.mupper.domain.traveler.Traveler
import com.mupper.usecase.traveler.GetActualTraveler
import io.ktor.application.ApplicationCall
import io.ktor.response.respond
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface Route {
    suspend operator fun invoke(call: ApplicationCall)
}

class TravelerRoute(
        private val getActualTraveler: GetActualTraveler,
        private val dispatcher: CoroutineDispatcher) : Route {
    override suspend fun invoke(call: ApplicationCall) {
        withContext(dispatcher) {
            val email = call.parameters["email"] ?: return@withContext call.respond(TravelerRouteResponse.Error.NoEmail)

            val actualTraveler = getActualTraveler(email) ?: return@withContext call.respond(TravelerRouteResponse.Error.NoTraveler)

            call.respond(TravelerRouteResponse.Success(actualTraveler))
        }
    }
}

sealed class TravelerRouteResponse(open val code: Int, open val message: String) {
    data class Success(val traveler: Traveler) : TravelerRouteResponse(200, "Traveler found successfully")
    sealed class Error(
            override val code: Int,
            override val message: String) : TravelerRouteResponse(code, message) {
        object NoEmail : Error(300, "Missing email parameter")
        object NoTraveler : Error(301, "Missing traveler on data stored")
    }
}