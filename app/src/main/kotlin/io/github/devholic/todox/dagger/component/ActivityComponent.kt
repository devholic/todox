package io.github.devholic.todox.dagger.component

import android.support.v7.app.AppCompatActivity
import dagger.Component
import io.github.devholic.todox.dagger.ActivityScope
import io.github.devholic.todox.dagger.module.ActivityModule
import io.github.devholic.todox.home.view.HomeActivity

@ActivityScope
@Component(
        dependencies = arrayOf(TodoxComponent::class),
        modules = arrayOf(ActivityModule::class)
)
interface ActivityComponent : TodoxComponent {

    fun provideActivity(): AppCompatActivity

    fun inject(activity: HomeActivity)
}