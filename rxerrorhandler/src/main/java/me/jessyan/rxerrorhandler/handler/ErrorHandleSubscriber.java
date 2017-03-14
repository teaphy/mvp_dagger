package me.jessyan.rxerrorhandler.handler;


import io.reactivex.observers.DisposableObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by jess on 9/2/16 14:41
 * Contact with jess.yan.effort@gmail.com
 */

public abstract class ErrorHandleSubscriber<T> extends DisposableObserver<T> {
	private ErrorHandlerFactory mHandlerFactory;

	public ErrorHandleSubscriber(RxErrorHandler rxErrorHandler){
		this.mHandlerFactory = rxErrorHandler.getmHandlerFactory();
	}

	@Override
	public void onNext(T t) {

	}

	@Override
	public void onComplete() {

	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
		mHandlerFactory.handleError(e);
	}

}

