package zoho.task.userapplication.presenter;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.realm.RealmList;
import zoho.task.userapplication.models.User;
import zoho.task.userapplication.realm.UserDao;
import zoho.task.userapplication.models.UserItemInfo;
import zoho.task.userapplication.models.UserItemsVO;
import zoho.task.userapplication.models.UserListResponse;
import zoho.task.userapplication.realm.UserObject;
import zoho.task.userapplication.base.BasePresenter;
import zoho.task.userapplication.interfaces.DaoResponse;
import zoho.task.userapplication.interfaces.UserView;
import zoho.task.userapplication.rest.ApiInterface;

public class UserPresenter extends BasePresenter<UserView>{
    private final ApiInterface service;
    private UserDao userDao;

    public UserPresenter(UserView view, ApiInterface service) {
        super(view);
        this.service = service;
        userDao = new UserDao();
    }

    public void getUserData(int page) {

        Single<UserItemsVO> single = this.service.getUsersList(page)
                .map(new Function<UserListResponse, UserItemsVO>() {
                    @Override
                    public UserItemsVO apply(UserListResponse userListResponse) throws Exception {
                        List<User> contents = userListResponse.data;
                        List<UserItemInfo> userItemVOS = new LinkedList<>();
                        for (User content : contents) {
                            UserItemInfo itemInfo = new UserItemInfo(content.id, content.first_name, content.last_name, content.avatar);
                            userItemVOS.add(itemInfo);
                        }
                        return new UserItemsVO(userItemVOS, userListResponse.page < userListResponse.total_pages);
                    }
                });

        subscribe(single, new DisposableSingleObserver<UserItemsVO>() {
            @Override
            public void onSuccess(UserItemsVO userData) {
                getView().onLoadUsers(userData);
            }

            @Override
            public void onError(Throwable e) {
                getView().onMessage(e.getMessage());
            }
        });
    }

    public void saveUserDataIntoDB(List<UserItemInfo> userData) {
        final RealmList<UserObject> userObjectRealmList = parseResponseIntoRealm(userData);

        userDao.storeOrUpdateUserList(userObjectRealmList, new DaoResponse() {
            @Override
            public void onSuccess(String message) {
                Log.e("TAG", message);
            }

            @Override
            public void onSuccess(Object item, String message) {
                // No implementation.
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("TAG", errorMessage);
            }
        });
    }

    public void getUserListFromDB() {
        List<UserItemInfo> userItemVOS = new LinkedList<>();
        List<UserObject> userDaoUserList = userDao.getUserList();
        for (UserObject userObject : userDaoUserList) {
            UserItemInfo itemInfo = new UserItemInfo(userObject.getUserId(), userObject.getFirstName(), userObject.getLastName(), userObject.getAvatar());
            userItemVOS.add(itemInfo);
        }
        getView().onLoadUsers(new UserItemsVO(userItemVOS, false));
    }

    private RealmList<UserObject> parseResponseIntoRealm(List<UserItemInfo> userData) {

        RealmList<UserObject> userObjectRealmList = new RealmList<>();

        for (UserItemInfo data : userData) {
            final UserObject userObject = new UserObject();
            userObject.setUserId(data.mUserId);
            userObject.setFirstName(data.mFirstName);
            userObject.setLastName(data.mLastName);
            userObject.setAvatar(data.mAvatar);
            userObjectRealmList.add(userObject);
        }
        return userObjectRealmList;
    }

    public void clearDBandSaveData(final List<UserItemInfo> itemsVO){
        userDao.clearDataIfAvailable(new DaoResponse() {
            @Override
            public void onSuccess(String message) {
                Log.e("TAG", message);
                saveUserDataIntoDB(itemsVO);
            }

            @Override
            public void onSuccess(Object item, String message) {

            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("TAG", errorMessage);
            }
        });
    }
}
