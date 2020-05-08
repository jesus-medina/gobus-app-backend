package com.mupper.utils

import com.mupper.DEPENDENCY_NAME_IO_DISPATCHER
import com.mupper.data.source.local.TravelerLocalDataSource
import com.mupper.datasource.TravelerStaticDataSource
import com.mupper.repositoryModule
import com.mupper.useCaseModule
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    stopKoin()
    startKoin {
        modules(
                listOf(
                        fakeAppModule,
                        fakeDataSourceModule,
                        repositoryModule,
                        useCaseModule
                ) + modules
        )
    }
}

private val fakeAppModule = module {
    single(named(DEPENDENCY_NAME_IO_DISPATCHER)) { Dispatchers.Unconfined }
}

private val fakeDataSourceModule = module {
    single<TravelerLocalDataSource> { TravelerStaticDataSource() }
}