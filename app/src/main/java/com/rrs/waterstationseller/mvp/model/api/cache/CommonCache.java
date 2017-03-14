package com.rrs.waterstationseller.mvp.model.api.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;


import com.rrs.waterstationseller.mvp.model.entity.User;

import io.reactivex.Flowable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Created by jess on 8/30/16 13:53
 * Contact with jess.yan.effort@gmail.com
 */
public interface CommonCache {



    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Flowable<Reply<List<User>>> getUsers(Flowable<List<User>> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);

}
