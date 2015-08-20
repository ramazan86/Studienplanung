package view;

import android.app.Notification;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import org.w3c.dom.Text;

import data.ModuleManual;

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

        initComponents();

        moduleManual = (ModuleManual) getIntent().getExtras().getSerializable("moduleManual");

        assignValuesToComponents();
    }



    private void initComponents() {
        //textView headline
        textView_headline = (TextView) findViewById(R.id.module_totalView_textViewHeadline);

        //linearLayout
        linearLayout_modules = (LinearLayout) findViewById(R.id.module_totalView_linearLayout_modules);
    }

    private void assignValuesToComponents() {

        //textview headline
        if(textView_headline != null) {
            textView_headline.setText(getResources().getString(R.string.totalView));
            checkIfParamsIsInitialized();

            params = (LinearLayout.LayoutParams) textView_headline.getLayoutParams();

            textView_headline.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            textView_headline.setTextSize(getResources().getDimension(R.dimen.dimen_2));
            textView_headline.setBackgroundColor(getResources().getColor(R.color.gray));
        }

            linearLayout_row = new LinearLayout(this);
            //LayoutParams : 1st Param: width, 2nd Param: height
            linearLayout_row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout_row.setBackgroundColor(getResources().getColor(R.color.red));


            textView_value = new TextView(this);
                textView_value.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView_value.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                checkIfParamsIsInitialized();

                params = (LinearLayout.LayoutParams) textView_value.getLayoutParams();
                int marginRight = Math.round(getResources().getDimension(R.dimen.dimen_1));
                int textSize = Math.round(getResources().getDimension(R.dimen.dimen_5));
                params.setMargins(0,0,marginRight,0);

                textView_value.setText("heerreee");
                textView_value.setTextSize(textSize);
                textView_value.setLayoutParams(params);
                textView_value.setTextColor(getResources().getColor(R.color.black));

            linearLayout_row.addView(textView_value);
            linearLayout_modules.addView(linearLayout_row);

    }

    private void checkIfParamsIsInitialized() {
        if(params != null) params = null;
    }
}
