package fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.util.ArrayList;

import data.Module;
import data.ModuleManual;
import data.ModuleOrganizer;


/**
 * Created by Ramazan Cinardere on 29.08.15.
 */
public class OverView extends Fragment implements View.OnClickListener {

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ModuleOrganizer moduleOrganizer = null;

    private ModuleManual moduleManual = null;

    private ArrayList<Module> modules = null;

    private String[] values = null;

    private TextView textView_passed    = null,
                     textView_notPassed = null,
                     textView_average   = null,
                     textView_cp        = null,
                     textView_exams     = null;

    private ImageButton imageButton_avgInfo       = null,
                        imageButton_calcPrognosis = null;

    private LinearLayout.LayoutParams params  = null;

    private Bundle arguments = null;


    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.overview_graduation, container, false);

        initAttributes(rootView);

      //Passed
        String passed = "";

        if(moduleOrganizer.getPassedExams().size() == 0) {
            passed = textView_passed.getText() + " " + getActivity().getString(R.string.notANumber);
        }else {
            passed = textView_passed.getText() + " " +moduleOrganizer.getPassedExams().size();
        }
        textView_passed.setText(passed);
      // ---------


      //Notpassed
        String notPassed = "";

        if(moduleOrganizer.getNotPassedExams().size() == 0) {
            notPassed = textView_notPassed.getText() + " " + getActivity().getString(R.string.notANumber);
        }else {
            notPassed = textView_notPassed.getText() + " " +moduleOrganizer.getNotPassedExams().size();
        }
        textView_notPassed.setText(notPassed);
      // ---------


      //average notes
        arguments = moduleOrganizer.getAverageNotes();
        textView_average.setText(" "+arguments.getFloat("avg"));



      //creditpoints
        textView_cp.setText(textView_cp.getText() + " " +moduleOrganizer.getCreditPoints() + "/" +moduleManual.getTotalCreditPoints());

        return rootView;
    }

    private void initAttributes(View rootView) {

        //from layout
        textView_passed     = (TextView) rootView.findViewById(R.id.overviewGraduation_textView_passed);
        textView_notPassed  = (TextView) rootView.findViewById(R.id.overviewGraduation_textView_notPassed);
        textView_average    = (TextView) rootView.findViewById(R.id.overviewGraduation_textView_noteAverage);
        textView_cp         = (TextView) rootView.findViewById(R.id.overviewGraduation_textView_creditPoints);
        textView_exams      = (TextView) rootView.findViewById(R.id.overviewGraduation_textView_exams);

        //Moduleorganizer
        moduleOrganizer = new ModuleOrganizer(getActivity().getApplicationContext());

        //moduleManual
        moduleManual = ModuleManual.getInstance(getActivity().getApplicationContext());

        //ImageButton
        imageButton_avgInfo = (ImageButton) rootView.findViewById(R.id.overViewGraduation_imageButton_avg);
        imageButton_calcPrognosis = (ImageButton) rootView.findViewById(R.id.overViewGraduation_imageButton_calc);

        //add to listener
        imageButton_avgInfo.setOnClickListener(this);
        imageButton_calcPrognosis.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.overViewGraduation_imageButton_avg) {


            LayoutInflater factory = LayoutInflater.from(getActivity());
            final View stateView = factory.inflate(R.layout.dialog_note_avg, null);


            if(arguments != null) {

                ArrayList<Module>list = (ArrayList<Module>) arguments.getSerializable("modules");
                ArrayList<String> titles = new ArrayList<>();

                LinearLayout linearLayout = (LinearLayout) stateView.findViewById(R.id.dialogNoteAvg_linearLayout_inner);


                for(int i = 0; i<list.size(); i++) {

                    TextView tv = showTitlesAndNotes(list.get(i).getTitle(), list.get(i).getGrade());
                    linearLayout.addView(tv);
                }

            }


            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setView(stateView);

            stateView.findViewById(R.id.dialogNoteAvg_imageButton_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();




        }
        else if(v.getId() == R.id.overViewGraduation_imageButton_calc) {

        }






    }

    private TextView showTitlesAndNotes(String title, String grade) {


        TextView textView_title = new TextView(getActivity());
        textView_title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        params = (LinearLayout.LayoutParams) textView_title.getLayoutParams();
        int marginRight = Math.round(getResources().getDimension(R.dimen.dimen_1));
        int textSize = Math.round(getResources().getDimension(R.dimen.dimen_5));
        params.setMargins(0, 10, marginRight, 0);

        textView_title.setTextColor(getResources().getColor(R.color.black));

        String s = title + " (" +grade + ")";

        Spannable spannable = new SpannableString(s);
        int color;

        if(Integer.parseInt(grade) == 5) {
            color = Color.RED;
        }else {
           color = getActivity().getResources().getColor(R.color.green);
        }

            spannable.setSpan(new ForegroundColorSpan(color), title.length()+2, s.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new StyleSpan(Typeface.BOLD), title.length()+2, s.length()-1,0);

        textView_title.setText(spannable);
        textView_title.setTextSize(16);
        textView_title.setLayoutParams(params);

        return textView_title;
    }
}
