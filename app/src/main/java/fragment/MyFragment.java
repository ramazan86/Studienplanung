package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.util.ArrayList;

import adapter.MySimpleArrayAdapter;
import data.Module;
import data.ModuleManual;
import data.ModuleOrganizer;
import dialog.MyAlertDialog;
import file.MyFile;

/**
 * Created by Ramazan Cinardere} on 24.08.2015.
 */
public abstract class MyFragment extends ListFragment{

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ModuleManual moduleManual = null;
    private Module module             = null;

    private ModuleOrganizer moduleOrganizer = null;

    private String[] values = null;

    private MySimpleArrayAdapter adapter = null;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////

    public MyFragment(String[] values) {
        this.values = values;
    }

    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    //Das ist die View f?r jedes einzelne Tab bzw. Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.e("onCreateView()"," #########");
        Log.e("valuesLenght: "," " +values.length);

        View rootView = inflater.inflate(R.layout.my_fragment, container, false);

        //Angemeldet
            adapter = new MySimpleArrayAdapter(getActivity().getApplicationContext(), values);
            adapter.setActivity(getActivity());
            setListAdapter(adapter);
            adapter.notifyDataSetChanged();
        return rootView;

    }














}
