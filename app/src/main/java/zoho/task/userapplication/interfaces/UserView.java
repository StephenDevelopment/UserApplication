package zoho.task.userapplication.interfaces;

import zoho.task.userapplication.models.UserItemsVO;
import zoho.task.userapplication.base.MainView;

public interface UserView extends MainView{
    void onLoadUsers(UserItemsVO itemsVO);
    void onProgressVisible(boolean visible);
}
