package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import file.MyFile;

/**
 * Created by Ramazan Cinardere} on 24.08.2015.
 */
public class MyFragment extends Fragment {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////


    /////////////////////////////
    //       Constructors      //
    /////////////////////////////

    public MyFragment() {

    }

    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    //Das ist die View f?r jedes einzelne Tab bzw. Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_fragment, container, false);

        Bundle args = getArguments();

        ((TextView) rootView.findViewById(R.id.myFragment_textView)).setText(Integer.toString(args.getInt("key")));
        return rootView;
    }
}
