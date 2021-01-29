package com.tashariko.exploredb.di.module

import androidx.lifecycle.ViewModelProvider
import com.tashariko.exploredb.di.util.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

//    @Binds
//    @IntoMap
//    @ViewModelKey(DealDetailViewModel::class)
//    abstract fun bindDealDetailViewModel(viewModel: DealDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
