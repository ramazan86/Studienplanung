package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import data.Module;
import dialog.MyAlertDialog;
import file.MyFile;
import fragment.CompletedExams;
import fragment.EnrolledExams;
import fragment.MyFragment;
import fragment.UnSubscribedExams;
import helper.MyHelper;


/**
 * @author  Ramazan Cinardere on 28.08.15.
 */


/*Diese Klasse wird von MyFragment benutzt*/
public class MySimpleArrayAdapter extends ArrayAdapter <String> implements View.OnLongClickListener {

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

    private int layoutId = 0;


    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    public MySimpleArrayAdapter(Context context, String[] values, int layoutId) {
        this(context, values);
        this.layoutId = layoutId;


        /*if(obj instanceof CompletedExams) {
            completedExams = (CompletedExams) obj;
        }
        else if(obj instanceof EnrolledExams) {
            enrolledExams = (EnrolledExams) obj;
        }
        else if(obj instanceof UnSubscribedExams) {
            unSubscribedExams = (UnSubscribedExams) obj;
        }*/
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

        showDialog(context.getResources().getStringArray(R.array.longClickModule));

        myAlertDialog = new MyAlertDialog(fragmentActivity);
        myAlertDialog.setTitle(moduleTitle);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(fragmentActivity, android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.longClickModule));

        //myAlertDialog.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,context.getResources().getStringArray(R.array.longClickModule)), new DialogInterface.OnClickListener() {
        final String finalModuleTitle = moduleTitle;
        myAlertDialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    //unsubscribe
                    case 0:
                        openDialog(finalModuleTitle);
                        notifyDataSetChanged();
                        break;

                    //edit
                    case 1:
                        break;
                }

            }
        });
        myAlertDialog.buildDialogWithNeutralButton(context.getResources().getString(R.string.close), "1");

        return false;
    }

    private void openDialog(String finalModuleTitle) {

        //Unsubscribe module respective to moduleTitle
        String title = context.getResources().getString(R.string.unsubscribeModule);
        String message = context.getResources().getString(R.string.questUnSubscribePraefix) + ":" +System.getProperty("line.separator") + ">> " + finalModuleTitle + " << " + System.getProperty("line.separator") +  context.getResources().getString(R.string.questForUnsubscribeModuleSuffix);

        Bundle data = new Bundle();
        data.putString(context.getResources().getString(R.string.moduleTitle), finalModuleTitle);

        MyAlertDialog dialog = new MyAlertDialog(title, message, fragmentActivity);
            dialog.setBundle(data);
            dialog.buildDialogWithPositiveAndNegativeButton(context.getResources().getString(R.string.yes),
                                                            context.getResources().getString(R.string.no),
                                                            MyHelper.CHECK_VALUE_MODULE_UNSUBSCRIBE);

    }

    private void showDialog(String[] values) {}

    public void setActivity(FragmentActivity activity) {
        this.fragmentActivity = activity;
    }

    public void clearContent() {
        this.clear();
    }
}
