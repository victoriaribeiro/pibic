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



public class PdfListFragment extends InternetFragment{
    PdfTask mTask;
    List<UrlPdf> mPdfs;
    ListView mListView;
    TextView mTextNome;
    ProgressBar mProgressBar;
    ListViewAdapterPdf mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
       // mListView.setOnItemClickListener(new ListClickHandler());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_pdfs_list, null);
        mTextNome = (TextView)layout.findViewById(android.R.id.empty);
        mProgressBar = (ProgressBar)layout.findViewById(R.id.progressBar);
        mListView = (ListView)layout.findViewById(R.id.list);
        mListView.setEmptyView(mTextNome);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mPdfs == null) {
            mPdfs = new ArrayList<UrlPdf>();
        }
        mAdapter = new ListViewAdapterPdf(getActivity(),R.layout.list_view, mPdfs);
        mListView.setAdapter(mAdapter);

        if (mTask == null) {
            if (PdfHttp.temConexao(getActivity(),getView())) {

                iniciarDownload();
            } else {
                mTextNome.setText("Sem conexão");
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
            exibirProgress(true);
        }
    }

    private void exibirProgress(boolean exibir) {
        if (exibir) {
            mTextNome.setText("Carregando");
        }
        mTextNome.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    public void iniciarDownload() {
        TextView textViewNome = (TextView) getView().findViewById(R.id.textViewNome);
        MainActivity activity = (MainActivity) getActivity();
        activity.changeToolbarTitle((String)textViewNome.getText());
        textViewNome.setText("");
        if (mTask == null ||  mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new PdfTask();
            mTask.execute();
        }
    }


    class PdfTask extends AsyncTask<Void, Void, List<UrlPdf>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
        }

        @Override
        protected List<UrlPdf> doInBackground(Void... strings) {
            return PdfHttp.carregarpdfsJson();
        }

        @Override
        protected void onPostExecute(List<UrlPdf> recados) {
            super.onPostExecute(recados);
            exibirProgress(false);
            if (recados != null) {
                mPdfs.clear();
                mPdfs.addAll(recados);
                mAdapter.notifyDataSetChanged();
            } else {
                mTextNome.setText("Falha ao obter recados");
            }
        }
    }

}
