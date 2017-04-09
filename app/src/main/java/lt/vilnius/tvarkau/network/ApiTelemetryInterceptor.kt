package lt.vilnius.tvarkau.network

import android.content.Context
import lt.vilnius.tvarkau.analytics.Analytics
import lt.vilnius.tvarkau.backend.LegacyApiService
import lt.vilnius.tvarkau.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response

class ApiTelemetryInterceptor(private val context: Context, private val analytics: Analytics) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        analytics.trackConnectionType(NetworkUtils.getConnectionType(context))

        val request = chain.request()

        analytics.trackStartRequest()
        val response = chain.proceed(request)
        analytics.trackFinishRequest(response.code(), request.header(LegacyApiService.HEADER_API_METHOD))

        return response
    }


}