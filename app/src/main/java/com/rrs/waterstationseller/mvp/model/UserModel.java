package com.rrs.waterstationseller.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;


import com.rrs.waterstationseller.mvp.contract.UserContract;
import com.rrs.waterstationseller.mvp.model.api.cache.CacheManager;
import com.rrs.waterstationseller.mvp.model.api.service.ServiceManager;
import com.rrs.waterstationseller.mvp.model.entity.User;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;


/**
 * Created by jess on 9/4/16 10:56
 * Contact with jess.yan.effort@gmail.com
 */
@ActivityScope
public class UserModel extends BaseModel<ServiceManager, CacheManager> implements UserContract.Model {
    public static final int USERS_PER_PAGE = 10;

    @Inject
    public UserModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }


    @Override
    public Flowable<List<User>> getUsers(int lastIdQueried, boolean update) {
	    Flowable<List<User>> users = mServiceManager.getUserService()
                .getUsers(lastIdQueried, USERS_PER_PAGE);
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return mCacheManager.getCommonCache()
                .getUsers(users
                        , new DynamicKey(lastIdQueried)
                        , new EvictDynamicKey(update))
		        .flatMap(new Function<Reply<List<User>>, Flowable<List<User>>>() {
			        @Override
			        public Flowable<List<User>>apply(@NonNull Reply<List<User>> listReply) throws Exception {
				        return Flowable.just(listReply.getData());
			        }
		        });
    }

}
