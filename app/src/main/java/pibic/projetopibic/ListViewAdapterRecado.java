package pibic.projetopibic;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapterRecado extends ArrayAdapter<recado> {

    Context context;

    public ListViewAdapterRecado(Context context, int resourceId, List<recado> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtMens;
        TextView txtData;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        recado rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_view, null);
            holder = new ViewHolder();
            holder.txtData = (TextView) convertView.findViewById(R.id.data);
            holder.txtMens = (TextView) convertView.findViewById(R.id.mens);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtData.setText(rowItem.getData());
        holder.txtMens.setText(rowItem.getMensagem());

        return convertView;
    }
}