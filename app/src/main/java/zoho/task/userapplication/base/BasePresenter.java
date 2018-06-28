package zoho.task.userapplication.base;

import java.lang.ref.WeakReference;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author Stephenraj
 * @version 1.0.0
 */
public abstract class BasePresenter<V extends MainView> {

    private final WeakReference<V> mView;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected BasePresenter(V view) {
        mView = new WeakReference<>(view);
    }

    public void initialize() {
        //some initialization code
    }

    public V getView() {
        return mView.get();
    }

    protected <O> void subscribe(Single<O> single, DisposableSingleObserver<O> observer) {
        mCompositeDisposable.add(single.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    protected <O> void subscribe(Flowable<O> flowable, ResourceSubscriber<O> subscriber) {
        mCompositeDisposable.add(flowable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber));
    }

    protected <O> void subscribe(Observable<O> observable, DisposableObserver<O> observer) {
        mCompositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    public void cleanup() {
        mView.clear();
        mCompositeDisposable.clear();
    }
}
