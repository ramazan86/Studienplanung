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
public class MyFragment extends ListFragment{

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ModuleManual moduleManual = null;
    private Module module             = null;

    private ModuleOrganizer moduleOrganizer = null;

    private String page = "";

    private String[] values = null;

    private int position = 0;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////

    public MyFragment() {}

    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    //Das ist die View f?r jedes einzelne Tab bzw. Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getAssignedValues();
        ArrayList<Module> modules = null;

        //Angemeldet
        if(page.equals(getResources().getStringArray(R.array.tab_names)[0])) {

            modules = moduleOrganizer.getEnrolledModules();
            values = new String[modules.size()];

        }//Abgemeldet
        else if(page.equals(getResources().getStringArray(R.array.tab_names)[1])){

            modules = moduleOrganizer.getUnSubscribedModules();
            values = new String[modules.size()];

        }else {
            values = new String[] {"A","B","C"};
        }

        if(modules != null) {
            for(int i = 0; i<modules.size(); i++) {
                values[i] = modules.get(i).getTitle();
            }
        }


        View rootView = inflater.inflate(R.layout.my_fragment, container, false);

        //Angemeldet
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity().getApplicationContext(), values);
            adapter.setActivity(getActivity());
            setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        return rootView;

    }


    private void setValuesIntoArray(int position) {


    }

    private void getAssignedValues() {

        Bundle args = getArguments();

        position = args.getInt("pos");
        moduleManual = (ModuleManual) args.getSerializable(getResources().getString(R.string.moduleManual));
        page = args.getString(getResources().getString(R.string.page));
        moduleOrganizer = new ModuleOrganizer(getActivity().getApplicationContext());
    }


    @Override
    public void onListItemClick(final ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
    }




    public int getPosition() {
        return position;
    }




    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Bundle args = getArguments();

        moduleManual = (ModuleManual) args.getSerializable(getResources().getString(R.string.moduleManual));
        page = args.getString(getResources().getString(R.string.page));
        moduleOrganizer = new ModuleOrganizer(getActivity().getApplicationContext());

        ArrayList<Module> modules = null;

        //Angemeldet
        if(page.equals(getResources().getStringArray(R.array.tab_names)[0])) {


            modules = moduleOrganizer.getEnrolledModules();
            values = new String[modules.size()];

        }//Abgemeldet
        else if(page.equals(getResources().getStringArray(R.array.tab_names)[1])){

            modules = moduleOrganizer.getUnSubscribedModules();
            values = new String[modules.size()];

        }else {
            values = new String[] {"A","B","C"};
        }

        if(modules != null) {
            for(int i = 0; i<modules.size(); i++) {
                values[i] = modules.get(i).getTitle();
            }
        }

        //Angemeldet
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity().getApplicationContext(), values);
        adapter.setActivity(getActivity());
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
*/



}
