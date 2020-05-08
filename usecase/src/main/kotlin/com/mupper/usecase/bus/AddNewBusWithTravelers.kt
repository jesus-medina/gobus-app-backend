package com.mupper.usecase.bus

import com.mupper.data.repository.BusRepository
import com.mupper.domain.bus.Bus
import com.mupper.usecase.traveler.GetActualTraveler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddNewBusWithTravelers(
    private val getActualTraveler: GetActualTraveler,
    private val busRepository: BusRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(bus: Bus) = withContext(dispatcher) {
        val traveler = withContext(dispatcher) { getActualTraveler.invoke(bus.path) }
        traveler?.let {
            withContext(dispatcher) { busRepository.addNewBusWithTravelers(bus, it) }
        }
    }
}