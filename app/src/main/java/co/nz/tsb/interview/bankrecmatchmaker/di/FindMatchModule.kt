package co.nz.tsb.interview.bankrecmatchmaker.di

import co.nz.tsb.interview.bankrecmatchmaker.data.repository.MatchRepository
import co.nz.tsb.interview.bankrecmatchmaker.data.repository.MockMatchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FindMatchModule {
  @Binds
  abstract fun provideMatchRepository(
    matchRepository: MockMatchRepository
  ): MatchRepository
}
