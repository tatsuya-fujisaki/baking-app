package com.example.android.bakingapp.dagger;

import android.app.Application;

import com.example.android.bakingapp.dagger.module.ActivityModule;
import com.example.android.bakingapp.dagger.module.ApplicationModule;
import com.example.android.bakingapp.dagger.module.FragmentModule;
import com.example.android.bakingapp.dagger.module.ServiceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class, ActivityModule.class, FragmentModule.class, ServiceModule.class})
public interface ApplicationComponent extends AndroidInjector<MyApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder application(Application application);

        ApplicationComponent build();
    }

    @Override
    void inject(MyApplication myApplication);
}