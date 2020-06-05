package com.example.capstone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    //카카오
    private SessionCallback sessionCallback;

    //네이버
    private static String ID = "h5zWuad4aNh7mnhB5nph";
    private static String SECRET = "cStKOHwftZ";
    private static String NAME = "네이버 아이디로 로그인";
    public static OAuthLoginButton naverLogInButton;
    public static OAuthLogin naverLoginInstance;

    public static String Nickname,Realname,Email;
    TextView tv_mail;
    public Context context;
    Button btnGetApi, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //카카오
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_test);
        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();


        //네이버
        init();
        init_View();
    }

    //////////////카카오////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            Toast.makeText(getApplicationContext(), "로그인 시작(OnSessionOpened)", Toast.LENGTH_SHORT).show();
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("name", result.getNickname());
                    intent.putExtra("profile", result.getProfileImagePath());
                    Toast.makeText(getApplicationContext(), "로그인을 완료했습니다.(OnSuccess)", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /////////네이버////////////
//    /**
//     * OAuthLoginHandler를 startOAuthLoginActivity() 메서드 호출 시 파라미터로 전달하거나 OAuthLoginButton
//     객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.
//     */
//    @SuppressLint("HandlerLeak")
//    static private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
//        @Override
//        public void run(boolean success) {
//            if (success) {
//                String accessToken = naverLoginInstance.getAccessToken(mContext);
//                String refreshToken = naverLoginInstance.getRefreshToken(mContext);
//                long expiresAt = naverLoginInstance.getExpiresAt(mContext);
//                String tokenType = naverLoginInstance.getTokenType(mContext);
////                mOauthAT.setText(accessToken);
////                mOauthRT.setText(refreshToken);
////                mOauthExpires.setText(String.valueOf(expiresAt));
////                mOauthTokenType.setText(tokenType);
////                mOAuthState.setText(mOAuthLoginModule.getState(mContext).toString());
//            } else {
//                String errorCode = naverLoginInstance.getLastErrorCode(mContext).getCode();
//                String errorDesc = naverLoginInstance.getLastErrorDesc(mContext);
//                Toast.makeText(mContext, "errorCode:" + errorCode
//                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
//            }
//        }
//    };


    /////////////////////////////네이버///////////////////////////////////////////

    //초기화
    private void init(){
        context = this;
        naverLoginInstance = OAuthLogin.getInstance();
        naverLoginInstance.init(this,ID,SECRET,NAME);
    }
    //변수 붙이기
    private void init_View(){
        naverLogInButton = (OAuthLoginButton)findViewById(R.id.buttonNaverLogin);

        //로그인 핸들러
        OAuthLoginHandler naverLoginHandler  = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {//로그인 성공
                    Toast.makeText(context,"로그인 성공",Toast.LENGTH_SHORT).show();
                } else {//로그인 실패
                    String errorCode = naverLoginInstance.getLastErrorCode(context).getCode();
                    String errorDesc = naverLoginInstance.getLastErrorDesc(context);
                    Toast.makeText(context, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            }

        };;

        naverLogInButton.setOAuthLoginHandler(naverLoginHandler);
        tv_mail = (TextView)findViewById(R.id.textView4);
        btnGetApi = (Button)findViewById(R.id.btngetapi);
        btnGetApi.setOnClickListener(this);
        btnLogout = (Button)findViewById(R.id.logout2);
        btnLogout.setOnClickListener(this);

    }

    public void onClick(View v) {
        if(v.getId() == R.id.btngetapi){
            new RequestApiTask().execute();//static이 아니므로 클래스 만들어서 시행.
        }
        if(v.getId() == R.id.logout2){
            naverLoginInstance.logout(context);
            tv_mail.setText((String) "");//메일 란 비우기
        }
    }


    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {//작업이 실행되기 전에 먼저 실행.
            tv_mail.setText((String) "");//메일 란 비우기
        }

        @Override
        protected String doInBackground(Void... params) {//네트워크에 연결하는 과정이 있으므로 다른 스레드에서 실행되어야 한다.
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = naverLoginInstance.getAccessToken(context);
            return naverLoginInstance.requestApi(context, at, url);//url, 토큰을 넘겨서 값을 받아온다.json 타입으로 받아진다.
        }

        protected void onPostExecute(String content) {//doInBackground 에서 리턴된 값이 여기로 들어온다.
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject response = jsonObject.getJSONObject("response");
                String email = response.getString("email");
                tv_mail.setText(email);//메일 란 채우기
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}