package lt.vilnius.tvarkau.dagger.module

import android.app.Application
import com.nhaarman.mockito_kotlin.mock
import com.squareup.leakcanary.RefWatcher
import dagger.Module
import dagger.Provides
import lt.vilnius.tvarkau.TestTvarkauApplication
import lt.vilnius.tvarkau.fragments.presenters.ConnectivityProvider
import rx.Scheduler
import rx.schedulers.Schedulers
import javax.inject.Singleton

/**
 * @author Martynas Jurkus
 */
@Module
class TestAppModule(val application: TestTvarkauApplication) {

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    @IoScheduler
    fun provideIoScheduler(): Scheduler = Schedulers.immediate()

    @Provides
    @Singleton
    @UiScheduler
    fun provideUiScheduler(): Scheduler = Schedulers.immediate()

    @Provides
    @Singleton
    internal fun providesRefWatcher(): RefWatcher {
        return application.refWatcher
    }

    @Provides
    @Singleton
    fun provideConnectivityProvider(): ConnectivityProvider = mock()
}