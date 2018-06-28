package zoho.task.userapplication.rest;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zoho.task.userapplication.retrofit.ProgressInterceptor;
import zoho.task.userapplication.retrofit.ProgressListener;

public class ApiClient {

    private static Retrofit retrofit = null;

    public ApiClient() {
    }

    public ApiInterface getClientInterface(ProgressListener progressListener) {

        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // Print details of every request through retrofit.
                    .addNetworkInterceptor(new ProgressInterceptor(progressListener))
                    .connectTimeout( 120, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            Retrofit retrofitWithProgress = new Retrofit.Builder()
                    .baseUrl(WebServiceURL.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(defaultHttpClient)
                    .build();

        return retrofitWithProgress.create(ApiInterface.class);
    }

    public ApiInterface getClientInterface() {
        if (retrofit == null) {

            OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // Print details of every request through retrofit.
                    .connectTimeout( 60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(WebServiceURL.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(defaultHttpClient)
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
