package dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.cinardere_ramazan_ba_2015.studienplanung.MainActivity;

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
    private MainActivity mainActivity = null;

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
        this.mainActivity = (MainActivity) context;
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
        setPositiveButton(btnPosText,this);
        setNegativeButton(btnNegtvText,this);
        create().show();


    }

    public void buildDialogWithNegativButton(String id) {


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

    /*
        if(checkVal.equals(MyHelper.CHECK_VAL_VERSION)) {

        }


        if(checkVal.equals(MyHelper.CHECK_VAL_PDF)) {

        }

        if(checkVal.equals(MyHelper.CHECK_VAL_TRASH)) {

            if(which == AlertDialog.BUTTON_NEGATIVE) {
            }
            if(which == AlertDialog.BUTTON_POSITIVE) {
                mainActivity.getPdfReader().deleteEvent(mainActivity);
                MainActivity.CHECK_FLAG = true;
                mainActivity.getRotaList().clear();
                mainActivity.deleteEntryFromDatabase(mainActivity.getTmpIdList());
                mainActivity.setPath(null);
                mainActivity.closeDataBase();
             }
        }//checkVal.TRASH


        if(checkVal.equals(MyHelper.CHECK_VAL_RATING)) {

            if(which == AlertDialog.BUTTON_POSITIVE || which == AlertDialog.BUTTON_NEUTRAL) {
                mainActivity.openMarket();
            }

        }*/

    }
}
