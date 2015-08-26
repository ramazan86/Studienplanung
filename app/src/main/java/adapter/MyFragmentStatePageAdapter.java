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

        Fragment fragment = new MyFragment();
        Bundle args = new Bundle();
            args.putInt("key", position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Object " +(position+1);
    }
}
