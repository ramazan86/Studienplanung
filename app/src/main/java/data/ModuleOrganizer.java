package data;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import file.MyFile;
import interfaces.ModuleAdministrator;

/**
 * Created by Ramazan Cinardere on 26.08.15.
 */
public class ModuleOrganizer implements ModuleAdministrator {


    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ModuleManual moduleManual = null;

    private Calendar calendar = null;

    private MyFile myFile = null;

    private Context context = null;

    private ArrayList<Float> notesList = null;

    private ArrayList<Module> modules = null;

    private ArrayList<String> randNote = null;

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

        if(calendar == null) calendar = Calendar.getInstance();
        Date now = new Date(calendar.getTimeInMillis());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String dayName = String.format("%tA", now);
        String date = dayName + ", den " +dateFormat.format(calendar.getTime()).split(" ")[0] + " um " +dateFormat.format(calendar.getTime()).split(" ")[1];

        module.setUnsubscribed(false);
        module.setEnrolled(true);
        module.setEnrolledDate(date);
        module.setNumberOfTrials(module.getNumberOfTrials()+1);

        moduleManual = moduleManual.replaceModuleInList(module);

        if(myFile == null) myFile = new MyFile(context);
            myFile.createFileAndWriteObject(context.getResources().getString(R.string.moduleManualSer), moduleManual);

        return true;
    }

    @Override
    public boolean unSubscribeModule(Module module) {

      //get current time
        if(calendar == null) calendar = Calendar.getInstance();
        Date now = new Date(calendar.getTimeInMillis());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String dayName = String.format("%tA", now);
        String date = dayName + ", den " +dateFormat.format(calendar.getTime()).split(" ")[0] + " um " +dateFormat.format(calendar.getTime()).split(" ")[1];
      //--------------------


      //update module
        module.setUnSubScribedDate(date);
        module.setUnsubscribed(true);
        module.setEnrolled(false);
        module.setNumberOfTrials(module.getNumberOfTrials()-1);

      //write into file
        moduleManual = moduleManual.replaceModuleInList(module);
        if(myFile == null) myFile = new MyFile(context);
            myFile.createFileAndWriteObject(context.getResources().getString(R.string.moduleManualSer), moduleManual);

        return false;
    }

    @Override
    public boolean updateModuleContent(Module module) {

        try {

            if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);

            moduleManual = moduleManual.replaceModuleInList(module);

            if(myFile == null) myFile = new MyFile(context);

            myFile.createFileAndWriteObject(context.getResources().getString(R.string.moduleManualSer), moduleManual);

            Toast.makeText(context, "Update von Modul: " +module.getTitle() + " erfolgreich!!",Toast.LENGTH_SHORT).show();

            return true;
        }catch (Exception e) {
            Log.e("updateModuleContent." +getClass().getName()," " +e.getMessage() + " " +e.getCause());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Module> getEnrolledModules() {

        ArrayList<Module> modules = new ArrayList<>();

        if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);

        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            if(moduleManual.getModuleList().get(i).isEnrolled()) {

                Module tmp = moduleManual.getModuleList().get(i);

                try {
                    //If the exam day is in the future compare to current day
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

        if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);

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

    @Override
    public ArrayList<Module> getPassedExams() {

        ArrayList<Module> modules = new ArrayList<>();

        if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);

        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            if(moduleManual.getModuleList().get(i).isPassed()) {
                modules.add(moduleManual.getModuleList().get(i));
            }
        }
        return modules;
    }

    @Override
    public ArrayList<Module> getNotPassedExams() {
        ArrayList<Module> modules = new ArrayList<>();

        if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);

        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            if(moduleManual.getModuleList().get(i).isNotPassed()) {
                modules.add(moduleManual.getModuleList().get(i));
            }
        }
        return modules;
    }

    @Override
    public ArrayList<Module> getProjects() {

        ArrayList<Module> modules = new ArrayList<>();

        if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);

        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            if(moduleManual.getModuleList().get(i).getExamType().equals(context.getResources().getStringArray(R.array.exam_types)[3])) {
                modules.add(moduleManual.getModuleList().get(i));
            }
        }
        return modules;
    }

    @Override
    public Bundle desiredNoteAverage(float desire) {

        ArrayList<Float> avList   = new ArrayList<>();
        ArrayList<Float> cpList   = new ArrayList<>();
        float creditPoints = 0.0f;
        float cpTimesNote  = 0.0f;


        if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);

        /*for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            int random = new Random().nextInt((4-1) +1) +1;
            if((i%3)==0) {
                moduleManual.getModuleList().get(i).setGrade(String.valueOf(random));
            }
        }*/

        //modules are removed which already has a grade and cannot regard
        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {
            Module tmp = moduleManual.getModuleList().get(i);
            try {
                avList.add(Float.parseFloat(tmp.getCreditPoints()) * Float.parseFloat(tmp.getGrade()));
                cpList.add(Float.valueOf(tmp.getCreditPoints()));
                creditPoints += Float.parseFloat(tmp.getCreditPoints());
                cpTimesNote  += Float.parseFloat(tmp.getCreditPoints()) * Float.parseFloat(tmp.getGrade());
                moduleManual.getModuleList().remove(i);
            }catch (Exception e) {}
        }


        Log.e("CP's : " +creditPoints," avgNotes: " +cpTimesNote/creditPoints);
        Log.e("  --  ","  --  ");

        /*Bundle b = calculateAvgNotes(cpTimesNote, creditPoints, desire);

        creditPoints = b.getFloat("cp");
        cpTimesNote = b.getFloat("cpTimesNote");*/

        Bundle args = null;

        for(int i = 0; i<13;i++) {
            args = calculateAvgNotes(cpTimesNote, creditPoints, desire);
                if(args.getBoolean("break")) {
                    break;
                }
        }


        for(int i = 0; i<((ArrayList<Module>) args.getSerializable("modules")).size(); i++) {

            Module tmp = ((ArrayList<Module>)args.getSerializable("modules")).get(i);
            String note = ((ArrayList<String>)args.getStringArrayList("randNotes")).get(i);


            Log.e("i: " +i ," "  +tmp.getTitle() + " ==> " +note);
        }

        cpTimesNote = args.getFloat("cpTimesNote");
        creditPoints = args.getFloat("cp");


        Log.e("  --  ","  --  ");
        Log.e("desire: " +desire + " CP's " +creditPoints, "  avg: " +cpTimesNote/creditPoints);



        if(moduleManual != null) {
            moduleManual = null;
        }
        return args;
    }

    private Bundle calculateAvgNotes(float cpTimesNote, float creditPoints, float desire) {

        Float[] floats =  new Float[] {1.0f, 1.3f, 1.5f, 1.7f,
                                        2.0f, 2.3f, 2.5f, 2.7f,
                                            3.0f, 3.3f, 3.5f, 3.7f,
                                                4.0f};

        Bundle data = new Bundle();

        if(notesList == null){
            notesList = new ArrayList<>(Arrays.asList(floats));
        }

        if(modules == null) {
            modules = new ArrayList<>();
        }

        if(randNote == null) {
            randNote = new ArrayList<>();
        }


        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            Module tmp = moduleManual.getModuleList().get(i);
            int max = notesList.size()-1;
            int min = 0;
            int random = new Random().nextInt((max - min) +1) + min;

            if(!tmp.getTitle().contains(context.getResources().getString(R.string.M28))) {

                cpTimesNote += Float.parseFloat(tmp.getCreditPoints()) * notesList.get(random);
                creditPoints += Float.parseFloat(tmp.getCreditPoints());

            }

            if(cpTimesNote/creditPoints <= desire) {
                Log.e("desire: "+desire," calculate: " +(cpTimesNote/creditPoints));

                data.putBoolean("break",true);
                break;
            }


            modules.add(tmp);
            randNote.add(String.valueOf(notesList.get(random)));

            //if the last module is achieved
            if(i == moduleManual.getModuleList().size()-1) {

                notesList.remove(random);
                moduleManual = moduleManual.replaceModuleInList(tmp);
            }
        }

            data.putSerializable("modules",modules);
            data.putStringArrayList("randNotes",randNote);
            data.putFloat("cpTimesNote", cpTimesNote);
            data.putFloat("cp",creditPoints);

        return data;
    }

    @Override
    public Bundle getAverageNotes() {

        Bundle args = new Bundle();

        if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);

        float grades = 0.0f;
        float bachelorGrade = 0;
        float creditPoints = 0;
        ArrayList<Module> list = new ArrayList<>();

        if(list.size() != 0) {
            list.clear();
        }


        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            Module tmp = moduleManual.getModuleList().get(i);

                try {

                    if(!tmp.getTitle().contains(context.getResources().getString(R.string.M28))) {
                        grades += Float.parseFloat(tmp.getCreditPoints()) * Float.parseFloat(tmp.getGrade());
                        creditPoints += Float.parseFloat(tmp.getCreditPoints());
                        list.add(tmp);
                        Log.e("getAverageNotes() ===> "," " +tmp.getTitle() + " " +tmp.getGrade());
                    }else {
                        bachelorGrade = Float.parseFloat(tmp.getGrade());
                    }
                }catch (Exception e) {
                    //Log.e("getAverageNotes()" +getClass().getName(), e.getCause() + " // " +e.getMessage());
                    //e.printStackTrace();
                    Log.e("EXCEPTION: "," " +tmp.getTitle() + "  " +tmp.getGrade());
                }
        }

        args.putSerializable("modules", list);

        if(bachelorGrade != 0) {
            args.putFloat("avg",(float) (Math.round((grades * 0.8f) + (bachelorGrade * 0.2f)*100.0)/100.0));
        }else {
            args.putFloat("avg",(float) (Math.round(grades/creditPoints *100.0)/100.0));
        }

        moduleManual = null;
        return args;
    }

    @Override
    public int getCreditPoints() {

        if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);
        int creditPoints = 0;

        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            Module tmp = moduleManual.getModuleList().get(i);

            if(tmp.isPassed()) {
                creditPoints += Integer.parseInt(tmp.getCreditPoints());
            }
        }

        return creditPoints;
    }








}
