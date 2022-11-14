package com.lx.api

import android.util.Log
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.lx.data.CareListResponse
import com.lx.data.FileUploadResponse
import com.lx.data.MemberListResponse
import com.lx.data.MemberRequestResponse
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.Exception
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import kotlin.collections.HashMap

/**
 * 웹서버 요청/응답을 처리하기 위한 기본 API
 */
interface BasicApi {

    /**
     * POST 방식으로 멤버 로그인 요청 / 사용가능
     */

    @FormUrlEncoded
    @POST("care/careLogin")
    fun postCareLogin(
        @Field("requestCode") requestCode: String,
        @Field("careId") careId: String,
        @Field("carePw") carePw: String
    ): Call<CareListResponse>

    @GET("/care/memberListTest")
    fun getMemberRequestList(
        @Query("requestCode") requestCode: String
    ): Call<MemberRequestResponse>

    /**
     * POST 방식으로 멤버 회원가입 요청 / 사용가능
     */

    @FormUrlEncoded
    @POST("care/careAdd")
    fun postCareAdd(
        @Field("requestCode") requestCode: String,
        @Field("careId") careId: String,
        @Field("careName") careName: String,
        @Field("carePw") carePw: String,
        @Field("careExperience") careExperience: String,
        @Field("careAddress") careAddress: String,
        @Field("careEducation") careEducation: String,
        @Field("careImage") careImage: String,
        @Field("careApproval") careApproval: String,
        @Field("lat") lat: String,
        @Field("lng") lng: String
    ): Call<CareListResponse>

    /**
     * POST 방식으로 멤버 비밀번호찾기 요청
     */

    @FormUrlEncoded
    @POST("care/postCareFindPw")
    fun postCareFindPw(
        @Field("requestCode") requestCode: String,
        @Field("memberMobile") memberMobile: String,
        @Field("memberName") memberName: String
    ): Call<CareListResponse>

    /**
     * POST 방식으로 아이디 중복체크 요청
     */

    @FormUrlEncoded
    @POST("care/careIdOverlap")
    fun postCareCheckId(
        @Field("requestCode") requestCode: String,
        @Field("careId") careId: String
    ): Call<CareListResponse>

    /**
     * POST 방식으로 돌보미 정보수정 요청
     */

    @FormUrlEncoded
    @POST("care/careUpdate")
    fun postCareUpdate(
        @Field("requestCode") requestCode: String,
        @Field("careAddress") careAddress: String,
        @Field("careNo") careNo: String,
        @Field("careName") careName: String,
        @Field("careImage") careImage: String
    ): Call<CareListResponse>

    /**
     * GET 방식으로 주변 펫시터 요청 / 사용가능
     */

    @GET("care/careDelete")
    fun getCareList(
        @Query("requestCode") requestCode: String,
        @Query("carex1") carex1: Int,
        @Query("carex2") carex2: Int,
        @Query("carey1") carey1: Int,
        @Query("carey2") carey2: Int
    ): Call<CareListResponse>

    /**
     * 파일 업로드 요청 / 사용하자
     */

    @Multipart
    @POST("/community/upload")
    fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part(value="params", encoding="UTF-8") params: HashMap<String,String> = hashMapOf()
    ): Call<FileUploadResponse>

}

class BasicClient {

    companion object {
        const val TAG = "BasicClient"

        private var instance : BasicApi? = null

        val api: BasicApi
            get() {
                return getInstance()
            }

        @Synchronized
        fun getInstance(): BasicApi {
            if (instance == null)
                instance = create()
            return instance as BasicApi
        }

        // 프로토콜
        private const val PROTOCOL = "http"

        // 기본 URL
        private const val BASE_URL = "http://192.168.0.215:8001/"

        // 헤더 속성
        private const val CLIENT_ID = ""
        private const val CLIENT_SECRET = ""
        var userId:String = ""

        fun create(): BasicApi {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Client-Id", CLIENT_ID)
                    .addHeader("X-Client-Secret", CLIENT_SECRET)
                    .addHeader("X-Client-UserId", userId)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val clientBuilder = OkHttpClient.Builder()

            if (PROTOCOL == "https") {

                val x509TrustManager: X509TrustManager = object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        val x509Certificates = arrayOf<X509Certificate>()
                        return x509Certificates
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                        Log.d(TAG, ": authType: $authType")
                    }

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                        Log.d(TAG, ": authType: $authType")
                    }
                }

                try {
                    val sslContext = SSLContext.getInstance("TLS")
                    sslContext.init(null, trustAllCerts, SecureRandom())
                    val sslSocketFactory = sslContext.socketFactory
                    clientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                clientBuilder.hostnameVerifier(RelaxedHostNameVerifier())

            }

            clientBuilder.addInterceptor(headerInterceptor)
            clientBuilder.addInterceptor(httpLoggingInterceptor)
            clientBuilder.callTimeout(60, TimeUnit.SECONDS)       // 호출 타임아웃 시간 설정 60초
            clientBuilder.connectTimeout(60, TimeUnit.SECONDS)    // 연결 타임아웃 시간 설정 60초
            clientBuilder.readTimeout(60, TimeUnit.SECONDS)
            clientBuilder.writeTimeout(60, TimeUnit.SECONDS)

            val client = clientBuilder.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BasicApi::class.java)
        }

        private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        })

        class RelaxedHostNameVerifier : HostnameVerifier {
            override fun verify(hostname: String, session: SSLSession): Boolean {
                return true
            }
        }

        private var format = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.KOREAN)
        private var seqCode = 0


        /**
         * 요청 코드 생성
         */
        @Synchronized
        fun generateRequestCode(): String {
            seqCode += 1
            if (seqCode > 999) {
                seqCode = 1
            }

            var seqCodeStr = seqCode.toString()
            if (seqCodeStr.length == 1) {
                seqCodeStr = "00$seqCodeStr"
            } else if (seqCodeStr.length == 2) {
                seqCodeStr = "0$seqCodeStr"
            }

            val date = Date()
            val dateStr = format.format(date)

            return dateStr + seqCodeStr
        }

    }
}
