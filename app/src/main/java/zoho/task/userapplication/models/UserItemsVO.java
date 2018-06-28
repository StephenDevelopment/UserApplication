package zoho.task.userapplication.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephenraj
 * @version 1.0.0
 */
public class UserItemsVO {

    private final List<UserItemInfo> userItemInfos = new ArrayList<>();
    private final boolean isLast;

    public UserItemsVO(@NonNull List<UserItemInfo> userItemInfos, boolean isLast) {
        this.userItemInfos.addAll(userItemInfos);
        this.isLast = isLast;
    }

    public List<UserItemInfo> getUserItemInfos() {
        return userItemInfos;
    }

    public boolean isLast() {
        return isLast;
    }
}
