package activity;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import data.Module;
import data.ModuleManual;
import data.ModuleOrganizer;
import dialog.MyAlertDialog;
import helper.MyHelper;

/**
 * Created by Ramazan Cinardere on 30.08.15.
 */
public class InformationAboutEnrolledExam extends ActionBarActivity implements View.OnClickListener, TextView.OnEditorActionListener{

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ActionBar actionBar = null;

    private TextView textView_actionBar_save_edit   = null,
                     textView_headline              = null,
                     textView_typeValue             = null,
                     textView_dateValue             = null,
                     textView_enrolledAtValue       = null,
                     textView_roomValue             = null,
                     textView_countTries            = null,
                     textView_cp                    = null,
                     textView_timeValue             = null,
                     textView_state                 = null,
                     textView_enrolledAt            = null,
                     textView_note                  = null,
                     textView_promptNote            = null;


    private EditText editText_note  = null,
                     editText_room  = null;

    private View view_state = null;

    private String moduleTitle = "";

    private ModuleOrganizer moduleOrganizer = null;
    private ModuleManual moduleManual       = null;
    private Module module                   = null;

    private EditText[] editTextArray = null;

    private ImageButton imageButton_info = null;

    private MyAlertDialog myAlertDialog = null;

    private String currentFragment = MyHelper.NOT_A_NUMBER;

    private RelativeLayout relativeLayout_note = null;

    private boolean updateState = false;


    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.information_about_enrolled_exam_view);
        getAssignedValues();


        //custom acitonbar
        createCustomActionBar();
        initReferences();
        initComponents();


        assignValueIntoComponents();

    }

    private void assignValueIntoComponents() {


        String[] tmp = moduleTitle.split(" ");
        String tmpTitle = "";

        if(tmp.length >= 4) {
            for(int i = 0; i<4; i++) {
                tmpTitle += tmp[i] + " ";
            }
            tmpTitle += "[...]";
            textView_headline.setText(tmpTitle);

        }else {
            textView_headline.setText(moduleTitle);
        }




        //get reference to respective title
        module = moduleManual.searchModule(moduleTitle);

        //assign values
        textView_typeValue.setText(module.getExamType());
        textView_dateValue.setText(module.getDateOfExam());
        textView_timeValue.setText(module.getTimeOfExam() + " Uhr");

       //if the current view is UnSubScribed
        String date = module.getEnrolledDate() + " Uhr";

        if(currentFragment.equals("value_2")) {
            date = module.getUnSubScribedDate() + " Uhr";
        }
        textView_enrolledAtValue.setText(date);
      //----------

        String[] enrolledAt = textView_enrolledAt.getText().toString().split(" ");

        if(currentFragment.equals("value_2")) {
            enrolledAt[0] = "Abgemeldet ";
        }

        textView_enrolledAt.setText(enrolledAt[0] + System.getProperty("line.separator") +enrolledAt[1]);

        editText_room.setText(module.getRoom());
        editText_note.setText(module.getGrade());

        textView_cp.setText(module.getCreditPoints());

        textView_state.setText(textView_state.getText() + " (" +module.getNumberOfTrials()+")") ;

        String st = module.getStateOf();
        Log.e("=====> "," st: "+st);

        Drawable drawable = null;

      //set view color of current state of module
        if(st.equals(MyHelper.MODULE_PASSED_FIRST_TRY)) {
            drawable = getResources().getDrawable(R.color.green);
        }
        else if(st.equals(MyHelper.MODULE_PASSED_SECOND_TRY)) {
            drawable = getResources().getDrawable(R.drawable.border_passed_second);
        }
        else if(st.equals(MyHelper.MODULE_PASSED_THIRD_TRY)) {
            drawable = getResources().getDrawable(R.drawable.border_passed_third);
        }

        //not passed
        else if(st.equals(MyHelper.MODULE_NOT_PASSED_FIRST_TRY)) {
            drawable = getResources().getDrawable(R.color.orange);
        }
        else if(st.equals(MyHelper.MODULE_NOT_PASSED_SECOND_TRY)) {
            drawable = getResources().getDrawable(R.drawable.border_notpassed_second);
        }
        else if(st.equals(MyHelper.MODULE_NOT_PASSED_THIRD_TRY)) {
            drawable = getResources().getDrawable(R.drawable.border_notpassed_third);
        }
        //not entry
        else {
            drawable = getResources().getDrawable(R.color.gray);
        }
        view_state.setBackground(drawable);
    }

    private void initReferences() {
        moduleOrganizer = new ModuleOrganizer(this);
        moduleManual    = ModuleManual.getInstance(this);

    }

    private void initComponents() {

        //TextView
        textView_headline           = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_headLine);

        textView_typeValue          = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_typeContent);
        textView_dateValue          = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_date);
        textView_timeValue          = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_time);
        textView_enrolledAtValue    = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_examSubscribedDateTime);
        textView_cp                 = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_cp);
        textView_state              = (TextView) findViewById(R.id.textView_state);
        textView_enrolledAt         = (TextView) findViewById(R.id.textView_enrolledAt);
        textView_note               = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_note);
        textView_promptNote         = (TextView) findViewById(R.id.textView_propmptInputNote);


        //Relativelayout
        relativeLayout_note         = (RelativeLayout) findViewById(R.id.informationAboutEnrolledExam_relative_note);

        //EditText
        editText_note  = (EditText) findViewById(R.id.informationAboutEnrolledExam_editText_note);
        editText_note.setOnEditorActionListener(this);

        editText_room  = (EditText) findViewById(R.id.informationAboutEnrolledExam_editText_room);
        editText_room.setOnEditorActionListener(this);

        //Edittextarray
        if(!currentFragment.equals("value_1")) {
            editTextArray = new EditText[2];

            editTextArray[0] = editText_note;
            editTextArray[1] = editText_room;


        }else {
            editTextArray = new EditText[1];
            editTextArray[0] = editText_room;
        }


        //View
        view_state = findViewById(R.id.informationAboutEnrolledExam_view_state);

        //ImageButton
        imageButton_info = (ImageButton) findViewById(R.id.informationAboutEnrolledExam_imageButton_info);
        imageButton_info.setOnClickListener(this);
    }

    private void getAssignedValues() {

        moduleTitle = getIntent().getExtras().getString(getString(R.string.moduleTitle));
        currentFragment  = getIntent().getExtras().getString("value");

    }

    private void createCustomActionBar() {

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.radial_background));

        LayoutInflater myInlater = LayoutInflater.from(this);
        View customView = myInlater.inflate(R.layout.my_actionbar, null);


        if(!currentFragment.equals("value_2")) {
            textView_actionBar_save_edit = (TextView) customView.findViewById(R.id.myActionbar_textView_add);
            textView_actionBar_save_edit.setText(getResources().getString(R.string.edit));
            textView_actionBar_save_edit.setAllCaps(false);
            textView_actionBar_save_edit.setOnClickListener(this);
        }else {
            textView_actionBar_save_edit = (TextView) customView.findViewById(R.id.myActionbar_textView_add);
            textView_actionBar_save_edit.setText(null);
        }

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onClick(View v) {

        //value_2 == unsubscribed; unsubscribed modules cannot edited
        if(!currentFragment.equals("value_2")) {

            if (v.getId() == textView_actionBar_save_edit.getId()) {

                String room = editText_room.getText().toString();
                String note = editText_note.getText().toString();

                if (textView_actionBar_save_edit.getText().equals(getString(R.string.save))) {

                  //change actionbar button_title
                    textView_actionBar_save_edit.setText(getString(R.string.edit));
                    showTextViewNoteInput(View.INVISIBLE);

                  //update room
                    if(!room.equals("") || room != null) {

                        if(module.getRoom() != null) {
                            if(!module.getRoom().equals(room)) {
                                module.setRoom(room);
                            }
                        }
                    }
                  // update room---------------------------------------------

                  // update grade
                    if(!note.equals("") || note != null) {

                        try {

                            if(!module.getGrade().equals(note)) {
                                module.setGrade(note);

                                if(Float.parseFloat(note) < 5) {
                                    module.setPassed(true);
                                }else {
                                    module.setNotPassed(true);
                                }

                                updateState = true;
                            }else {
                                updateState = false;
                            }
                        }catch (Exception e) {
                            module.setGrade(note);

                            if(Float.parseFloat(note) < 5) {
                                module.setPassed(true);
                            }else {
                                module.setNotPassed(true);
                            }

                            updateState = true;
                        }
                    }
                    //else note is "" || null
                    else {
                        //show textView prompt input Note only if "Fertiggestellt" view is selected
                        if(currentFragment.equals(MyHelper.CHECK_VALUE_COMPLETED_FRAGMENT)) {
                            showTextViewNoteInput(View.VISIBLE);
                        }

                    }
                  // update grade---------------------------------------------

                    if(updateState) {
                        // show dialog, if user click "ja" module content will update
                        String dialogMessage = getString(R.string.updateModulePraefix) + System.getProperty("line.separator") + moduleTitle + System.getProperty("line.separator") + getString(R.string.updateModuleSuffix);
                        Bundle data = new Bundle();
                        data.putSerializable(getString(R.string.module), module);

                        showMyAlertDialog(getString(R.string.edit), dialogMessage, data);
                    }
                    // -------------------

                    changeEditTextUseAbility(false);

                }//textViewActionBar save
                else if (textView_actionBar_save_edit.getText().equals(getString(R.string.edit))) {
                    textView_actionBar_save_edit.setText(getString(R.string.save));
                    changeEditTextUseAbility(true);
                }

                }
        }//if(checvalue != value_2

        if(v.getId() == R.id.informationAboutEnrolledExam_imageButton_info) {

            LayoutInflater factory = LayoutInflater.from(this);
            final View stateView = factory.inflate(R.layout.dialog_state_information, null);

            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setView(stateView);

            stateView.findViewById(R.id.dialogStateInformation_back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            alertDialog.show();
        }

    }

    private void showTextViewNoteInput(int status) {
        textView_promptNote.setVisibility(status);
    }

    private void showMyAlertDialog(String title, String dialogMessage, Bundle data) {

        myAlertDialog = new MyAlertDialog(this);
        myAlertDialog.setTitle(title);
        myAlertDialog.setMessage(dialogMessage);
        myAlertDialog.setBundle(data);
        myAlertDialog.buildDialogWithPositiveAndNegativeButton("Ja","Nein",MyHelper.CHECK_VALUE_MODULE_EDIT);


    }

    private void changeEditTextUseAbility(boolean state) {


        for(int i = 0; i<editTextArray.length; i++) {

            editTextArray[i].setFocusableInTouchMode(state);
            editTextArray[i].setEnabled(state);
            editTextArray[i].setClickable(state);
            editTextArray[i].setFocusable(state);
            editTextArray[i].requestFocus();
            editTextArray[i].setInputType(InputType.TYPE_CLASS_TEXT);
        }


    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


        switch (actionId) {

            case EditorInfo.IME_ACTION_DONE:

            if(v.getId() == editText_note.getId()) {

                if(!v.getText().toString().matches(MyHelper.PATTERN_NOTE)) {
                    showInvalidNoteDialog();
                }
            }
            break;
        }





        return false;
    }


    public void showInvalidNoteDialog() {

        LayoutInflater factory = LayoutInflater.from(this);
            final View stateView = factory.inflate(R.layout.invalid_note_input, null);

            TextView textView = (TextView) stateView.findViewById(R.id.invalidNoteInput_textView_valideNote);


        String title = "Erlaubte Werte sind: ";
        //String intNumbers = "Ganze Zahlen: >= 1 und <= 5";
        String intNumbers = "Ganze Zahlen: {1, 2, ..., 5}";
        //String decimalNumbers = "Dezimahlzahlen: >= 1.0 und <= 5.0";
        String decimalNumbers = "Dezimahlzahlen: {1.0, 1.1, ..., 5.0}";

        StringBuilder stringBuilder = new StringBuilder()
                                            .append(title)
                                            .append(System.getProperty("line.separator"))
                                            .append(System.getProperty("line.separator"))
                                            .append(intNumbers)
                                            .append(System.getProperty("line.separator"))
                                            .append(decimalNumbers);

       //integers
        String[] splitIntNumbers = intNumbers.split(" ");
        int begin = title.length()-2+ splitIntNumbers[0].length() + splitIntNumbers[1].length() + splitIntNumbers[2].length() + 3;

        String[] splitDec = decimalNumbers.split(" ");
        int beg = (title.length() + intNumbers.length()+2) + splitDec[0].length()+1;

        Spannable spannable = new SpannableString(stringBuilder);
            spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), begin,(title.length()+intNumbers.length()+2),0);
            spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), beg, stringBuilder.length(),0);

        //-------------

        textView.setText(spannable);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setView(stateView);

            stateView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
    }


}
