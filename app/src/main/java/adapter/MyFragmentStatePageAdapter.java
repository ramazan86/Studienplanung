package adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cinardere_ramazan_ba_2015.studienplanung.MyTabActivity;
import com.cinardere_ramazan_ba_2015.studienplanung.R;

import data.Module;
import data.ModuleManual;
import file.MyFile;
import fragment.Android;
import fragment.IOS;
import fragment.MyFragment;
import fragment.Windows;

/**
 * Created by Ramazan Cinardere on 25.08.15.
 */
public class MyFragmentStatePageAdapter extends FragmentStatePagerAdapter {

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private int count;

    private MyFile myFile = null;

    private ModuleManual moduleManual = null;
    private Module module             = null;

    private Context context = null;

    private String nameOfCurrentTabPage = "";

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////

    public MyFragmentStatePageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }



    ////////////////////////////
    //         Methods        //
    ////////////////////////////

    @Override
    public Fragment getItem(int position) {

        /*switch (position) {

            case 0: showEnrolledExams();     break;
            case 1: showUnSubscribedExams(); break;
            case 2: showFinishedExams();     break;

        }

        Fragment fragment = new MyFragment();
        Bundle args = new Bundle();

            args.putSerializable(context.getResources().getString(R.string.moduleManual), moduleManual);
            args.putString(context.getResources().getString(R.string.page), nameOfCurrentTabPage);
            args.putInt("pos", position);

        fragment.setArguments(args);
        return fragment;*/


        switch (position) {

            case 0: return new Android();
            case 1: return new IOS();
            case 2: return new Windows();
        }
        return null;
    }

/*
    @Override
    public int getItemPosition(Object object) {

        MyFragment myFragment = (MyFragment) object;
        int pos = myFragment.getPosition();


        if(pos >= 0) {
            return pos;
        }else {
            return POSITION_NONE;
        }

    }*/

    private void showFinishedExams() {

    }

    private void showUnSubscribedExams() {

    }

    private void showEnrolledExams() {

        moduleManual = (ModuleManual) new MyFile(context).getObjectFromFile(context.getResources().getString(R.string.moduleManualSer));

    }

    @Override
    public int getCount() {
        //return count;
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Object " +(position+1);
    }

    //set the count of total tabs/pages
    public void setCount(int count) {
        this.count = count;
    }

    public void setNameOfCurrentTab(String nameOfCurrentPage) {
        this.nameOfCurrentTabPage = nameOfCurrentPage;
    }
}
