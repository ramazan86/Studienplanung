package view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.io.LineNumberReader;
import java.lang.reflect.Type;

import data.Module;
import data.ModuleManual;
import helper.MyHelper;

/**
 * Created by Ramazan Cinardere} on 20.08.2015.
 */
public class ModuleTotalView extends ActionBarActivity {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private TextView textView_headline = null;
    private LinearLayout.LayoutParams params  = null;

    private LinearLayout linearLayout_modules = null,
                         linearLayout_row     = null;
    private TextView textView_value   = null;
    private View view_verticalLine    = null;
    private ModuleManual moduleManual = null;

    private final int LINEAR_LAYOUT_HEIGHT = 150;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////


    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.totalView));
        setContentView(R.layout.module_total_view);
        //setContentView(R.layout.test);
        initComponents();

        moduleManual = (ModuleManual) getIntent().getExtras().getSerializable("moduleManual");

        assignValuesToComponents();
    }

    private void initComponents() {
        //textView headline
        //textView_headline = (TextView) findViewById(R.id.module_totalView_textViewHeadline);

        //linearLayout
        linearLayout_modules = (LinearLayout) findViewById(R.id.module_totalView_linearLayout_modules);
    }

    private void assignValuesToComponents() {

        Module module = moduleManual.getModuleList().get(0);
        //textview headline
        createHeadLine();

        //Each linearLayout_row shows one module
        createLinearLayoutForEachModule();


        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            LinearLayout linearRow = createLinearLayoutForEachModule();

            for(int k = 0; k<Module.NUMBER_OF_MODULE_VIEW_COLUMNS; k++) {

                TextView textView_module = createTextView(moduleManual.getModuleList().get(i).toString(k), k);
                linearRow.addView(textView_module);

                if(k != (Module.NUMBER_OF_MODULE_VIEW_COLUMNS - 1)) {
                    View view = createVerticalLine();
                    linearRow.addView(view);
                }
            }//for(int k
            linearLayout_modules.addView(linearRow);
        }



        //add view into linearlayout_wrap
        //linearLayout_modules.addView(linearLayout_row);
        //linearLayout_modules.addView(linear);
    }

    private void addViewIntoLinearLayoutRow(View view) {

        linearLayout_row.addView(view);
        view = null;
    }

    private void createHeadLine() {

        if(textView_headline != null) {
            textView_headline.setText(getResources().getString(R.string.totalView));
            checkIfParamsIsInitialized();

            params = (LinearLayout.LayoutParams) textView_headline.getLayoutParams();

            textView_headline.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            textView_headline.setTextSize(getResources().getDimension(R.dimen.dimen_2));
            textView_headline.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    private LinearLayout createLinearLayoutForEachModule() {

        LinearLayout row = new LinearLayout(this);
            //LayoutParams : 1st Param: width, 2nd Param: height
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //row.setBackgroundColor(getResources().getColor(R.color.light_blue_2));
            row.setBackgroundColor(getResources().getColor(R.color.light_blue_2));
                //linearlayout margin
                params = (LinearLayout.LayoutParams) row.getLayoutParams();
                params.setMargins(30, 20, 0, 20);
            row.setLayoutParams(params);


        GradientDrawable gd = new GradientDrawable();
            gd.setColor(getResources().getColor(R.color.light_blue_2));
            gd.setCornerRadius(5);
            gd.setStroke(1, 0xFF000000);

            row.setBackgroundDrawable(gd);
        return row;
    }

    private View createVerticalLine( ) {

        View v = new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(5, ViewGroup.LayoutParams.MATCH_PARENT));

        checkIfParamsIsInitialized();
        params = (LinearLayout.LayoutParams) v.getLayoutParams();
        params.setMargins(0, 8, 4, 8);

        v.setLayoutParams(params);
        v.setBackgroundColor(getResources().getColor(R.color.white));

        return v;
    }

    private TextView createTextView(String content, int i ) {

        int width = 0;

        switch (i) {
            case 0: width = 140; break;
            case 1: width = 612; break;
            case 2: width = 160;  break;
            case 3: width = 345; break;
            case 4: width = 490; break;
            case 5: width = 493; break;
            case 6: width = 372; break;
            case 7: width = 100; break;
        }



        TextView textView= new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));

        checkIfParamsIsInitialized();

        params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        int marginRight = Math.round(getResources().getDimension(R.dimen.dimen_1));
        int textSize = Math.round(getResources().getDimension(R.dimen.dimen_5));
            params.setMargins(0, 0, marginRight, 0);

            textView.setText(content);
            textView.setTextSize(20);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.black));

        return textView;

    }

    private void checkIfParamsIsInitialized() {
        if(params != null) params = null;
    }
}
