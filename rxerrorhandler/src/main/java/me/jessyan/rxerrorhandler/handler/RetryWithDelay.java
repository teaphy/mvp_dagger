package me.jessyan.rxerrorhandler.handler;

import android.util.Log;


import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;


/**
 * Created by jess on 9/2/16 14:32
 * Contact with jess.yan.effort@gmail.com
 */
public class RetryWithDelay implements
		Function<Flowable<? extends Throwable>, Flowable<?>> {
    public final String TAG = this.getClass().getSimpleName();
    private final int maxRetries;
    private final int retryDelaySecond;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    @Override
    public Flowable<?> apply(Flowable<? extends Throwable> attempts) {
        return attempts
                .flatMap(new Function<Throwable, Flowable<?>>() {
                    @Override
                    public Flowable<?> apply(Throwable throwable) {
                        if (++retryCount <= maxRetries) {
                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            Log.d(TAG, "get error, it will try after " + retryDelaySecond
                                    + " second, retry count " + retryCount);
                            return Flowable.timer(retryDelaySecond,
                                    TimeUnit.SECONDS);
                        }
                        // Max retries hit. Just pass the error along.
                        return Flowable.error(throwable);
                    }
                });
    }

}