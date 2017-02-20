package pibic.projetopibic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PdfHttp  {
    public static String URL = "http://e-computacao.com.br/app/";
    public static String PDF_URL_JSON = "http://e-computacao.com.br/app/";

    private static HttpURLConnection connectar(String urlArquivo) throws IOException {
        final int SEGUNDOS = 1000;
        java.net.URL url = new URL(urlArquivo);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setReadTimeout(10 * SEGUNDOS);
        conexao.setConnectTimeout(15 * SEGUNDOS);
        conexao.setRequestMethod("GET");
        conexao.setDoInput(true);
        conexao.setDoOutput(false);
        conexao.connect();
        return conexao;
    }

    public static boolean temConexao(Context ctx, View view) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        String ip = Utils.getIPAddress(true);
        TextView textViewNome = (TextView) view.findViewById(R.id.textViewNome);


        if(ip.contains("192.168.")){
            PDF_URL_JSON = URL + "pesquisaPdfPreCalc.json";
            textViewNome.setText("Pré Cálculo");

        }
        else{
            PDF_URL_JSON = URL + "pesquisaPdfGA.json";
            textViewNome.setText("Geometria Analítica");
        }
        return (info != null && info.isConnected());
    }

    public static List<UrlPdf> carregarpdfsJson() {

        try {
            HttpURLConnection conexao = connectar(PDF_URL_JSON);

            int resposta = conexao.getResponseCode();
            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(is));
                return lerJsonpdfs(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<UrlPdf> lerJsonpdfs(JSONObject json) throws JSONException {
        List<UrlPdf> listaDepdfs = new ArrayList<UrlPdf>();

        JSONArray jsonPdfs = json.getJSONArray("PDF");
        for (int j = 0; j < jsonPdfs.length(); j++) {
            JSONObject jsonPdf = jsonPdfs.getJSONObject(j);

            UrlPdf pdf = new UrlPdf(
                    jsonPdf.getString("nome"),
                    jsonPdf.getString("url"),
                    jsonPdf.getString("data")
            );

            listaDepdfs.add(pdf);

        }

        return listaDepdfs;
    }

    private static String bytesParaString(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        // O bufferzao vai armazenar todos os bytes lidos
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        // precisamos saber quantos bytes foram lidos
        int bytesLidos;
        // Vamos lendo de 1KB por vez...
        while ((bytesLidos = is.read(buffer)) != -1) {
            // copiando a quantidade de bytes lidos do buffer para o bufferzão
            bufferzao.write(buffer, 0, bytesLidos);
        }
        return new String(bufferzao.toByteArray(), "UTF-8");
    }
}
