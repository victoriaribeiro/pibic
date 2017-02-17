package pibic.projetopibic;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by victoria on 30/06/16.
 */
public class ListViewAdapterPdf extends ArrayAdapter<UrlPdf> {


    Context context;

    public ListViewAdapterPdf(Context context, int resourceId, List<UrlPdf> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtNome;
        TextView txtData;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        UrlPdf rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_view, null);
            holder = new ViewHolder();
            holder.txtData = (TextView) convertView.findViewById(R.id.data);
            holder.txtNome = (TextView) convertView.findViewById(R.id.mens);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
//        View v = mInflater.inflate(R.layout.fragment_pdfs_list,null);
//        ListView list = (ListView) v.findViewById(R.id.list);
//        if(list == null){
//
//            Toast.makeText(getContext(), "NULL", Toast.LENGTH_SHORT).show();
//
//        }
        holder.txtData.setText(rowItem.getData());
        holder.txtNome.setText(rowItem.getNome());
//
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), "Exemplo Toast", Toast.LENGTH_SHORT).show();
//            }
//
////            @Override
////            public void onClick(View v) {
////                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
////            }
//        });

//

        return convertView;

    }


}
