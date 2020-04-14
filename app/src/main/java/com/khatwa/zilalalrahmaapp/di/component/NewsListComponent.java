package com.khatwa.zilalalrahmaapp.di.component;

import com.khatwa.zilalalrahmaapp.di.module.NewsListMvpModule;
import com.khatwa.zilalalrahmaapp.di.scope.NewsListFragmentScope;
import com.khatwa.zilalalrahmaapp.ui.NewsList.LastNewsFragment;

import dagger.Component;

@NewsListFragmentScope
@Component(dependencies = AppComponent.class, modules = NewsListMvpModule.class)

public interface NewsListComponent {

    void inject(LastNewsFragment lastNewsFragment);

}