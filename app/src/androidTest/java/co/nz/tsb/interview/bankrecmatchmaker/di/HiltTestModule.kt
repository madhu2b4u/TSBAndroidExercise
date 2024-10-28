package co.nz.tsb.interview.bankrecmatchmaker.di

import co.nz.tsb.interview.bankrecmatchmaker.data.repository.MatchRepository
import co.nz.tsb.interview.bankrecmatchmaker.data.repository.MockMatchRepositoryForSuccessTest
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
  components = [SingletonComponent::class],
  replaces = [FindMatchModule::class]
)
object HiltTestModule {
  @Singleton
  @Provides
  fun provideMatchRepository(): MatchRepository = MockMatchRepositoryForSuccessTest()
}