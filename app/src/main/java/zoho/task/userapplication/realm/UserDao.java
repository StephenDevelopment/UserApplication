package zoho.task.userapplication.realm;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import zoho.task.userapplication.interfaces.DaoResponse;

/**
 * Created by Stephenraj.
 */

public class UserDao {
    private final String TAG = UserDao.class.getSimpleName();

    public void storeOrUpdateUserList(final RealmList<UserObject> userRealmList, final DaoResponse callback) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // As primary key is involved, use copy to realm or update as it will create or
                //  update based on object availability.
                realm.copyToRealmOrUpdate(userRealmList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess("user data successfully stored!");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, error.getMessage());
                callback.onFailure("user data is not stored");
                realm.close();
            }
        });
    }

    public List<UserObject> getUserList() {
        Realm realm = Realm.getDefaultInstance();
        List<UserObject> athleteRoutesList = realm.copyFromRealm(realm.where(UserObject.class).findAll());
        realm.close();
        return athleteRoutesList;
    }

    public void clearDataIfAvailable(final DaoResponse callback) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(UserObject.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess("user data deleted successfully");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, error.getMessage());
                callback.onFailure("user data not deleted");
                realm.close();
            }
        });
    }
}