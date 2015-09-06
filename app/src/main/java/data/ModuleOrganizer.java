package data;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import email.MyEmail;
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

    private Calendar calendar = null;

    private MyFile myFile = null;

    private Context context = null;

    private ArrayList<Float> notesList = null;

    private ArrayList<Module> modules = null;

    private ArrayList<String> randNoteList = null;

    private final int COUNT_OF_NOTES = 13;

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


        Student stud = (Student) myFile.getObjectFromFile(context.getString(R.string.file_studentSer));


        Log.e(" stud: "," " +stud.getEmail() + " " +stud.isSendEmail());


        if(stud.isSendEmail()) {


        new MyEmail(context).sendEmail(stud.getEmail(), context.getString(R.string.enrolledAt), module);




        }






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

    public Bundle calcAvgWithFixNote(float note) {

        float creditPoints = 0.0f;
        float cpTimesNote  = 0.0f;

        Bundle bundle = new Bundle();

        if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);

        //modules are removed which already has a grade and cannot regard
        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {
            Module tmp = moduleManual.getModuleList().get(i);
            try {
                //if getGrade ist not set, returns null;
                //since we get the modules, which is not set  a note already
                tmp.getGrade();

                creditPoints += Float.parseFloat(tmp.getCreditPoints());
                cpTimesNote  += Float.parseFloat(tmp.getCreditPoints()) * Float.parseFloat(tmp.getGrade());
                moduleManual.getModuleList().remove(i);
            }catch (Exception e) {}
        }

        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            Module tmp = moduleManual.getModuleList().get(i);

            /*Bachelor grade will calculate in a different formula*/
            if(!tmp.getTitle().contains(context.getResources().getString(R.string.M28))) {
                cpTimesNote += Float.parseFloat(tmp.getCreditPoints()) * note;
                creditPoints += Float.parseFloat(tmp.getCreditPoints());
                modules.add(tmp);
            }
        }

            bundle.putSerializable("modules", modules);
            bundle.putFloat("avg", (cpTimesNote/creditPoints));
        return bundle;
    }


    @Override
    public Bundle desiredNoteAverage(float desire, int maxLimit) {

        ArrayList<Float> avList   = new ArrayList<>();
        ArrayList<Float> cpList   = new ArrayList<>();
        float creditPoints = 0.0f;
        float cpTimesNote  = 0.0f;

        if(moduleManual == null) moduleManual = ModuleManual.getInstance(context);

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


        ArrayList<ArrayList<Module>> modulesList = new ArrayList<>();
        ArrayList<ArrayList<String>> notesList = new ArrayList<>();
        ArrayList<Float> cpTimesNotesList = new ArrayList<>();
        ArrayList<Float> cpsList = new ArrayList<>();

        Bundle args = null;

        for(int i = 0; i<COUNT_OF_NOTES; i++) {
            args = calculateAvgNotes(cpTimesNote, creditPoints, desire, maxLimit);

                modulesList.add((ArrayList<Module>) args.getSerializable("modules"));       //calculated desire note

                notesList.add(args.getStringArrayList("randNotes"));

                cpTimesNotesList.add(args.getFloat("cpTimesNote"));
                cpsList.add(args.getFloat("cp"));

                if(args.getBoolean("break")) {
                    break;
                }
        }




        /**
         * if the calculated avg is still higher the current avg
         * then the user will see, the best lowest avg notes
         * which he can reach with random notes
         * */
        // get the lowest avg
        float[] tmp = null;

        for(int i = 0; i<modulesList.size(); i++) {

            if(i == 0) {
                tmp = new float[modulesList.size()];
            }
            float avg = cpTimesNotesList.get(i) / cpsList.get(i);

            tmp[i] = avg;
        }

            Arrays.sort(tmp);               //sort the array of avg notes asc

    // #######################################################################



        //assign values into bundle
            args.putSerializable("nestedModuleList",modulesList);
            args.putSerializable("nestedNoteList", notesList);
            args.putFloatArray("sortArray",tmp);

        if(moduleManual != null) {
            moduleManual = null;
        }
        return args;
    }

    private Bundle calculateAvgNotes(float cpTimesNote, float creditPoints, float desire, int maxLimit) {

        Float[] floats =  new Float[] {1.0f, 1.3f, 1.5f, 1.7f,
                                           2.0f, 2.3f, 2.5f, 2.7f,
                                                3.0f, 3.3f, 3.5f, 3.7f,
                                                    4.0f};

        Bundle data = new Bundle();

        if(notesList == null || notesList.size() != 0){
            notesList = new ArrayList<>(Arrays.asList(floats));
        }

        /*else if(notesList == null || notesList.size() == 0 && maxLimit != 0) {
            notesList = new ArrayList<>(Arrays.asList(y));
        }*/

        if(modules == null) {
            modules = new ArrayList<>();
        }

        if(randNoteList == null) {
            randNoteList = new ArrayList<>();
        }

        if(modules.size() != 0) {
            modules.clear();
        }

        if(randNoteList.size() != 0) {
            randNoteList.clear();
        }



        for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

            Module tmp = moduleManual.getModuleList().get(i);
            int max = notesList.size()-1;
            int min = 0;


            int random = new Random().nextInt((max - min) +1) + min;

            /*Bachelor grade will calculate in a different formula*/
            if(!tmp.getTitle().contains(context.getResources().getString(R.string.M28))) {
                cpTimesNote += Float.parseFloat(tmp.getCreditPoints()) * notesList.get(random);
                creditPoints += Float.parseFloat(tmp.getCreditPoints());

                modules.add(tmp);
                randNoteList.add(String.valueOf(notesList.get(random)));
            }

            if(cpTimesNote/creditPoints <= desire) {
                data.putBoolean("break",true);
                break;
            }

            //if the last module is achieved
            if(i == moduleManual.getModuleList().size()-1) {

                notesList.remove(random);
                moduleManual = moduleManual.replaceModuleInList(tmp);
            }
        }

        //assign values into bundle
            data.putSerializable("modules",modules);
            data.putStringArrayList("randNotes",randNoteList);

            data.putFloat("calcAvg",(cpTimesNote/creditPoints));
            data.putFloat("cpTimesNote", cpTimesNote);
            data.putFloat("cp",creditPoints);
            data.putFloat("desire", desire);

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
                    }else {
                        bachelorGrade = Float.parseFloat(tmp.getGrade());
                    }
                }catch (Exception e) {
                    //Log.e("getAverageNotes()" +getClass().getName(), e.getCause() + " // " +e.getMessage());
                    //e.printStackTrace();
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

    @Override
    public int getStateDrawable(String moduleTitle) {

        String[] states = new String[] { MyHelper.MODULE_PASSED_FIRST_TRY,
                                            MyHelper.MODULE_PASSED_SECOND_TRY,
                                            MyHelper.MODULE_PASSED_THIRD_TRY,
                                            MyHelper.MODULE_NOT_PASSED_FIRST_TRY,
                                            MyHelper.MODULE_NOT_PASSED_SECOND_TRY,
                                            MyHelper.MODULE_NOT_PASSED_THIRD_TRY,
                                            MyHelper.MODULE_NOT_ENROLLED_YET};


        int[] drawables= new int[]{R.color.green,
                                    R.drawable.border_passed_second_upright,
                                    R.drawable.border_passed_third_upright,
                                    R.color.orange,
                                    R.drawable.border_notpassed_second_upright,
                                    R.drawable.border_notpassed_third_upright,
                                    R.color.gray};


        if(moduleManual == null) {
            moduleManual = ModuleManual.getInstance(context);
        }


        Module tmp = moduleManual.searchModule(moduleTitle);

        for(int i = 0; i<states.length; i++) {

            if(tmp.getStateOf().equals(states[i])) {
                return drawables[i];
            }
        }

        return 0;
    }


}
