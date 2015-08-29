package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import adapter.MyFragmentStatePageAdapter;

/**
 * Created by Ramazan Cinardere on 25.08.15.
 */




/**
 * Work with Fragment to show tabs
 * */

public class MyTabActivity extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ActionBar actionBar = null;
    private ViewPager viewPager = null;

    private MyFragmentStatePageAdapter myFragmentStatePageAdapter = null;

    private TextView textView_add = null;

    private String nameOfCurrentPage = "";

    private android.support.v7.app.ActionBar.Tab[] tabs = null;


    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_view_pager);

        createTabs();

        myFragmentStatePageAdapter = new MyFragmentStatePageAdapter(getSupportFragmentManager(), getApplicationContext());
        myFragmentStatePageAdapter.setCount(getResources().getStringArray(R.array.tab_names).length);
        myFragmentStatePageAdapter.setNameOfCurrentTab(nameOfCurrentPage);

        viewPager = (ViewPager) findViewById(R.id.myViewPager_pager);

        viewPager.setAdapter(myFragmentStatePageAdapter);
        viewPager.setOnPageChangeListener(this);

    }

     private void createTabs() {

         actionBar = getSupportActionBar();
         actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
         actionBar.setDisplayShowTitleEnabled(false);
         actionBar.setDisplayHomeAsUpEnabled(false);
         //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.radial_background));

         LayoutInflater myInlater = LayoutInflater.from(this);
         View customView = myInlater.inflate(R.layout.my_actionbar,null);

         actionBar.setCustomView(customView);
         actionBar.setDisplayShowCustomEnabled(true);

         textView_add = (TextView) customView.findViewById(R.id.myActionbar_textView_add);
         textView_add.setOnClickListener(this);

         tabs = new android.support.v7.app.ActionBar.Tab[getResources().getStringArray(R.array.tab_names).length];

         for(int i = 0; i<getResources().getStringArray(R.array.tab_names).length; i++) {
             tabs[i] = actionBar.newTab();
             tabs[i].setText(getResources().getStringArray(R.array.tab_names)[i]);
             tabs[i].setTabListener(this);
             actionBar.addTab(tabs[i]);
         }

     }


     @Override
     public void setActionBar(Toolbar toolbar) {
         Log.e("setActionBar()" ," " +toolbar.getChildAt(0).getClass().getName());
     }


     @Override
     public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
         Log.e("onTabSelected() at position: " +tab.getPosition()," name: " +tab.getText());
         nameOfCurrentPage = tab.getText().toString();

         if(myFragmentStatePageAdapter != null) {
             myFragmentStatePageAdapter.setNameOfCurrentTab(nameOfCurrentPage);

         }

         if(viewPager != null) {
             viewPager.setCurrentItem(tab.getPosition());
         }

     }

     @Override
     public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
         Log.e("onTabUnselected() at position: " +tab.getPosition()," name: " +tab.getText());

     }

     @Override
     public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
         Log.e("onTabReselected() at position: " +tab.getPosition()," name: " +tab.getText());


     }

     @Override
     public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

     }

     @Override
     public void onPageSelected(int position) {
         getSupportActionBar().setSelectedNavigationItem(position);


         Log.e("onPageSelected() "," position: " +position);

         TextView textView = (TextView) findViewById(R.id.myActionbar_textView_add);

         if(position != 0) {
             textView.setVisibility(View.INVISIBLE);
             //myFragmentStatePageAdapter.notifyDataSetChanged();

         }else {
             textView.setVisibility(View.VISIBLE);
         }


     }

     @Override
     public void onPageScrollStateChanged(int state) {

     }


    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.myActionbar_textView_add) {
            startActivity(new Intent(this, SubscribeExamActivity.class).putExtra(getResources().getString(R.string.layoutId), R.layout.add_exam));
        }


    }
}
