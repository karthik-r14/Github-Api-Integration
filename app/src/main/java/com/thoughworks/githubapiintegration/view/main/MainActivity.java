package com.thoughworks.githubapiintegration.view.main;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.thoughworks.githubapiintegration.presenter.MainPresenter;
import com.thoughworks.githubapiintegration.R;
import com.thoughworks.githubapiintegration.view.commitInfo.CommitInfoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
    }

    @OnClick(R.id.button)
    public void onButtonClick() {
        boolean internetConnectivity = connectivityAvailable();

        presenter.onButtonClick(internetConnectivity);
    }

    public boolean connectivityAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void showToastMessage() {
        Toast.makeText(getApplicationContext(), R.string.no_internet_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchCommitInfoActivity() {
        Intent intent = new Intent(MainActivity.this, CommitInfoActivity.class);
        startActivity(intent);
    }
}
