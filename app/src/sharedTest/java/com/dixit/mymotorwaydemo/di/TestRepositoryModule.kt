package com.dixit.mymotorwaydemo.di

import com.dixit.mymotorwaydemo.mymodule.data.repositoryIml.TestRandomTextRepository
import com.dixit.mymotorwaydemo.mymodule.domain.repository.RandomTextRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


/**
 *  TestRepositoryModule
 *  This is a TestRepository Module, It will replace the RepositoryModule for testing purpose.
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object TestRepositoryModule {

    @Provides
    @Singleton
    fun provideRandomTextRepository(): RandomTextRepository {
        return TestRandomTextRepository()
    }
}