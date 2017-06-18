package com.thoughworks.githubapiintegration;

import com.thoughworks.githubapiintegration.presenter.MainPresenter;
import com.thoughworks.githubapiintegration.view.main.MainView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class MainPresenterTest {
    @Mock
    MainView view;

    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        presenter = new MainPresenter(view);
    }

    @Test
    public void shouldShowToastMessageIfNoInternetIsAvailableAndButtonIsClick() throws Exception {
        boolean internetConnectivity = false;

        presenter.onButtonClick(internetConnectivity);

        verify(view).showToastMessage();
    }

    @Test
    public void shouldLaunchCommitInfoActivityIfInternetIsAvailableAndButtonIsClicked() throws Exception {
        boolean internetConnectivity = true;

        presenter.onButtonClick(internetConnectivity);

        verify(view).launchCommitInfoActivity();
    }
}