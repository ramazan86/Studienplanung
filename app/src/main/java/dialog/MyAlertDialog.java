package dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.cinardere_ramazan_ba_2015.studienplanung.R;
import activity.SubscribeExamActivity;

import data.Module;
import data.ModuleManual;
import data.ModuleOrganizer;
import file.MyFile;
import helper.MyHelper;

/**
 * Created by Ramazan Cinardere} on 22.08.2015
 */
public class MyAlertDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener{


    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private String title   = null,
                   message = null;

    private int icon = 0;

    private String checkVal = null;

    private ModuleOrganizer moduleOrganizer = null;
    private ModuleManual moduleManual       = null;
    private Module module                   = null;

    private SubscribeExamActivity subscribeExamActivity = null;

    private Context context = null;
    private Bundle arguments = null;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////

    public MyAlertDialog(String title, String message, int icon,  Context context) {
        this(context);
        this.title = title;
        this.message = message;
        this.icon = icon;
    }
    public MyAlertDialog(String title, String message,  Context context) {
        this(context);
        this.title = title;
        this.message = message;
    }

    public MyAlertDialog(String message, int icon, Context context) {
        this(context);
        this.message = message;
        this.icon = icon;
    }


    public MyAlertDialog(Context context) {
        super(context);

        if(context instanceof SubscribeExamActivity) {
            this.subscribeExamActivity = (SubscribeExamActivity) context;
        }

        this.context = context;
    }

    /////////////////////////////
    //         Methods         //
    /////////////////////////////

    public void buildDialogWithNeutralButton(String btnText, String checkVal) {

        initAttributes(checkVal);
        setNeutralButton(btnText, this).create().show();

    }

    public void buildDialogWithPositiveAndNegativeButton(String btnPosText, String btnNegtvText, String checkVal) {

        initAttributes(checkVal);
        setPositiveButton(btnPosText, this);
        setNegativeButton(btnNegtvText, this);
        create().show();

    }

    public void buildDialogWithNegativeButton(String id) {


    }

    public void initAttributes(String checkVal) {
        if(this.title != null) {
            setTitle(this.title);
        }if(this.message != null) {
            setMessage(this.message);
        }if(this.icon != 0) {
            setIcon(this.icon);
        }
        this.checkVal = checkVal;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if(checkVal.equals(MyHelper.CHECK_VALUE_ENROLL_EXAM)) {

            switch (which) {

                case AlertDialog.BUTTON_POSITIVE:

                    Module m = (Module) arguments.getSerializable(context.getResources().getString(R.string.module));
                    new ModuleOrganizer(context).subScribeModule(m);
                    break;
            }

        }else if(checkVal.equals(MyHelper.CHECK_VALUE_MODULE_UNSUBSCRIBE)) {

            switch (which) {

                case AlertDialog.BUTTON_POSITIVE:

                    if(moduleManual == null) {
                        moduleManual = (ModuleManual) new MyFile(context).getObjectFromFile(context.getResources().getString(R.string.moduleManualSer));
                    }

                    String moduleTitle = arguments.getString(context.getResources().getString(R.string.moduleTitle));
                    Module tmpModule   = moduleManual.searchModule(moduleTitle);

                    new ModuleOrganizer(context).unSubscribeModule(tmpModule);

                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                        dialog.dismiss();
                    break;
            }

        }else if(checkVal.equals(MyHelper.CHECK_VALUE_ENROLL_WARNING_DATE)) {

            switch (which) {
                case AlertDialog.BUTTON_NEUTRAL:
                    dialog.dismiss();
                  break;
            }
        }else if(checkVal.equals(MyHelper.CHECK_VALUE_MODULE_EDIT)) {

            switch (which) {

                case AlertDialog.BUTTON_POSITIVE:
                    new ModuleOrganizer(context).updateModuleContent((Module) arguments.getSerializable(context.getString(R.string.module)));
                    break;

                case AlertDialog.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }



        }//CHECK_VALUE_ENROLL_WARNING_DATE




    }

    public void setBundle(Bundle arguments) {
        this.arguments = arguments;
    }


}
