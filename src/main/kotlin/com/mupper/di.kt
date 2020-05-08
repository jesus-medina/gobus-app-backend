package com.mupper

import com.mupper.data.repository.TravelerRepository
import com.mupper.data.repository.TravelerRepositoryNewDerived
import com.mupper.data.source.local.TravelerLocalDataSource
import com.mupper.datasource.TravelerStaticDataSource
import com.mupper.infrastructure.route.TravelerRoute
import com.mupper.usecase.traveler.GetActualTraveler
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initDI() {
    startKoin {
        modules(
                listOf(
                        appModule,
                        dataSourceModule,
                        repositoryModule,
                        useCaseModule,
                        routingModule
                )
        )
    }
}

const val DEPENDENCY_NAME_IO_DISPATCHER = "ioDispatcher"

private val appModule = module {
    single(named(DEPENDENCY_NAME_IO_DISPATCHER)) { Dispatchers.IO }
}

private val dataSourceModule = module {
    single<TravelerLocalDataSource> { TravelerStaticDataSource() }
}

val repositoryModule = module {
    factory<TravelerRepository> {
        TravelerRepositoryNewDerived(
                get(),
                get(named(DEPENDENCY_NAME_IO_DISPATCHER))
        )
    }
}

val useCaseModule = module {
    single { GetActualTraveler(get()) }
}

private val routingModule = module {
    single { TravelerRoute(get(), get(named(DEPENDENCY_NAME_IO_DISPATCHER))) }
}