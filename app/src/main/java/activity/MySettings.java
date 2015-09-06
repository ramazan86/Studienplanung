package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.util.ArrayList;
import java.util.List;

import adapter.CustomListViewAdapter;
import file.MyFile;
import helper.MyHelper;
import helper.RowItem;
import preference.PrefEmail;
import preference.PrefProfile;

/**
 * Created by Ramazan Cinardere} on 26.03.2015.
 */
public class MySettings extends ActionBarActivity implements AdapterView.OnItemClickListener {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private final String[] titles = {"Profil", "Email"};
    private final String[] descriptions = {"A", "B",};
    private final Integer[] images = {R.drawable.ic_action_person,R.drawable.ic_action_email_48x48};

    private ListView listView = null;
    private List<RowItem> rowItems;
    private MyFile myFile = null;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////

    public MySettings() {}

    /////////////////////////////
    //         Methods         //
    /////////////////////////////




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
        setContentView(R.layout.setting_main);

        rowItems = new ArrayList<RowItem>();

        for(int i = 0; i<titles.length; i++) {
            RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.setting_main_listView);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.adapter_view_settings, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    public void setActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        switch (position) {

            case 0 :
                startActivity(new Intent(this, PrefProfile.class));break;

            case 1:
                startActivity(new Intent(this, PrefEmail.class));
                break;

        }
        myFile = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }




}
