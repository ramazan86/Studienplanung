package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import data.Module;
import helper.MyHelper;

/**
 * Created by Ramazan Cinardere on 06.09.15.
 */
public class CustomModuleListViewAdapter extends ArrayAdapter {


    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private Context context = null;
    private String[] values = null;

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////

    public CustomModuleListViewAdapter(Context context, String[] values) {
        super(context, R.layout.custom_semester_total_view, values);
        this.context = context;
        this.values  = values;
    }

    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = convertView;
             v = layoutInflater.inflate(R.layout.custom_semester_total_view, null);


        TextView textView = (TextView) v.findViewById(R.id.customSemesterTotalView_textView);

        if(position == values.length-1 && values[position].contains("Gesamt")){
            textView.setTypeface(Typeface.createFromAsset(context.getAssets(), MyHelper.FONTS[6]));
        }else {
            textView.setTypeface(Typeface.createFromAsset(context.getAssets(), MyHelper.FONTS[5]));
        }

        textView.setText(values[position]);

        return v;
    }


}
