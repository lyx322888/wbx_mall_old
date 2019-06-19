package com.wbx.mall.baserx;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

public class NewRxBus {
    private static volatile NewRxBus mInstance;
    private SerializedSubject<Object, Object> mSubject;

    private NewRxBus() {
        mSubject = new SerializedSubject<>(PublishSubject.create());
    }

    public static NewRxBus getInstance() {
        if (mInstance == null) {
            synchronized (NewRxBus.class) {
                if (mInstance == null) {
                    mInstance = new NewRxBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送事件
     *
     * @param o
     */
    public void post(Object o) {
        mSubject.onNext(o);
    }

    /**
     * 返回指定类型的Observable实例
     *
     * @param type
     * @param <T>
     * @return
     */
    public <T> Observable<T> tObservable(final Class<T> type) {
        return mSubject.ofType(type);
    }

    /**
     * 是否已有观察者订阅
     *
     * @return
     */
    public boolean hasObservers() {
        return mSubject.hasObservers();
    }

    /**
     * 一个默认的订阅方法
     *
     * @param type
     * @param next
     * @param error
     * @param <T>
     * @return
     */
    public <T> Subscription doSubscribe(Class<T> type, Action1<T> next, Action1<Throwable> error) {
        return tObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
    }

}
