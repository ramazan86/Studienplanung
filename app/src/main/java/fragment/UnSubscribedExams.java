package fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.util.ArrayList;

import adapter.MySimpleArrayAdapter;
import data.Module;
import data.ModuleOrganizer;

/**
 * Created by Ramazan Cinardere on 29.08.15.
 */
public class UnSubscribedExams extends ListFragment {

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ModuleOrganizer moduleOrganizer = null;

    private ArrayList<Module> modules = null;

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

        modules = moduleOrganizer.getUnSubscribedModules();

        values = new String[modules.size()];

        for(int i = 0; i<modules.size(); i++){
            values[i] = modules.get(i).getTitle();
        }



        //Angemeldet
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity().getApplicationContext(), values);
        adapter.setActivity(getActivity());
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();


        return inflater.inflate(R.layout.my_fragment, container, false);
    }
}
