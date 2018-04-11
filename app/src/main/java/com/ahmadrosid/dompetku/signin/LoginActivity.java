package com.ahmadrosid.dompetku.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.main.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements Login.View {

    @BindView(R.id.google_signin_button)
    Button googleSigninButton;

    private Login.Presenter presenter;

    private int RC_SIGN_IN = 7001;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenter(this);
    }


    @OnClick(R.id.google_signin_button)
    public void onViewClicked() {
        presenter.doLogin();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void doGoogleSignIn(GoogleSignInClient signInClient) {
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            presenter.loginResult(data);
        }
    }

    @Override
    public void loginFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successLogin() {
        MainActivity.start(this);
        finish();
    }

}
