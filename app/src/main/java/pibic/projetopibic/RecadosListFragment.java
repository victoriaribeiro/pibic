package pibic.projetopibic;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecadosListFragment extends InternetFragment{
    RecadosTask mTask;
    List<recado> mRecados;
    ListView mListView;
    TextView mTextMensagem;
    ProgressBar mProgressBar;
    ListViewAdapterRecado mAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_recados_list, null);
        mTextMensagem = (TextView)layout.findViewById(android.R.id.empty);
        mProgressBar = (ProgressBar)layout.findViewById(R.id.progressBar);
        mListView = (ListView)layout.findViewById(R.id.list);
        mListView.setEmptyView(mTextMensagem);

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mRecados == null) {
            mRecados = new ArrayList<recado>();
        }
        mAdapter = new ListViewAdapterRecado(getActivity(),R.layout.list_view, mRecados);
        mListView.setAdapter(mAdapter);

        if (mTask == null) {
            if (RecadoHttp.temConexao(getActivity(),getView())) {

                iniciarDownload();
            } else {
                mTextMensagem.setText("Sem conexão");
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
            exibirProgress(true);
        }
    }

//    public void updateToolbar(){
//        String ip = Utils.getIPAddress(true);
//        if(ip.contains("192.168")){
//            PdfHttp.PDF_URL_JSON = PdfHttp.URL + "pesquisaPdfPreCalc.json";
//            getActivity().getSupportActionBar().setTitle("Pré calculo");
//        }
//    }

    private void exibirProgress(boolean exibir) {
        if (exibir) {
            mTextMensagem.setText("Carregando");
        }
        mTextMensagem.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    public void iniciarDownload() {
        TextView textViewNome = (TextView) getView().findViewById(R.id.textViewNome);
        MainActivity activity = (MainActivity) getActivity();
        activity.changeToolbarTitle((String)textViewNome.getText());
        textViewNome.setText("");
        if (mTask == null ||  mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new RecadosTask();
            mTask.execute();
        }
    }

    class RecadosTask extends AsyncTask<Void, Void, List<recado>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
        }

        @Override
        protected List<recado> doInBackground(Void... strings) {
            return RecadoHttp.carregarRecadosJson();
        }

        @Override
        protected void onPostExecute(List<recado> recados) {
            super.onPostExecute(recados);
            exibirProgress(false);
            if (recados != null) {
                mRecados.clear();
                mRecados.addAll(recados);
                mAdapter.notifyDataSetChanged();
            } else {
                mTextMensagem.setText("Falha ao obter recados");
            }
        }
    }
}
