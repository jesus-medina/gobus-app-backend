package com.mupper.usecase.bus

import com.mupper.data.source.local.BusLocalDataSource

class IsBusInDb(
    private val busLocalDataSource: BusLocalDataSource,
    private val path: String
) {
    suspend operator fun invoke() = busLocalDataSource.getBusCount(path)
}