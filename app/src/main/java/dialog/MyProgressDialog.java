package dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Ramazan Cinardere} on 22.04.2015.
 */
public class MyProgressDialog extends ProgressDialog implements Runnable{


    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private Thread thread = null;
    private final String NAME_threadWheeling = "threadWheeling";
    private final String NAME_threadLoading = "loading";
    private int time;
    private Context context = null;

    ProgressDialog progressDialog = null;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////

    public MyProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;


    }

    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    public void showWheelingProgress(final String title, String message, final int time) {

        setTitle(title);
        setMessage(message);
        setMax(time);
        show();

        thread = new Thread(this);
        thread.setName(NAME_threadWheeling);
        thread.start();
        thread = null;


    }


    public void showWheelingProgress(String message, int time) {
        show(context, message,"");
    }

    @Override
    public void run() {

        if(Thread.currentThread().getName().equals(NAME_threadWheeling)) {
            int i = 0;
            while(i<= getMax()) {
                try {
                    Thread.sleep(50);
                    setProgress(i);
                    //Log.e("i: " +i, " time: " +getMax());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
          //stopProgress();
        }


        if(Thread.currentThread().getName().equals(NAME_threadLoading)) {

        }

    }

    public void stopProgress() {
        dismiss();
    }
}
