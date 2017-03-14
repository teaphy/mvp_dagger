package com.jess.arms.utils;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.BaseView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by jess on 11/10/2016 16:39
 * Contact with jess.yan.effort@gmail.com
 */

public class RxUtils {

	public static <T> FlowableTransformer<T, T> applySchedulers(final BaseView view) {
		return new FlowableTransformer<T, T>() {
			@Override
			public Flowable<T> apply(Flowable<T> observable) {
				return observable.subscribeOn(Schedulers.io())
						.doOnSubscribe(new Consumer<Subscription>() {
							@Override
							public void accept(@NonNull Subscription subscription) throws Exception {
								view.showLoading();
							}
						})
						.subscribeOn(AndroidSchedulers.mainThread())
						.observeOn(AndroidSchedulers.mainThread())
						.doAfterTerminate(new Action() {
							@Override
							public void run() {
								view.hideLoading();//隐藏进度条
							}
						}).compose(RxUtils.<T>bindToLifecycle(view));
			}
		};
	}


    public static <T> LifecycleTransformer<T> bindToLifecycle(BaseView view) {
        if (view instanceof BaseActivity) {
            return ((BaseActivity) view).<T>bindToLifecycle();
        } else if (view instanceof BaseFragment) {
            return ((BaseFragment) view).<T>bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }

    }

}
