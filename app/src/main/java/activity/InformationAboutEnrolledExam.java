package activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
public class InformationAboutEnrolledExam extends ActionBarActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ActionBar actionBar = null;

    private TextView textView_actionBar_save_edit   = null,
                     textView_headline              = null,
                     textView_typeValue             = null,
                     textView_dateTimeValue         = null,
                     textView_enrolledAtValue       = null,
                     textView_roomValue             = null,
                     textView_countTries            = null,
                     textView_cp                    = null;

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

       //custom acitonbar
        createCustomActionBar();
        initReferences();
        initComponents();

        getAssignedValues();

        assignValueIntoComponents();

    }

    private void assignValueIntoComponents() {

        textView_headline.setText(moduleTitle);

        //get reference to respective title
        module = moduleManual.searchModule(moduleTitle);

        //assign values
        textView_typeValue.setText(module.getExamType());
        textView_dateTimeValue.setText(module.getDateOfExam() + System.getProperty("line.separator")+ "\t" + "\t" + "\t"+ "\t"+ "\t" + module.getTimeOfExam());
        textView_enrolledAtValue.setText(module.getEnrolledDate());
        textView_roomValue.setText(module.getRoom());

        editText_note.setText(module.getGrade());
        textView_cp.setText(module.getCreditPoints());

        textView_countTries.setText("(" +module.getNumberOfTrials() + ")");

    }

    private void initReferences() {
        moduleOrganizer = new ModuleOrganizer(this);
        moduleManual    = ModuleManual.getInstance(this);

    }

    private void initComponents() {

        //TextView
        textView_headline           = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_headLine);

        textView_typeValue          = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_typeContent);
        textView_dateTimeValue      = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_examDateTimeContent);
        textView_enrolledAtValue    = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_examSubscribedDateTime);
        textView_roomValue          = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_roomContent);
        textView_countTries         = (TextView) findViewById(R.id.informationAboutEnrolledExam_textView_countTries);
        textView_cp                 = (TextView) findViewById(R.id.informationAboutEnrolledExam_editText_cp);

        //EditText
        editText_note  = (EditText) findViewById(R.id.informationAboutEnrolledExam_editText_note);
        editText_note.setOnEditorActionListener(this);
        editText_room  = (EditText) findViewById(R.id.informationAboutEnrolledExam_editText_room);
        editText_room.setOnEditorActionListener(this);

        //Edittextarray
        editTextArray = new EditText[2];
        editTextArray[0] = editText_note;
        editTextArray[1] = editText_room;

        //View
        view_state = findViewById(R.id.informationAboutEnrolledExam_view_stateContent);

        //ImageButton
        imageButton_info = (ImageButton) findViewById(R.id.informationAboutEnrolledExam_imageButton_info);
        imageButton_info.setOnClickListener(this);

        }

    private void getAssignedValues() {

        moduleTitle = getIntent().getExtras().getString(getString(R.string.moduleTitle));
    }

    private void createCustomActionBar() {

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.radial_background));

        LayoutInflater myInlater = LayoutInflater.from(this);
        View customView = myInlater.inflate(R.layout.my_actionbar, null);

        textView_actionBar_save_edit = (TextView) customView.findViewById(R.id.myActionbar_textView_add);
        textView_actionBar_save_edit.setText(getResources().getString(R.string.edit));
        textView_actionBar_save_edit.setAllCaps(false);
        textView_actionBar_save_edit.setOnClickListener(this);


        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == textView_actionBar_save_edit.getId()) {

            if(textView_actionBar_save_edit.getText().equals(getString(R.string.save))) {
                textView_actionBar_save_edit.setText(getString(R.string.edit));

                String room = editText_room.getText().toString();
                String note = editText_note.getText().toString();

                if(!room.equals("") || room != null) {
                    module.setRoom(room);
                }

                if(!note.equals("") || note != null) {
                    module.setGrade(note);
                }

            // show dialog, if user click "ja" module content will update
                String dialogMessage = getString(R.string.updateModulePraefix) + System.getProperty("line.separator") + moduleTitle + System.getProperty("line.separator")  +getString(R.string.updateModuleSuffix);
                Bundle data = new Bundle();
                data.putSerializable(getString(R.string.module), module);

                showMyAlertDialog(getString(R.string.edit), dialogMessage, data);
            // -------------------

                changeEditTextUseAbility(false);

            }else if(textView_actionBar_save_edit.getText().equals(getString(R.string.edit))) {
                textView_actionBar_save_edit.setText(getString(R.string.save));

                changeEditTextUseAbility(true);

            }





        }

        else if(v.getId() == R.id.informationAboutEnrolledExam_imageButton_info) {

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

    private void showMyAlertDialog(String title, String dialogMessage, Bundle data) {

        myAlertDialog = new MyAlertDialog(this);
        myAlertDialog.setTitle(title);
        myAlertDialog.setMessage(dialogMessage);
        myAlertDialog.setBundle(data);
        myAlertDialog.buildDialogWithPositiveAndNegativeButton("Ja","Nein",MyHelper.CHECK_VALUE_MODULE_EDIT);


    }

    private void changeEditTextUseAbility(boolean state) {

        for(int i = 0; i<editTextArray.length; i++) {

            if(state) {
                editTextArray[1].setVisibility(View.VISIBLE);
            }

            editTextArray[i].setFocusableInTouchMode(state);
            editTextArray[i].setEnabled(state);
            editTextArray[i].setClickable(state);
            editTextArray[i].setFocusable(state);
            editTextArray[i].setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}
