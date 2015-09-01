package interfaces;

import android.os.Bundle;

import java.util.ArrayList;

import data.Module;

/**
 * Created by Ramazan Cinardere on 26.08.15.
 */
public interface ModuleAdministrator {

    /**
     * # ################ #
     * #      Methods     #
     * # ################ #
     */

    public boolean subScribeModule(Module module);
    public boolean unSubscribeModule(Module module);
    public boolean updateModuleContent(Module module);


    public ArrayList<Module> getEnrolledModules();
    public ArrayList<Module> getUnSubscribedModules();

    public ArrayList<Module> getCompletedExams();

    public ArrayList<Module> getPassedExams();
    public ArrayList<Module> getNotPassedExams();

    public ArrayList<Module> getProjects();

    public Bundle desiredNoteAverage(float desire);

    public float getAverageNotes();
    public int getCreditPoints();

}
