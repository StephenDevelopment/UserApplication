package zoho.task.userapplication.base;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    private int PRIVATE_MODE = 0;
    private static final String USER_PREFERENCE = "user_preference";

    public AppPreference(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(USER_PREFERENCE, PRIVATE_MODE);
        editor = pref.edit();
    }

    // delete all values saved under the application.
    public void clearPreference() {
        editor.clear();
        editor.commit();
    }
}
