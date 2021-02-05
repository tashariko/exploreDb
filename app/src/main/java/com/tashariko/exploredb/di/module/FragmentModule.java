package com.tashariko.exploredb.di.module;


import com.tashariko.exploredb.ui.main.user.UserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract UserFragment bindUserFragment();

}
