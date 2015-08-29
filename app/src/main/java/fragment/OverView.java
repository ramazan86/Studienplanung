package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.util.ArrayList;

import adapter.MySimpleArrayAdapter;
import data.Module;
import data.ModuleManual;
import data.ModuleOrganizer;

/**
 * Created by Ramazan Cinardere on 29.08.15.
 */
public class OverView extends Fragment {

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
        textView_average.setText(textView_average.getText() + " " +moduleOrganizer.getAverageNotes());

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

    }


}
