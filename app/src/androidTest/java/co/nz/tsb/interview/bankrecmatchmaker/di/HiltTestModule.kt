package co.nz.tsb.interview.bankrecmatchmaker.di

import co.nz.tsb.interview.bankrecmatchmaker.data.repository.FakeTransactionUseCase
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.repository.TransactionRepository
import co.nz.tsb.interview.bankrecmatchmaker.data.repository.MockMatchRepositoryForSuccessTest
import co.nz.tsb.interview.bankrecmatchmaker.transactions.di.FindMatchModule
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.TransactionUseCase
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
  fun provideMatchRepository(): TransactionRepository = MockMatchRepositoryForSuccessTest()

  @Singleton
  @Provides
  fun provideTestTransactionUseCase(): TransactionUseCase {
    return FakeTransactionUseCase()
  }
}