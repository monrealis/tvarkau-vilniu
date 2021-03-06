package lt.vilnius.tvarkau.fragments.presenters

import lt.vilnius.tvarkau.fragments.interactors.ReportListInteractor
import lt.vilnius.tvarkau.fragments.views.ReportListView
import rx.Scheduler
import rx.Subscription

/**
 * @author Martynas Jurkus
 */
class MyReportListPresenterImpl(
        private val interactor: ReportListInteractor,
        private val uiScheduler: Scheduler,
        view: ReportListView,
        connectivityProvider: ConnectivityProvider
) : BaseReportListPresenter(connectivityProvider, view) {

    private val subscriptions = mutableListOf<Subscription>()

    override fun onAttach() {
    }

    override fun getReportsForPage(page: Int) {
        withConnectivityCheck()
                .flatMap { interactor.getProblems(page) }
                .observeOn(uiScheduler)
                .doOnSubscribe { view.showProgress() }
                .doOnUnsubscribe { view.hideProgress() }
                .doOnUnsubscribe { view.hideLoader() }
                .subscribe({
                    if (it.isEmpty()) {
                        view.showEmptyState()
                    } else {
                        view.hideEmptyState()
                        view.onReportsLoaded(it)
                    }
                }, {
                    handleError(it, page)
                }).apply { subscriptions += this }
    }

    override fun onDetach() {
        subscriptions.forEach(Subscription::unsubscribe)
    }
}