package com.rrs.waterstationseller.di.component;

import com.jess.arms.di.scope.ActivityScope;
import com.rrs.waterstationseller.di.module.UserModule;
import com.rrs.waterstationseller.mvp.ui.activity.UserActivity;

import common.AppComponent;
import dagger.Component;

/**
 * Created by jess on 9/4/16 11:17
 * Contact with jess.yan.effort@gmail.com
 */
@ActivityScope
@Component(modules = UserModule.class,dependencies = AppComponent.class)
public interface UserComponent {
    void inject(UserActivity activity);
}
