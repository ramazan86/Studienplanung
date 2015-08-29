package data;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dialog.MyAlertDialog;
import file.MyFile;
import helper.MyHelper;
import interfaces.ModuleAdministrator;

/**
 * Created by Ramazan Cinardere on 26.08.15.
 */
public class ModuleOrganizer implements ModuleAdministrator {


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
        myFile = new MyFile(context);
        moduleManual = (ModuleManual) myFile.getObjectFromFile(context.getResources().getString(R.string.moduleManualSer));

    }

    @Override
    public boolean subScribeModule(Module module) {

        Log.e("subScribeModule." +getClass().getName()," module: " +module.getTitle());

        module.setUnsubscribed(false);
        module.setEnrolled(true);

        moduleManual = moduleManual.replaceModuleInList(module);

        if(myFile == null) myFile = new MyFile(context);
            myFile.createFileAndWriteObject(context.getResources().getString(R.string.moduleManualSer), moduleManual);

        return true;
    }

    @Override
    public boolean unSubscribeModule(Module module) {

        module.setUnsubscribed(true);
        module.setEnrolled(false);

        moduleManual = moduleManual.replaceModuleInList(module);

        if(myFile == null) myFile = new MyFile(context);
            myFile.createFileAndWriteObject(context.getResources().getString(R.string.moduleManualSer), moduleManual);

        return false;
    }

    @Override
    public ArrayList<Module> getEnrolledModules() {

        ArrayList<Module> modules = new ArrayList<>();

        if(myFile == null) myFile = new MyFile(context);
        if(moduleManual == null) moduleManual = (ModuleManual) myFile.getObjectFromFile(context.getResources().getString(R.string.moduleManualSer));

        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            if(moduleManual.getModuleList().get(i).isEnrolled()) {

                Module tmp = moduleManual.getModuleList().get(i);

                try {

                    if(new SimpleDateFormat("dd/MM/yyyy").parse(tmp.getDateOfExam()).after(new Date())) {
                        modules.add(tmp);
                    }else {
                        tmp.setEnrolled(false);
                        moduleManual = moduleManual.replaceModuleInList(tmp);
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }

            }//if(module is enrolled
        }//for

        myFile.createFileAndWriteObject(context.getString(R.string.moduleManualSer), moduleManual);

        return modules;
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

    @Override
    public ArrayList<Module> getCompletedExams() {

        ArrayList<Module> modules = new ArrayList<>();
        calendar = Calendar.getInstance();

        int currentYear     = calendar.get(Calendar.YEAR);
        int currentMonth    = calendar.get(Calendar.MONTH) + 1;
        int currentDay      = calendar.get(Calendar.DAY_OF_MONTH);

        int currentHours    = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinutes  = calendar.get(Calendar.MINUTE);



        if(myFile == null) myFile = new MyFile(context);
        if(moduleManual == null) moduleManual = (ModuleManual) myFile.getObjectFromFile(context.getResources().getString(R.string.moduleManualSer));


        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            Module tmp = moduleManual.getModuleList().get(i);

            try {


                if(new SimpleDateFormat("dd/MM/yyyy").parse(tmp.getDateOfExam()).before(new Date()) && !tmp.isUnsubscribed())  {
                    modules.add(tmp);
                }else {
                    tmp.setEnrolled(false);
                    moduleManual = moduleManual.replaceModuleInList(tmp);
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }


        return modules;
    }

}
