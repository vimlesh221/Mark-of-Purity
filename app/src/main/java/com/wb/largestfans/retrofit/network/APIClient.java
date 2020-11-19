package com.wb.largestfans.retrofit.network;

import android.annotation.SuppressLint;


import com.wb.largestfans.constant.AppConstants;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by vimleshn on 05/01/17.
 * description this is class is use to intract with server
 */

@SuppressWarnings("unused")
public class APIClient {

    public static boolean isHeader = false;
    public static String sessionId = "";
    public static boolean isHeaderDeviced = false;
    public static boolean isTokenApi = false;
    public static String deviceId = "" ;
    private static final String userId = "" ;
    public static String accessToken = "";

  public static Retrofit getClient() {
// created interceptor to log api header and body
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client;

        @SuppressWarnings("Convert2Lambda") Interceptor interceptorHeaderAuth = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                Request request = original.newBuilder()

                       // .header(AppConstants.authorization, accessToken)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            } };
       // OkHttpClient.Builder builder =new OkHttpClient.Builder();
        // builder.addInterceptor(interceptor);
          // client = builder.build();
        // client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).addInterceptor(interceptorHeaderAuth).addInterceptor(interceptor).build();
        client =getUnsafeOkHttpClient().addInterceptor(interceptor).build();
        isHeader = false;
      boolean isHeaderUserid = false;
        isHeaderDeviced = false;
        isTokenApi = false;
        // return retrofit client
        return new Retrofit.Builder()
                .baseUrl(AppConstants.Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                // .client(getUnsafeOkHttpClient().addInterceptor(interceptor).build())
                .client(client)
                .build();
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);

           // builder.hostnameVerifier((hostname, session) -> true);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
