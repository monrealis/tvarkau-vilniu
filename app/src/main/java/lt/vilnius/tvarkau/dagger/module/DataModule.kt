package lt.vilnius.tvarkau.dagger.module

import android.app.Application
import dagger.Module
import dagger.Provides
import lt.vilnius.tvarkau.R
import lt.vilnius.tvarkau.backend.LegacyApiService
import lt.vilnius.tvarkau.fragments.interactors.MultipleReportsMapInteractor
import lt.vilnius.tvarkau.fragments.interactors.MultipleReportsMapInteractorImpl
import lt.vilnius.tvarkau.mvp.interactors.ReportTypesInteractor
import lt.vilnius.tvarkau.mvp.interactors.ReportTypesInteractorImpl
import lt.vilnius.tvarkau.prefs.Preferences
import lt.vilnius.tvarkau.prefs.StringPreference
import rx.Scheduler
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Martynas Jurkus
 */
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideReportTypesInteractor(
            legacyApi: LegacyApiService,
            @IoScheduler ioScheduler: Scheduler
    ): ReportTypesInteractor {
        return ReportTypesInteractorImpl(legacyApi, ioScheduler)
    }

    @Provides
    @Singleton
    fun provideMultipleReportsMapInteractor(
            api: LegacyApiService,
            context: Application,
            @IoScheduler ioScheduler: Scheduler,
            @Named(Preferences.SELECTED_FILTER_REPORT_TYPE) reportType: StringPreference,
            @Named(Preferences.SELECTED_FILTER_REPORT_STATUS) reportStatus: StringPreference
    ): MultipleReportsMapInteractor {
        return MultipleReportsMapInteractorImpl(
                api,
                ioScheduler,
                reportType,
                reportStatus,
                context.getString(R.string.report_filter_all_report_types)
        )
    }
}