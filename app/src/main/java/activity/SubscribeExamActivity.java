package activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import data.Module;
import data.ModuleManual;
import dialog.MyAlertDialog;
import file.MyFile;
import helper.MyHelper;

/**
 * Created by Ramazan Cinardere on 26.08.15.
 */
public class SubscribeExamActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener, View.OnTouchListener, TimePickerDialog.OnTimeSetListener {


    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ModuleManual moduleManual = null;
    private Module module             = null;

    private Context context = null;
    private int layoutId;


    private Spinner spinner_module   = null,
            spinner_type     = null,
            spinner_date     = null,
            spinner_time     = null,
            spinner_semester = null;

    private ArrayAdapter<String> arrayAdapter_module   = null,
            arrayAdapter_type     = null,
            arrayAdapter_date     = null,
            arrayAdapter_time     = null,
            arrayAdapter_semester = null;

    private Calendar calendar = null;

    private int currentYear, newYear,
            currentMonth, newMonth,
            currentDay, newDay,
            currentHours, newHours,
            currentMinutes, newMinutes;

    private MyFile myFile = null;

    private ArrayList<String> semesters_list = null,
            modules_list   = null;

    private static int numberOfItemSelected = 0;
    private ActionBar actionBar    = null;
    private TextView textView_save = null;

    private String semester     = "",
            moduleTitle  = "",
            examType     = "",
            notice       = "",
            date         = "",
            time         = "";

    private final int DATE_PICKER_ID = 1111,
            TIME_PICKER_ID = 2222;

    private String[] currentDate = null;
    private String[] currentTime = null;

    private MyAlertDialog myAlertDialog = null;

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutId = getIntent().getExtras().getInt(getResources().getString(R.string.layoutId));

        setContentView(layoutId);
        createCustomActionBar();
        initComponents();
    }

    private void createCustomActionBar() {

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.radial_background));

        LayoutInflater myInlater = LayoutInflater.from(this);
        View customView = myInlater.inflate(R.layout.my_actionbar,null);

        textView_save = (TextView) customView.findViewById(R.id.myActionbar_textView_add);
        textView_save.setText(getResources().getString(R.string.save));
        textView_save.setAllCaps(false);
        textView_save.setOnClickListener(this);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    private void initComponents() {

        //ModuleManual
        moduleManual = (ModuleManual) new MyFile(getApplicationContext()).getObjectFromFile(getResources().getString(R.string.moduleManualSer));

        //Calendar
        calendar = Calendar.getInstance();
        currentYear     = calendar.get(Calendar.YEAR);
        currentMonth    = calendar.get(Calendar.MONTH) + 1;
        currentDay      = calendar.get(Calendar.DAY_OF_MONTH);
        currentHours    = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinutes  = calendar.get(Calendar.MINUTE);

        //Spinner
        spinner_date     = (Spinner) findViewById(R.id.addExam_spinner_date);
        spinner_module   = (Spinner) findViewById(R.id.addExam_spinner_module);
        spinner_time     = (Spinner) findViewById(R.id.addExam_spinner_time);
        spinner_type     = (Spinner) findViewById(R.id.addExam_spinner_type);
        spinner_semester = (Spinner) findViewById(R.id.addExam_spinner_semester);

        //create array/list for adapter
        currentDate = new String[]{String.valueOf(new StringBuilder().append(currentDay).append("/").append(currentMonth).append("/").append(currentYear))};
        currentTime = new String[]{String.format("%tR", new Date())};

        //ArrayAdapter
        arrayAdapter_date     = new ArrayAdapter<String>(this, R.layout.spinner_item,currentDate);
        arrayAdapter_type     = new ArrayAdapter<String>(this, R.layout.spinner_item, getResources().getStringArray(R.array.exam_types));
        arrayAdapter_time     = new ArrayAdapter<String>(this, R.layout.spinner_item, currentTime);

        ArrayList<String> semesters = new ArrayList<>();
        semesters.add(getResources().getString(R.string.chooseSemester));
        semesters.addAll(ModuleManual.getSemesters(getApplicationContext()));
        arrayAdapter_semester = new ArrayAdapter<String>(this, R.layout.spinner_item, semesters);


        //add adapter
        spinner_date.setAdapter(arrayAdapter_date);
        spinner_time.setAdapter(arrayAdapter_time);
        spinner_type.setAdapter(arrayAdapter_type);
        spinner_semester.setAdapter(arrayAdapter_semester);

        //listener
        spinner_semester.setOnItemSelectedListener(this);
        spinner_date.setOnTouchListener(this);

        spinner_time.setOnItemSelectedListener(this);
        spinner_time.setOnTouchListener(this);

        spinner_type.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {

        if(position != 0) {

            switch (parent.getId()) {

                case R.id.addExam_spinner_semester:

                    modules_list = new ArrayList<>();
                    modules_list.add(getResources().getString(R.string.chooseModule));
                    modules_list.addAll(moduleManual.getModules(parent.getItemAtPosition(position).toString()));

                    arrayAdapter_module = new ArrayAdapter<String>(this, R.layout.spinner_item, modules_list);

                    spinner_module.setAdapter(arrayAdapter_module);
                    spinner_module.setOnItemSelectedListener(this);

                    semester = parent.getItemAtPosition(position).toString();

                    break;

                case R.id.addExam_spinner_module:
                    moduleTitle = parent.getItemAtPosition(position).toString();

                    if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);
                    Module tmp = moduleManual.searchModule(moduleTitle);

                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addExam_linearLayout_wrapSpinnerModule);

                    if(tmp.isEnrolled()) {
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.red));
                    }else {
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.light_green));
                    }
                    break;

                case R.id.addExam_spinner_type:
                    examType = parent.getItemAtPosition(position).toString();
                    break;
            }
        }

        //counts how often an item in a spinner was selected
        numberOfItemSelected++;
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, this, currentYear, currentMonth, currentDay);

            case TIME_PICKER_ID:
                return new TimePickerDialog(this, this, currentHours, currentMinutes, false);
        }

        return null;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        //Speichern
        if(v.getId() == R.id.myActionbar_textView_add) {

            if(!semester.equals("") && !moduleTitle.equals("")) {

                //
                if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);
                Module mod = moduleManual.searchModule(moduleTitle);

                //Check if module is already enrolled
                if(mod.isEnrolled()) {

                    MyAlertDialog dialog = new MyAlertDialog(this);
                        dialog.setIcon(getResources().getDrawable(R.drawable.warning_24x24_red));
                        dialog.setTitle(getResources().getString(R.string.enrollExam));
                        dialog.setMessage(getResources().getString(R.string.warningMultipleEnroll));
                        dialog.buildDialogWithNeutralButton("OK", "-1");
                }
                else {
                    String[] date = arrayAdapter_date.getItem(0).toString().split("/");

                    try {
                        if(new SimpleDateFormat("dd/MM/yyyy").parse(arrayAdapter_date.getItem(0).toString()).after(new Date())) {

                            Module tmp = moduleManual.searchModule(moduleTitle);
                            tmp.setDateOfExam(arrayAdapter_date.getItem(0).toString());
                            tmp.setTimeOfExam(arrayAdapter_time.getItem(0).toString());

                            if(!examType.equals(getResources().getStringArray(R.array.exam_types)[0])) {
                                tmp.setExamType(examType);
                            }


                            Bundle data = new Bundle();
                            data.putSerializable(getResources().getString(R.string.module), tmp);
                            data.putSerializable(getResources().getString(R.string.moduleManual), moduleManual);


                            if(tmp.getNumberOfTrials() == 3) {
                                alertDialogNotAllowed(getString(R.string.enrolleExamNotAllowed), getString(R.string.examThirdEnroll), "-1");
                            }else {
                                openDialogEnrolleModule(data);
                            }

                            //myAlertDialog listener calls enrollForModule if user clicks "JA", otherwise nothing

                        }else {
                            alertDialogNotAllowed(getString(R.string.invalidDate),getString(R.string.dateWarning), MyHelper.CHECK_VALUE_ENROLL_WARNING_DATE);
                        }
                    } catch (ParseException e) {
                        Log.e("Exception in onClick." +getClass().getName()," " +e.getCause() + " // " +e.getMessage());
                        e.printStackTrace();
                    }
                }



            }//if((!semester.equals("") && !moduleTitle.equals(""))
            else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.warningEnrollExam), Toast.LENGTH_LONG).show();

            }//else
        }
    }

    private void alertDialogNotAllowed(String title, String message, String checkValue) {

        if(myAlertDialog != null) {
            myAlertDialog = null;
        }
        myAlertDialog = new MyAlertDialog(this);
        myAlertDialog.setIcon(getResources().getDrawable(R.drawable.warning_24x24_red));
        myAlertDialog.setTitle(title);
        myAlertDialog.setMessage(message);
        myAlertDialog.buildDialogWithNeutralButton("OK", checkValue);

    }

    private void openDialogEnrolleModule(Bundle data) {

        myAlertDialog = new MyAlertDialog(this);
        myAlertDialog.setBundle(data);

        myAlertDialog.setTitle(getResources().getString(R.string.enrollExam));
        String alertMessage = getResources().getString(R.string.questionForEnrollExamPraefix) +":" +System.getProperty("line.separator") + ">> " +moduleTitle + " <<" +System.getProperty("line.separator")  + getResources().getString(R.string.questionForEnrollExamSuffix);

        myAlertDialog.setMessage(alertMessage);
        myAlertDialog.buildDialogWithPositiveAndNegativeButton("Ja","Nein", MyHelper.CHECK_VALUE_ENROLL_EXAM);



    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        newYear  = year;
        newMonth = monthOfYear+1;
        newDay   = dayOfMonth;

        String date = newDay + getResources().getString(R.string.backSlash) + newMonth + getResources().getString(R.string.backSlash) + newYear;

        currentDate[0] = date;
        arrayAdapter_date.notifyDataSetChanged();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(v.getId() == R.id.addExam_spinner_date) {
            showDialog(DATE_PICKER_ID);
        }
        else if(v.getId() == R.id.addExam_spinner_time) {
            showDialog(TIME_PICKER_ID);
        }

        else if(v.getId() == R.id.addExam_spinner_semester) {
            Log.e("onTouch() "," " +v.getContentDescription().toString());
        }



        return false;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        newHours   = hourOfDay;
        newMinutes = minute;

        currentTime[0] = String.format("%d:%02d",newHours,newMinutes);
        arrayAdapter_time.notifyDataSetChanged();

    }

    public String getModuleTitle() {
        return this.moduleTitle;
    }
}
