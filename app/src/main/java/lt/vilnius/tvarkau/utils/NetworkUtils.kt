package lt.vilnius.tvarkau.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager


object NetworkUtils {

    /**
     * Abstracts mobile connection types like 3G, GPRS and EDGE to MOBILE_SLOW and MOBILE_FAST.
     */
    enum class ConnectionType {
        WIFI,
        MOBILE_FAST,
        MOBILE_SLOW,
        NONE
    }

    /**
     * Source: http://stackoverflow.com/a/8548926
     * @return true if mobile connection supports speeds over ca. 400 kbps.
     */
    private fun isMobileConnectionFast(mobileSubtype: Int): Boolean {
        when (mobileSubtype) {
            TelephonyManager.NETWORK_TYPE_1xRTT -> return false // ~ 50-100 kbps
            TelephonyManager.NETWORK_TYPE_CDMA -> return false // ~ 14-64 kbps
            TelephonyManager.NETWORK_TYPE_EDGE -> return false // ~ 50-100 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> return true // ~ 400-1000 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_A -> return true // ~ 600-1400 kbps
            TelephonyManager.NETWORK_TYPE_GPRS -> return false // ~ 100 kbps
            TelephonyManager.NETWORK_TYPE_HSDPA -> return true // ~ 2-14 Mbps
            TelephonyManager.NETWORK_TYPE_HSPA -> return true // ~ 700-1700 kbps
            TelephonyManager.NETWORK_TYPE_HSUPA -> return true // ~ 1-23 Mbps
            TelephonyManager.NETWORK_TYPE_UMTS -> return true // ~ 400-7000 kbps
            TelephonyManager.NETWORK_TYPE_EHRPD // API level 11
            -> return true // ~ 1-2 Mbps
            TelephonyManager.NETWORK_TYPE_EVDO_B // API level 9
            -> return true // ~ 5 Mbps
            TelephonyManager.NETWORK_TYPE_HSPAP // API level 13
            -> return true // ~ 10-20 Mbps
            TelephonyManager.NETWORK_TYPE_IDEN // API level 8
            -> return false // ~25 kbps
            TelephonyManager.NETWORK_TYPE_LTE // API level 11
            -> return true // ~ 10+ Mbps
            TelephonyManager.NETWORK_TYPE_UNKNOWN -> return true         // Unknown
            else -> return false
        }
    }

    /**
     * Get ConnectionType.
     */
    fun getConnectionType(context: Context): ConnectionType {
        if (isConnectedWifi(context)) {
            return ConnectionType.WIFI
        } else if (isConnectedMobile(context)) {
            if (isConnectedFast(context)) {
                return ConnectionType.MOBILE_FAST
            } else {
                return ConnectionType.MOBILE_SLOW
            }
        }
        return ConnectionType.NONE
    }

    /**
     * @return true if device is connected to any network.
     */
    fun isConnected(context: Context): Boolean {
        val info = NetworkUtils.getNetworkInfo(context)
        return info != null && info.isConnected
    }

    private fun getNetworkInfo(context: Context): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    /**
     * @return true if device is connected to a WiFi network.
     */
    private fun isConnectedWifi(context: Context): Boolean {
        val info = NetworkUtils.getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * @return true if device is connected to a mobile network.
     */
    private fun isConnectedMobile(context: Context): Boolean {
        val info = NetworkUtils.getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
    }

    /**
     * Check if there is fast connectivity
     */
    private fun isConnectedFast(context: Context): Boolean {
        val info = NetworkUtils.getNetworkInfo(context)
        return info != null && info.isConnected && NetworkUtils.isConnectionFast(info.type, info.subtype)
    }

    /**
     * Check if the connection is fast
     */
    private fun isConnectionFast(type: Int, subType: Int): Boolean {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            return isMobileConnectionFast(subType)
        } else {
            return false
        }
    }
}