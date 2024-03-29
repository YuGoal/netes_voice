package io.yugoal.lib_network.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.yugoal.lib_network.okhttp.cookie.SimpleCookieJar;
import io.yugoal.lib_network.okhttp.https.HttpsUtils;
import io.yugoal.lib_network.okhttp.listener.DisposeDataHandle;
import io.yugoal.lib_network.okhttp.response.CommonFileCallback;
import io.yugoal.lib_network.okhttp.response.CommonJsonCallback;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author caoyu
 * date  2019/9/15
 * 用来发送get, post请求的工具类，包括设置一些请求的共用参数
 */
public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        /**
         *  为所有请求添加请求头，看个人需求
         */
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("name", "").build();
                return chain.proceed(request);
            }
        });
        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true);
        /**
         * trust all the https point
         */
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(),
                HttpsUtils.initTrustManager());
        mOkHttpClient = okHttpClientBuilder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 通过构造好的Request,Callback去发送请求
     */
    public static Call get(Request request, DisposeDataHandle disposeDataHandle){
        Call call =mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(disposeDataHandle));
        return call;
    }

    public static Call post(Request request, DisposeDataHandle disposeDataHandle){
        Call call =mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(disposeDataHandle));
        return call;
    }

    public static Call downloadFile(Request request, DisposeDataHandle disposeDataHandle){
        Call call =mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(disposeDataHandle));
        return call;
    }
}
