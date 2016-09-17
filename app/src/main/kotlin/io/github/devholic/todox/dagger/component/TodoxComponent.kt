package io.github.devholic.todox.dagger.component

import android.content.Context
import com.squareup.sqlbrite.BriteDatabase
import dagger.Component
import io.github.devholic.todox.dagger.module.TodoxModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TodoxModule::class))
interface TodoxComponent {

    fun provideApplicationContext(): Context
    fun provideDatabase(): BriteDatabase
}
