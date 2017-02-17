package pibic.projetopibic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by victoria on 01/02/16.
 */
public class SplashActivity extends Activity implements Runnable {

    private Handler handler;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);
        handler = new Handler();
        handler.postDelayed(this, 2000);
    }



    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(this);

    }



    @Override

    public void run() {

        Intent it = new Intent(this, MainActivity.class);

        startActivity(it);

        finish();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }



}