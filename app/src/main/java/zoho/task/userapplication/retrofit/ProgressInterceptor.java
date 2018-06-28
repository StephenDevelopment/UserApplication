package zoho.task.userapplication.retrofit;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import zoho.task.userapplication.retrofit.ProgressListener;
import zoho.task.userapplication.retrofit.ProgressResponseBody;

public class ProgressInterceptor implements Interceptor {
    private final ProgressListener mProgressListener;

    public ProgressInterceptor(ProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), mProgressListener))
                .build();
    }
}
