package pibic.projetopibic;

//import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;

/**
 * Created by projcampi on 22/06/16.
 */


public abstract class InternetFragment extends Fragment {
    private ConexaoReceiver mReceiver;

    @Override
    public void onResume(){
        super.onResume();
        mReceiver = new ConexaoReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mReceiver,filter);

    }

    public void onPause(){
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
    }

    public abstract void iniciarDownload();

    class ConexaoReceiver extends BroadcastReceiver {
        boolean mPrimeiraVez =  true;

        @Override
        public void onReceive(Context context, Intent intent) {
            if(mPrimeiraVez){
                mPrimeiraVez = false;
                return;
            }

            if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                if(RecadoHttp.temConexao(context,getView()) || PdfHttp.temConexao(context,getView())){
                    iniciarDownload();
                }
            }
        }
    }
}


