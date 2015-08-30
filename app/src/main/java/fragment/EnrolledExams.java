package fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.util.ArrayList;

import adapter.MySimpleArrayAdapter;
import data.Module;
import data.ModuleManual;
import data.ModuleOrganizer;
import file.MyFile;

/**
 * Created by Ramazan Cinardere on 28.08.15.
 */
public class EnrolledExams extends ListFragment {

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ArrayList<Module> modules = null;

    private MyFile myFile = null;

    private ModuleManual moduleManual = null;

    private ModuleOrganizer moduleOrganizer = null;

    private String[] values = null;

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        moduleOrganizer = new ModuleOrganizer(getActivity().getApplicationContext());

        modules = moduleOrganizer.getEnrolledModules();

        values = new String[modules.size()];

        for(int i = 0; i<modules.size(); i++){
            values[i] = modules.get(i).getTitle();
        }


        //Angemeldet
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity().getApplicationContext(), values, this);
        adapter.setActivity(getActivity());
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();

        return inflater.inflate(R.layout.my_fragment, container, false);
    }
}
