package pibic.projetopibic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


public class RecadoHttp {
    public static final String URL = "http://e-computacao.com.br/app/";
    public static String recadoUrlJson ="";


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
            recadoUrlJson = URL +"pesquisaPreCalc.json";
            textViewNome.setText("Pré Cálculo");
        }
        else{
            recadoUrlJson = URL + "pesquisaGA.json";
            textViewNome.setText("Geometria Analítica");
        }
        return (info != null && info.isConnected());
    }

    public static List<recado> carregarRecadosJson() {
        try {
            HttpURLConnection conexao = connectar(recadoUrlJson);

            int resposta = conexao.getResponseCode();
            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(is));
                return lerJsonRecados(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<recado> lerJsonRecados(JSONObject json) throws JSONException {
        List<recado> listaDeRecados = new ArrayList<recado>();


            JSONArray jsonRecados = json.getJSONArray("recados");
            for (int j = 0; j < jsonRecados.length(); j++) {
                JSONObject jsonRecado = jsonRecados.getJSONObject(j);

                recado Recado = new recado(
                        jsonRecado.getString("mensagem"),
                        jsonRecado.getString("data")
                );

                listaDeRecados.add(Recado);

            }
          return listaDeRecados;
        }




    private static String bytesParaString(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];

        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int bytesLidos;

        while ((bytesLidos = is.read(buffer)) != -1) {
            bufferzao.write(buffer, 0, bytesLidos);
        }
        return new String(bufferzao.toByteArray(), "UTF-8");
    }
}
