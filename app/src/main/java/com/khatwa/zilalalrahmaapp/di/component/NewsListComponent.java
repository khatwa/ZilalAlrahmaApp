package com.khatwa.zilalalrahmaapp.di.component;

import com.khatwa.zilalalrahmaapp.di.module.NewsListMvpModule;
import com.khatwa.zilalalrahmaapp.di.scope.NewsListFragmentScope;
import com.khatwa.zilalalrahmaapp.ui.NewesList.NewsListFragment;

import dagger.Component;

@NewsListFragmentScope
@Component(dependencies = AppComponent.class, modules = NewsListMvpModule.class)

public interface NewsListComponent {

    void inject(NewsListFragment newsListFragment);

}