package pibic.projetopibic;

/**
 * Created by sdmai on 08/02/2017.
 */

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


//Class extending FirebaseInstanceIdService

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    public static final String URL_DO_SERVIDOR =  "http://www.e-computacao.com.br/app/token.php";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        enviarRegistrationIdParaServidor(refreshedToken);
        storeToken(refreshedToken);
    }

    private void storeToken(String token) {
        //saving the token on shared preferences
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }

    private void enviarRegistrationIdParaServidor(final String key) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(URL_DO_SERVIDOR);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    conexao.setRequestMethod("POST");
                    conexao.setDoOutput(true);
                    OutputStream os = conexao.getOutputStream();
                    os.write(("acao=registrar&regId=" + key).getBytes());
                    os.flush();
                    os.close();
                    conexao.connect();
                    int responseCode = conexao.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d("TOKEN", "FUNCIONOU");
                    } else {
                        throw new RuntimeException("Erro ao salvar no servidor");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }



}