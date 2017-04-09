package lt.vilnius.tvarkau.backend

import lt.vilnius.tvarkau.backend.requests.GetReportRequest
import lt.vilnius.tvarkau.backend.requests.GetReportTypesRequest
import lt.vilnius.tvarkau.entity.LoginResponse
import lt.vilnius.tvarkau.entity.Problem
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import rx.Observable
import rx.Single

interface LegacyApiService {

    @POST("server.php")
    @Headers("$HEADER_API_METHOD: getProblem")
    fun getProblem(@Body getProblemRequest: GetReportRequest): Observable<ApiResponse<Problem>>

    @POST("server.php")
    @Headers("$HEADER_API_METHOD: getProblems")
    fun getProblems(@Body getProblemsRequest: ApiRequest<GetProblemsParams>): Observable<ApiResponse<List<Problem>>>

    @POST("server.php")
    @Headers("$HEADER_API_METHOD: postNewProblem")
    fun postNewProblem(@Body getNewProblemRequest: ApiRequest<GetNewProblemParams>): Observable<ApiResponse<Int>>

    @POST("server.php")
    @Headers("$HEADER_API_METHOD: getProblemTypes")
    fun getProblemTypes(@Body request: GetReportTypesRequest): Single<ApiResponse<List<String>>>

    @POST("server.php")
    @Headers("$HEADER_API_METHOD: loginToVilniusAccount")
    fun loginToVilniusAccount(@Body getVilniusSignRequest: ApiRequest<GetVilniusSignParams>): Observable<ApiResponse<LoginResponse>>

    companion object {
        const val HEADER_API_METHOD = "Method"
    }
}
