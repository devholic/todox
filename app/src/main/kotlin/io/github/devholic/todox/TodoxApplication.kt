package io.github.devholic.todox

import android.app.Application
import io.github.devholic.todox.dagger.component.DaggerTodoxComponent
import io.github.devholic.todox.dagger.component.TodoxComponent
import io.github.devholic.todox.dagger.module.TodoxModule

class TodoxApplication : Application() {

    private lateinit var component: TodoxComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerTodoxComponent
                .builder()
                .todoxModule(TodoxModule(this))
                .build()
    }

    fun component(): TodoxComponent {
        return component
    }
}