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

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import adapter.MySimpleArrayAdapter;
import data.Module;
import data.ModuleManual;
import data.ModuleOrganizer;
import file.MyFile;

/**
 * Created by Ramazan Cinardere} on 24.08.2015.
 */
public class MyFragment extends ListFragment {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ModuleManual moduleManual = null;
    private Module module             = null;

    private ModuleOrganizer moduleOrganizer = null;

    private String page = "";


    /////////////////////////////
    //       Constructors      //
    /////////////////////////////

    public MyFragment() {

    }

    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };


        Bundle args = getArguments();

        moduleManual = (ModuleManual) args.getSerializable(getResources().getString(R.string.moduleManual));
        page = args.getString(getResources().getString(R.string.page));


        //Angemeldet


        Log.e("moduleManual: " +moduleManual.getModuleList().size()," page: " +page);

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity().getApplicationContext(), values);
        setListAdapter(adapter);
    }

    //Das ist die View f?r jedes einzelne Tab bzw. Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment, container, false);
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
