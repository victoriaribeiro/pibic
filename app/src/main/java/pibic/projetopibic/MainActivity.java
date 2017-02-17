package pibic.projetopibic;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

       // ImageButton botaoMensagem = (ImageButton) findViewById(R.id.botaoMensagem);




    }

    public void procurarRecados(View minhaview) {

        String token = SharedPrefManager.getInstance(this).getDeviceToken();
        Log.d("VAI", "Notification JSON " + token);
        Intent intent = new Intent(this, mudanca.class);
        startActivity(intent);

    }

    public void procurarLista(View minhaview){
        PdfHttp.URL =" http://e-computacao.com.br/app/";
        Intent intent = new Intent(this, mudancaPdf.class);
        startActivity(intent);
    }

    public void procurarMaterial(View minhaview){
        PdfHttp.URL =" http://e-computacao.com.br/app/lista/";
        Intent intent = new Intent(this, mudancaPdf.class);
        startActivity(intent);

    }


}








