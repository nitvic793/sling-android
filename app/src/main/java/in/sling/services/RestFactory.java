package in.sling.services;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nitiv on 5/11/2016.
 */
public class RestFactory {
    private static Retrofit retrofit;

    static{
        retrofit = new Retrofit.Builder()
                .baseUrl("http://sling-server.herokuapp.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static SlingService createService()
    {
        return retrofit.create(SlingService.class);
    }

    public static SlingService createService(final String authToken)
    {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Accept", "application/json;versions=1");
                        ongoing.addHeader("Authorization", authToken);
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://sling-server.herokuapp.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        return retrofit.create(SlingService.class);
    }
}
