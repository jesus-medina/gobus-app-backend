package com.mupper.usecase.traveler

import com.mupper.data.repository.TravelerRepository
import com.mupper.domain.traveler.Traveler

class GetActualTraveler(
    private val travelerRepository: TravelerRepository
) {
    suspend operator fun invoke(email: String): Traveler? {
        val findTravelerByEmail = travelerRepository.findTravelerByEmail(email)
        return findTravelerByEmail
    }
}