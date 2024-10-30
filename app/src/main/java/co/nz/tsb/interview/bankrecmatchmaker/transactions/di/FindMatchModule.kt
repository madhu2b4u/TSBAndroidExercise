package co.nz.tsb.interview.bankrecmatchmaker.transactions.di

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.repository.TransactionRepository
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.repository.TransactionRepositoryImpl
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.MatchItemsUseCase
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.MatchItemsUseCaseImpl
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.TransactionUseCase
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.TransactionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FindMatchModule {
    @Binds
    abstract fun provideMatchRepository(
        matchRepository: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    internal abstract fun bindsUseCase(
        useCaseImpl: TransactionUseCaseImpl
    ): TransactionUseCase

    @Binds
    internal abstract fun bindsMatchItemUseCase(
        useCaseImpl: MatchItemsUseCaseImpl
    ): MatchItemsUseCase
}
