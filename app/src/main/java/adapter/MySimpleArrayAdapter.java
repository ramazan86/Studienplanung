package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cinardere_ramazan_ba_2015.studienplanung.MainActivity;
import com.cinardere_ramazan_ba_2015.studienplanung.R;

import org.w3c.dom.Text;

import activity.InformationAboutEnrolledExam;
import data.Module;
import dialog.MyAlertDialog;
import file.MyFile;
import fragment.CompletedExams;
import fragment.EnrolledExams;
import fragment.MyFragment;
import fragment.Projects;
import fragment.UnSubscribedExams;
import helper.MyHelper;



/**
 * @author  Ramazan Cinardere on 28.08.15.
 */


/*Diese Klasse wird von MyFragment benutzt*/
public class MySimpleArrayAdapter extends ArrayAdapter <String> implements View.OnLongClickListener, DialogInterface.OnClickListener {

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private Context context = null;
    private String[] values = null;
    private int pos;
    private MyAlertDialog myAlertDialog = null;
    private FragmentActivity fragmentActivity = null;

    private CompletedExams completedExams       = null;
    private EnrolledExams enrolledExams         = null;
    private UnSubscribedExams unSubscribedExams = null;
    private Projects projects = null;

    private int layoutId = 0;

    private ArrayAdapter<String> adapter = null;
    private String title = "";

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    public MySimpleArrayAdapter(Context context, String[] values, Object obj) {
        this(context, values);

        if(obj instanceof UnSubscribedExams) {
            unSubscribedExams = (UnSubscribedExams) obj;
        }
        else if(obj instanceof EnrolledExams) {
            enrolledExams = (EnrolledExams) obj;
        }
        else if(obj instanceof CompletedExams) {
            completedExams = (CompletedExams) obj;
        }
        else if(obj instanceof Projects) {
            projects = (Projects) obj;
        }

    }


    public MySimpleArrayAdapter(Context context, String[] values, int layoutId) {
        this(context, values);
        this.layoutId = layoutId;
    }

    public MySimpleArrayAdapter(Context context, String[] values) {
        super(context, R.layout.my_rowlayout, values);
        this.context = context;
        this.values  = values;
    }



    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Log.e("getView "," valuesSize: " +values.length + " positon: " +position);


        if(layoutId != 0) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rootView = inflater.inflate(layoutId, parent, false);

            return rootView;

        }else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rootView = inflater.inflate(R.layout.my_rowlayout, parent, false);

            //get components of view
            TextView textView = (TextView) rootView.findViewById(R.id.myRowlayout_imageView_text);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.myRowlayout_imageView_image);

            textView.setText(values[position]);
            textView.setOnLongClickListener(this);
            imageView.setImageResource(R.drawable.examination);

            return rootView;
        }

    }

    @Override
    public boolean onLongClick(View v) {

        String moduleTitle = "";

        if(v instanceof TextView) {
            moduleTitle = ((TextView)v).getText().toString();
        }

        myAlertDialog = new MyAlertDialog(fragmentActivity);
        myAlertDialog.setTitle(moduleTitle);
        final String finalModuleTitle = moduleTitle;
        title = finalModuleTitle;


        if(unSubscribedExams != null) {
            adapter = new ArrayAdapter<String>(fragmentActivity, android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.longClickModule_2));
        }
        else if(completedExams != null || projects != null) {
            adapter = new ArrayAdapter<String>(fragmentActivity, android.R.layout.simple_list_item_1, new String[]{context.getResources().getStringArray(R.array.longClickModule_1)[1]});
        }

        else if(enrolledExams != null){
            adapter = new ArrayAdapter<String>(fragmentActivity, android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.longClickModule_1));
        }

        myAlertDialog.setAdapter(adapter, this);
        myAlertDialog.buildDialogWithNeutralButton("Schliesen", "-1");


        return true;
    }


    private void openDialog(String finalModuleTitle) {

        String checkValue    = "";
        String dialogTitle   = "";
        String dialogMessage = "";

        if(enrolledExams != null) {
            checkValue    = MyHelper.CHECK_VALUE_MODULE_UNSUBSCRIBE;
            dialogTitle   = context.getResources().getString(R.string.unsubscribeModule);
            dialogMessage = context.getResources().getString(R.string.questUnSubscribePraefix) + ":" +System.getProperty("line.separator") + ">> " + finalModuleTitle + " << " + System.getProperty("line.separator") +  context.getResources().getString(R.string.questForUnsubscribeModuleSuffix);
            enrolledExams = null;
        }
        else if(unSubscribedExams != null) {
            checkValue = MyHelper.CHECK_VALUE_ENROLL_EXAM;
            dialogTitle = context.getResources().getString(R.string.enrollExam);
            dialogMessage = context.getResources().getString(R.string.questionForEnrollExamPraefix) + ": " +System.getProperty("line.separator") + finalModuleTitle + System.getProperty("line.separator") +context.getResources().getString(R.string.questionForEnrollExamSuffix);
            unSubscribedExams = null;
        }

           Bundle data = new Bundle();
           data.putString(context.getResources().getString(R.string.moduleTitle), finalModuleTitle);

           MyAlertDialog dialog = new MyAlertDialog(dialogTitle, dialogMessage, fragmentActivity);
           dialog.setBundle(data);
           dialog.buildDialogWithPositiveAndNegativeButton(context.getResources().getString(R.string.yes),
                   context.getResources().getString(R.string.no), checkValue);

    }


    public void setActivity(FragmentActivity activity) {
        this.fragmentActivity = activity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

       //if "info" is clicked
        if(adapter.getItem(which).toString().equals(context.getResources().getStringArray(R.array.longClickModule_1)[1])) {
            Intent intent = new Intent(context, InformationAboutEnrolledExam.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(context.getString(R.string.moduleTitle), title);
            context.startActivity(intent);
        }else {
            openDialog(title);
            notifyDataSetChanged();
        }

    }
}
