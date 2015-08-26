package adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fragment.MyFragment;

/**
 * Created by Ramazan Cinardere on 25.08.15.
 */
public class MyFragmentStatePageAdapter extends FragmentStatePagerAdapter {

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private int count;

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////

    public MyFragmentStatePageAdapter(FragmentManager fm) {
        super(fm);
    }



    ////////////////////////////
    //         Methods        //
    ////////////////////////////

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0: showEnrolledExams(); break;



        }







        Fragment fragment = new MyFragment();
        Bundle args = new Bundle();
            args.putInt("key", position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    private void showEnrolledExams() {

    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Object " +(position+1);
    }

    public void setCount(int count) {
        this.count = count;
    }
}
