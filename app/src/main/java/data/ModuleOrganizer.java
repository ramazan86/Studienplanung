package data;

import android.content.Context;

/**
 * Created by Ramazan Cinardere on 26.08.15.
 */
public class ModuleOrganizer implements ModuleAdministrator {


    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ModuleManual moduleManual = null;
    private Module module             = null;

    private Context context = null;


    ////////////////////////////
    //       Constructor      //
    ////////////////////////////

    public ModuleOrganizer(Context context) {
        this.context = context;
    }

    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    public boolean enrolForModule(Module module) {
        return false;
    }

    @Override
    public boolean signUpFromModule(Module module) {
        return false;
    }

}
