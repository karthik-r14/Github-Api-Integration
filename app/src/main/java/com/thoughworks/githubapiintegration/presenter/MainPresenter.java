package com.thoughworks.githubapiintegration.presenter;

import com.thoughworks.githubapiintegration.view.main.MainView;

public class MainPresenter {
    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void onButtonClick(boolean internetConnectivity) {

        if (internetConnectivity) {
            view.launchCommitInfoActivity();
        } else {
            view.showToastMessage();
        }
    }
}
