package com.khatwa.zilalalrahmaapp.di.component;

import com.khatwa.zilalalrahmaapp.di.module.NewsDetailsMvpModule;
import com.khatwa.zilalalrahmaapp.di.scope.NewsDetailsActivityScope;
import com.khatwa.zilalalrahmaapp.ui.NewsDetails.NewsDetailsActivity;

import dagger.Component;

@NewsDetailsActivityScope
@Component(dependencies = AppComponent.class, modules = NewsDetailsMvpModule.class)
public interface NewsDetailsComponent {

    void inject(NewsDetailsActivity newsDetailsActivity);

}