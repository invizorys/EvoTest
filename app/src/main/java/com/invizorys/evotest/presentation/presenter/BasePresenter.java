package com.invizorys.evotest.presentation.presenter;

import com.invizorys.evotest.presentation.view.BaseView;

/**
 * Created by Roma on 25.02.2017.
 */

public abstract class BasePresenter<V extends BaseView> {
    protected final String TAG = this.getClass().getSimpleName();
    private V view;

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public V getView() {
        return view;
    }

    public boolean isViewAttached() {
        return view != null;
    }
}

