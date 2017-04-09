package lt.vilnius.tvarkau.analytics

import android.app.Activity
import lt.vilnius.tvarkau.entity.Problem
import lt.vilnius.tvarkau.utils.NetworkUtils

/**
 * @author Martynas Jurkus
 */
interface Analytics {

    fun trackOpenFragment(activity: Activity, name: String)

    fun trackCloseFragment(name: String)

    fun trackStartRequest()

    fun trackFinishRequest(responseCode: Int, method: String?)

    fun trackViewProblem(problem: Problem)

    fun trackReportRegistration(reportType: String, photoCount: Int)

    fun trackReportValidation(validationError: String)

    fun trackPersonalDataSharingEnabled(enabled: Boolean)

    fun trackConnectionType(connectionType: NetworkUtils.ConnectionType)

    fun trackLogIn()

    fun trackApplyReportFilter(status: String, category: String, target: String)

}