package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.util.List;

import helper.RowItem;

/**
 * Created by Ramazan Cinardere} on 01.04.2015.
 */
public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private Context context = null;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////

    public CustomListViewAdapter(Context context, int resourceId, List<RowItem> itemms) {
        super(context, resourceId, itemms);
        this.context = context;

    }

    /////////////////////////////
    //         Methods         //
    /////////////////////////////

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_view_settings, null);
            holder = new ViewHolder();
            //holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.adapter_view_user);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        //holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }




    /////////////////////////////
    //         Inner-class     //
    /////////////////////////////

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }


}
