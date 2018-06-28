package zoho.task.userapplication.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

/**
 * @author Stephenraj
 * @version 1.0.0
 */
public abstract class BaseCompatActivity<P extends BasePresenter<? extends MainView>> extends AppCompatActivity {

    private P mPresenter;
    @Nullable
    private ProgressDialog mProgressDialog;
    private AppPreference mPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isNoTitleFeature();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mPresenter = onCreatePresenter();

        if (mPresenter != null) {
            mPresenter.initialize();
        }
        onActivityCreated(savedInstanceState);
    }

    protected P onCreatePresenter() {
        return null;
    }

    public P getPresenter() {
        return mPresenter;
    }

    protected boolean isNoTitleFeature() {
        return false;
    }

    public AppPreference getSessionManager() {
        if (mPreference == null) {
            mPreference = new AppPreference(this);
        }
        return mPreference;
    }

    @CallSuper
    protected void onActivityCreated(@Nullable Bundle savedInstanceState) {
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void printOut(String message) {
        System.out.println(message);
    }

    protected void showProgressDialog(String message, boolean cancelable) {
        dismissDialog();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
    }

    protected void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void onMessage(String message) {
        showToast(message);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.cleanup();
        }
        super.onDestroy();
    }

    protected abstract int getContentView();
}
