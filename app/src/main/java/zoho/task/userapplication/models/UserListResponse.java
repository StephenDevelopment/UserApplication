package zoho.task.userapplication.models;

import java.io.Serializable;
import java.util.List;

public class UserListResponse implements Serializable{
    public int page;
    public int per_page;
    public int total;
    public int total_pages;
    public List<User> data;
}
