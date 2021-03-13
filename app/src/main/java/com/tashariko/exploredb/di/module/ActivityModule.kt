package com.tashariko.exploredb.di.module

import com.tashariko.exploredb.ui.main.MainActivity
import com.tashariko.exploredb.ui.main.movieDetail.MovieDetailActivity
import com.tashariko.exploredb.ui.splash.LandingActivity
import com.tashariko.exploredb.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeLandingActivity(): LandingActivity

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailActivity(): MovieDetailActivity


}
