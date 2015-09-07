package fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import data.Module;
import data.ModuleManual;
import data.ModuleOrganizer;
import dialog.MyAlertDialog;
import helper.MyHelper;


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
                     textView_exams     = null,
                     textView_inputAvg  = null,
                     textView_calcAvg   = null;

    private ImageButton imageButton_avgInfo       = null,
                        imageButton_calcPrognosis = null,
                        imageButton_cp            = null;

    private LinearLayout.LayoutParams params  = null;

    private Bundle bundle_averageNotes     = null,
                   bundle_calculateDesire  = null,
                   bundle_creditPoints     = null;

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.overview_graduation, container, false);

        initComponents(rootView);

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
        bundle_averageNotes = moduleOrganizer.getAverageNotes();
        textView_average.setText(" "+bundle_averageNotes.getFloat("avg"));


      //creditpoints
        bundle_creditPoints = moduleOrganizer.getCreditPoints();
        textView_cp.setText(bundle_creditPoints.getInt("cps") + "/" +moduleManual.getTotalCreditPoints());

        return rootView;
    }

    private void initComponents(View rootView) {

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
        imageButton_cp = (ImageButton) rootView.findViewById(R.id.overViewGraduation_imageButton_cp);

        //add to listener
        imageButton_avgInfo.setOnClickListener(this);
        imageButton_calcPrognosis.setOnClickListener(this);
        imageButton_cp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        ArrayList<Module>list = (ArrayList<Module>) bundle_averageNotes.getSerializable("modules");
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View stateView = factory.inflate(R.layout.dialog_note_avg, null);

        LinearLayout linearLayout = (LinearLayout) stateView.findViewById(R.id.dialogNoteAvg_linearLayout_inner);
        TextView headLine = (TextView) stateView.findViewById(R.id.dialogNoteAvg_textView_headline);

        textView_inputAvg = (TextView) stateView.findViewById(R.id.dialogNoteAvg_textView_inputAvg);
        textView_calcAvg  = (TextView) stateView.findViewById(R.id.dialogNoteAvg_textView_calculatedAvg);


        if(v.getId() == R.id.overViewGraduation_imageButton_avg) {

            textView_inputAvg.setVisibility(View.INVISIBLE);
            textView_calcAvg.setVisibility(View.INVISIBLE);


            if(bundle_averageNotes != null) {

                headLine.setText(getActivity().getString(R.string.notesTilYet));

                //
                for(int i = 0; i<list.size(); i++) {

                    TextView tv = showTitlesAndNotes(list.get(i).getTitle(), list.get(i).getGrade(), MyHelper.CHECK_VALUE_AVG);
                    linearLayout.addView(tv);
                }
                // ---------------

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
        }//
        else if(v.getId() == R.id.overViewGraduation_imageButton_calc) {


            if(moduleOrganizer.getAverageNotes().getFloat("avg") != 0) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater factory_ = LayoutInflater.from(getActivity());
                final View stateView_ = factory_.inflate(R.layout.dialog_input, null);

                textView_inputAvg.setVisibility(View.INVISIBLE);
                textView_calcAvg.setVisibility(View.INVISIBLE);

                final EditText input = (EditText) stateView_.findViewById(R.id.dialogInput_note);

                builder.setView(stateView_);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        float desire = 0, currentAvg = 0;

                        try {
                            desire =  Float.parseFloat(input.getText().toString());
                            currentAvg = moduleOrganizer.getAverageNotes().getFloat("avg");
                        }catch (Exception e) {
                            Log.e(" ##### EXCEPTION: "," " +e.getMessage() + " " +e.getCause());

                        }

                        //note == input edittext;
                        if(desire != 0.0f && currentAvg != 0.0f) {

                            //calculation of desire avg is only given, if desireNote < currentAvg
                            if(desire >= currentAvg) {
                                showDialogInvalidInput(desire, currentAvg);
                            }else {

                                //calculated students desire avg note
                                bundle_calculateDesire = moduleOrganizer.desiredNoteAverage(desire, 0);

                                //if calculated note < desired note
                                //sortArray[0] == best avgNote which is calculated
                                if(desire < bundle_calculateDesire.getFloatArray("sortArray")[0]) {


                                    showCalculatedAvgIsBiggerInput(desire);
                                }else {
                                    showCalculatedList(bundle_calculateDesire);
                                }

                            }
                        }
                    }
                });

                builder.create().show();

            }else {
                Toast.makeText(getActivity(), "Keine Noten vorhanden ...", Toast.LENGTH_SHORT).show();
            }



        }

        else if(v.getId() == R.id.overViewGraduation_imageButton_cp) {

            headLine.setText(getActivity().getString(R.string.cpPlural));
            ArrayList<Module> modules = (ArrayList<Module>) bundle_creditPoints.getSerializable("modules");

            textView_inputAvg.setVisibility(View.INVISIBLE);
            textView_calcAvg.setVisibility(View.INVISIBLE);

            //
            for(int i = 0; i<modules.size(); i++) {

                TextView tv = showTitlesAndNotes(modules.get(i).getTitle(), modules.get(i).getCreditPoints(), MyHelper.CHECK_VALUE_CP);
                linearLayout.addView(tv);
            }
            // ---------------

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


    }

    private void showCalculatedAvgIsBiggerInput(float desire) {

        if(moduleOrganizer == null) {
            moduleOrganizer = new ModuleOrganizer(getActivity());
        }
        Bundle args = moduleOrganizer.calcAvgWithFixNote(1.0f);


        ArrayList<Module> list = (ArrayList<Module>) args.getSerializable("modules");

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View stateView = factory.inflate(R.layout.dialog_note_avg, null);

        //initComponents
        TextView textView_inputAvg = (TextView) stateView.findViewById(R.id.dialogNoteAvg_textView_inputAvg);
        TextView textView_calcAvg  = (TextView) stateView.findViewById(R.id.dialogNoteAvg_textView_calculatedAvg);

        //set values

        // textView inputValue
        String input = String.valueOf(desire);

        Spannable spanInput = new SpannableString(input);
        spanInput.setSpan(new ForegroundColorSpan(Color.BLUE), 0, spanInput.length(),0);
        textView_inputAvg.setText(textView_inputAvg.getText() + " " +"(" +spanInput + ")");
        // #############################


        //textView calculatedValue
        String calcAvg = String.valueOf(args.getFloat("avg")).substring(0,4);

        Spannable spanCalc = new SpannableString(calcAvg);
        spanCalc.setSpan(new ForegroundColorSpan(Color.BLUE), 0, spanCalc.length(),0);

        textView_calcAvg.setText(textView_calcAvg.getText() + " (" +spanCalc + ")");
        // ##########################



        LinearLayout linearLayout = (LinearLayout) stateView.findViewById(R.id.dialogNoteAvg_linearLayout_inner);

        //set headline
        TextView headLine = (TextView) stateView.findViewById(R.id.dialogNoteAvg_textView_headline);
        headLine.setText(getActivity().getString(R.string.prognosis) + " (1.0)");

        //set modules with respective notes into view
        for(int i = 0; i<list.size(); i++) {
            TextView tv = showTitlesAndNotes(list.get(i).getTitle(), String.valueOf(1.0f), "");
            linearLayout.addView(tv);
        }

        //popup alertdialog
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

    private void showCalculatedList(Bundle arguments) {

        ArrayList<Module> list = (ArrayList<Module>) arguments.getSerializable("modules");
        ArrayList<String> markList = ((ArrayList<String>)arguments.getStringArrayList("randNotes"));

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View stateView = factory.inflate(R.layout.dialog_note_avg, null);

        //initComponents
        TextView textView_inputAvg = (TextView) stateView.findViewById(R.id.dialogNoteAvg_textView_inputAvg);
        TextView textView_calcAvg  = (TextView) stateView.findViewById(R.id.dialogNoteAvg_textView_calculatedAvg);

        //set values

      // textView inputValue
        String input = String.valueOf(arguments.getFloat("desire"));

        Spannable spanInput = new SpannableString(input);
            spanInput.setSpan(new ForegroundColorSpan(Color.BLUE), 0, spanInput.length(),0);
        textView_inputAvg.setText(textView_inputAvg.getText() + " " +"(" +spanInput + ")");
      // #############################


      //textView calculatedValue
        String calcAvg = String.valueOf(arguments.getFloat("calcAvg")).substring(0,4);

        Spannable spanCalc = new SpannableString(calcAvg);
            spanCalc.setSpan(new ForegroundColorSpan(Color.BLUE), 0, spanCalc.length(),0);

        textView_calcAvg.setText(textView_calcAvg.getText() + " (" +spanCalc + ")");
      // ##########################



        LinearLayout linearLayout = (LinearLayout) stateView.findViewById(R.id.dialogNoteAvg_linearLayout_inner);

        //set headline
        TextView headLine = (TextView) stateView.findViewById(R.id.dialogNoteAvg_textView_headline);
        headLine.setText(getActivity().getString(R.string.prognosis));

        //set modules with respective notes into view
        for(int i = 0; i<list.size(); i++) {
            TextView tv = showTitlesAndNotes(list.get(i).getTitle(), markList.get(i), "");
            linearLayout.addView(tv);
        }

        //popup alertdialog
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

    private void showDialogInvalidInput(float note, float currentAvg) {

        MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
        myAlertDialog.setIcon(getActivity().getResources().getDrawable(R.drawable.warning_24x24_red));
        myAlertDialog.setTitle(getActivity().getResources().getString(R.string.invalidInput));

        String messageFirstLine = getActivity().getResources().getString(R.string.noteSmallCurrent) + System.getProperty("line.separator");
        String messageSecondLine = "" + note + " > " + currentAvg;
        String message = messageFirstLine + " " +messageSecondLine;

        Spannable spannable = new SpannableString(message);

        spannable.setSpan(new ForegroundColorSpan(Color.RED), messageFirstLine.length()+1, message.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), messageFirstLine.length()+1, message.length(),0);

        myAlertDialog.setMessage(spannable);
        myAlertDialog.buildDialogWithNeutralButton("OK","-1");

    }

    private TextView showTitlesAndNotes(String title, String grade, String checkVal) {


        TextView textView_title = new TextView(getActivity());
        textView_title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        params = (LinearLayout.LayoutParams) textView_title.getLayoutParams();
        int marginRight = Math.round(getResources().getDimension(R.dimen.dimen_1));
        int textSize = Math.round(getResources().getDimension(R.dimen.dimen_5));
        params.setMargins(0, 10, marginRight, 0);

        textView_title.setTextColor(getResources().getColor(R.color.black));

        String s = title + " (" +grade + ")";

        Spannable spannable = new SpannableString(s);
        int color = getActivity().getResources().getColor(R.color.green);;


        if(!checkVal.equals(MyHelper.CHECK_VALUE_CP)) {
            //change color of grade
            if(grade.equals("5") || grade.equals("5.0")) {
                color = Color.RED;
            }
        }


            spannable.setSpan(new ForegroundColorSpan(color), title.length()+2, s.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new StyleSpan(Typeface.BOLD), title.length()+2, s.length()-1,0);

        textView_title.setText(spannable);
        textView_title.setTextSize(16);
        textView_title.setLayoutParams(params);

        return textView_title;
    }



}
