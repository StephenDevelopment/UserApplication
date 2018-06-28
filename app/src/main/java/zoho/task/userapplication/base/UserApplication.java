package zoho.task.userapplication.base;

import android.app.Application;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import zoho.task.userapplication.rest.ApiClient;
import zoho.task.userapplication.rest.ApiInterface;
import zoho.task.userapplication.retrofit.ProgressListener;

public class UserApplication extends Application {
    protected static UserApplication mInstance;
    private AppPreference mSharedPreferences;
    private ApiClient mApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mSharedPreferences = new AppPreference(this);
        mApiClient = new ApiClient();
        Realm.init(this);
        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .schemaVersion(0)
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

                        // DynamicRealm exposes an editable schema
                        RealmSchema schema = realm.getSchema();

                        // No major migration during development phase.
                        if (oldVersion == 0) {

                        }
                    }
                })
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static UserApplication getApp() {
        if (mInstance != null && mInstance instanceof UserApplication) {
            return mInstance;
        } else {
            mInstance = new UserApplication();
            mInstance.onCreate();
            return mInstance;
        }
    }

    public AppPreference getUserPreference() {
        return mSharedPreferences;
    }

    public ApiInterface getRetrofitInterface() {
        return mApiClient.getClientInterface();
    }

    public ApiInterface getRetrofitInterface(ProgressListener progressListener) {
        return mApiClient.getClientInterface(progressListener);
    }
}
