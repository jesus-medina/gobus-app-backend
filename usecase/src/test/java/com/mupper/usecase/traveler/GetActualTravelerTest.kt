package com.mupper.usecase.traveler

import com.mupper.data.repository.TravelerRepository
import com.mupper.sharedtestcode.fakeTraveler
import com.mupper.sharedtestcode.fakeTravelingPath
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class GetActualTravelerTest {
    @MockK
    lateinit var travelerRepository: TravelerRepository

    lateinit var getActualTraveler: GetActualTraveler

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        getActualTraveler = GetActualTraveler(travelerRepository)
    }

    @Test
    fun `invoke should return expected traveler from retrieveActualTraveler`() {
        runBlocking {
            // GIVEN
            coEvery { travelerRepository.findTravelerByEmail(any()) } returns fakeTraveler

            // WHEN
            val actualTraveler = getActualTraveler("")

            // THEN
            assertThat(actualTraveler, `is`(fakeTraveler))
        }
    }
}