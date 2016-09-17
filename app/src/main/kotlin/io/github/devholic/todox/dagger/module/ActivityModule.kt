package io.github.devholic.todox.dagger.module

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides fun provideActivity(): AppCompatActivity {
        return activity
    }
}