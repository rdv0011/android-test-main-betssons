package com.betsson.interviewtest

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideOddsCalculator(): OddsCalculator {
        return OddsCalculator()
    }
    
    @Provides
    @Singleton
    fun provideBetRepository(oddsCalculator: OddsCalculator): BetRepository {
        return BetRepository(oddsCalculator)
    }
}
