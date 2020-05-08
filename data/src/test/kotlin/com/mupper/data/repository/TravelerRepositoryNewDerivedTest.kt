package com.mupper.data.repository

import com.mupper.data.source.local.TravelerLocalDataSource
import com.mupper.sharedtestcode.fakeTraveler
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

class TravelerRepositoryNewDerivedTest {
    @MockK
    lateinit var mockTravelerLocalDataSource: TravelerLocalDataSource

    lateinit var travelerRepositoryNewDerived: TravelerRepositoryNewDerived

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        travelerRepositoryNewDerived = TravelerRepositoryNewDerived(
                mockTravelerLocalDataSource,
                Dispatchers.Unconfined
        )
    }

    @Test
    fun `findTravelerByEmail should return fakeTraveler given any email`() {
        runBlocking {
            // Given
            coEvery { mockTravelerLocalDataSource.findTravelerByEmail(any()) } returns fakeTraveler

            // When
            val findTravelerByEmail = travelerRepositoryNewDerived.findTravelerByEmail("")

            // Then
            assertThat(findTravelerByEmail, `is`(fakeTraveler))
        }
    }
}