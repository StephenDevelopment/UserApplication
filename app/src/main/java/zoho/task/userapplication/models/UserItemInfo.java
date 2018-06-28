package zoho.task.userapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Stephenraj
 * @version 1.0.0
 */

public class UserItemInfo implements Parcelable {

    public String mFirstName;
    public String mLastName;
    public String mAvatar;
    public int mUserId;

    public UserItemInfo(int id, String first_name, String last_name, String avatar) {
        mUserId = id;
        mFirstName = first_name;
        mLastName = last_name;
        mAvatar = avatar;
    }

    protected UserItemInfo(Parcel in) {
        mUserId = in.readInt();
        mFirstName = in.readString();
        mAvatar = in.readString();
        mLastName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mUserId);
        dest.writeString(mFirstName);
        dest.writeString(mLastName);
        dest.writeString(mAvatar);
    }

    public static final Creator<UserItemInfo> CREATOR = new Creator<UserItemInfo>() {
        @Override
        public UserItemInfo createFromParcel(Parcel in) {
            return new UserItemInfo(in);
        }

        @Override
        public UserItemInfo[] newArray(int size) {
            return new UserItemInfo[size];
        }
    };
}
