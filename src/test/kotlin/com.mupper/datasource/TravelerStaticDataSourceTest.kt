package com.mupper.datasource

import com.mupper.sharedtestcode.fakeTraveler
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

class TravelerStaticDataSourceTest {
    private lateinit var travelerStaticDataSource: TravelerStaticDataSource

    @Before
    fun setUp() {
        travelerStaticDataSource = TravelerStaticDataSource()
    }

    @Test
    fun `getTravelerCount should return 1`() {
        runBlocking {
            // Given
            // When
            val travelerCount = travelerStaticDataSource.getTravelerCount()

            // Then
            assertThat(travelerCount, `is`(1))
        }
    }

    @Test
    fun `findTravelerByEmail should return fakeTraveler given any string`() {
        runBlocking {
            // Given
            // When
            val findTravelerByEmail = travelerStaticDataSource.findTravelerByEmail("")

            // Then
            assertThat(findTravelerByEmail, `is`(fakeTraveler))
        }
    }
}