package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

/**
 * Created by Ramazan Cinardere on 28.08.15.
 */
public class Android extends Fragment {

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////


    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            View android = inflater.inflate(R.layout.android_frag, container, false);
            ((TextView)android.findViewById(R.id.textView)).setText("Android");
            return android;

    }
}
