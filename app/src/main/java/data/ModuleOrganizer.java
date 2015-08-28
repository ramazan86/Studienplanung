package data;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dialog.MyAlertDialog;
import file.MyFile;
import helper.MyHelper;

/**
 * Created by Ramazan Cinardere on 26.08.15.
 */
public class ModuleOrganizer implements ModuleAdministrator{


    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ModuleManual moduleManual = null;
    private Module module             = null;

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

    private Context context = null;

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////

    public ModuleOrganizer() {

    }

    public ModuleOrganizer(Context context) {
        this();
        initAttributes(context);
    }

    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    private void initAttributes(Context context) {

        this.context = context;
        //ModuleManual
        moduleManual = (ModuleManual) new MyFile(context).getObjectFromFile(context.getResources().getString(R.string.moduleManualSer));

    }

    @Override
    public boolean subScribeModule(Module module) {

        Log.e("enrolForModule"," " +module.getTitle());

        module.setEnrolled(true);

        moduleManual = moduleManual.replaceModuleInList(module);

        if(myFile == null) myFile = new MyFile(context);
            myFile.createFileAndWriteObject(context.getResources().getString(R.string.moduleManualSer), moduleManual);

        return true;
    }

    @Override
    public boolean unSubscribeFromModule(Module module) {
        return false;
    }

    @Override
    public ArrayList<Module> getEnrolledModules() {

        ArrayList<Module> tmpList = new ArrayList<>();

        if(myFile == null) myFile = new MyFile(context);
        if(moduleManual == null) moduleManual = (ModuleManual) myFile.getObjectFromFile(context.getResources().getString(R.string.moduleManualSer));

        for(int i = 0; i<moduleManual.getModuleList().size(); i++)

            if(moduleManual.getModuleList().get(i).isEnrolled()) {
                tmpList.add(moduleManual.getModuleList().get(i));
            }

        return tmpList;
    }

    @Override
    public ArrayList<Module> getUnSubscribedModules() {
        ArrayList<Module> tmpList = new ArrayList<>();

        if(myFile == null) myFile = new MyFile(context);
        if(moduleManual == null) moduleManual = (ModuleManual) myFile.getObjectFromFile(context.getResources().getString(R.string.moduleManualSer));

        for(int i = 0; i<moduleManual.getModuleList().size(); i++)

            if(moduleManual.getModuleList().get(i).isUnsubscribed()) {
                tmpList.add(moduleManual.getModuleList().get(i));
            }

        return tmpList;
    }

}
