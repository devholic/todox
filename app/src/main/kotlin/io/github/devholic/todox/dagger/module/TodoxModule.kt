package io.github.devholic.todox.dagger.module

import android.content.Context
import com.squareup.sqlbrite.BriteDatabase
import dagger.Module
import dagger.Provides
import io.github.devholic.todox.TodoxApplication
import io.github.devholic.todox.db.DB
import javax.inject.Singleton

@Module
class TodoxModule(val application: TodoxApplication) {

    @Provides @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides @Singleton
    fun provideDatabase(): BriteDatabase {
        return DB.getInstance(application)
    }
}