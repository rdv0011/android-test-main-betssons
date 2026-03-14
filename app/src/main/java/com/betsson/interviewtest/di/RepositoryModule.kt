package com.betsson.interviewtest.di

import com.betsson.interviewtest.data.repository.BetRepositoryImpl
import com.betsson.interviewtest.domain.repository.BetRepository
import com.betsson.interviewtest.utils.OddsCalculator
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
        return BetRepositoryImpl(oddsCalculator)
    }
}
