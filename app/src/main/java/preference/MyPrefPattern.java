package preference;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

/**
 * Created by Ramazan Cinardere .
 */
public abstract class MyPrefPattern extends ActionBarActivity {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    public final int CONTENT = android.R.id.content;
    public Fragment myFrag = null;


    /////////////////////////////
    //       Constructors      //
    /////////////////////////////


    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(CONTENT, myFrag);
            fragmentTransaction.commit();

        setActionBar();
    }

    public void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.radial_background));

        LayoutInflater myInlater = LayoutInflater.from(this);
        View customView = myInlater.inflate(R.layout.my_actionbar,null);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);
    }


}
