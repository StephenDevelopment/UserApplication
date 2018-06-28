package zoho.task.userapplication.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserObject extends RealmObject{
    @PrimaryKey
    private int userId;
    private String firstName;
    private String lastName;
    private String avatar;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
