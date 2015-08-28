package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

/**
 * @author  Ramazan Cinardere on 28.08.15.
 */


/*Diese Klasse wird von MyFragment benutzt*/
public class MySimpleArrayAdapter extends ArrayAdapter <String>{

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private Context context = null;
    private String[] values = null;

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    public MySimpleArrayAdapter(Context context, String[] values) {
        super(context, R.layout.my_rowlayout, values);
        this.context = context;
        this.values  = values;
    }

    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = inflater.inflate(R.layout.my_rowlayout, parent, false);

        //get components of view
        TextView textView = (TextView) rootView.findViewById(R.id.myRowlayout_imageView_text);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.myRowlayout_imageView_image);

        textView.setText(values[position]);
        imageView.setImageResource(R.drawable.examination);

        return rootView;
    }
}
