package com.multiventas.app.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PujasAdminViewModelFactory implements ViewModelProvider.Factory{
    private final Application application;

    public PujasAdminViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PujasAdminViewModel(application);
    }
}
