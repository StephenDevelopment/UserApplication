package zoho.task.userapplication.rest;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import zoho.task.userapplication.models.UserListResponse;

public interface ApiInterface {
    @GET(WebServiceURL.GET_USERS)
    Single<UserListResponse> getUsersList(@Query("page") int page);
}
