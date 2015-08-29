package interfaces;

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

    public ArrayList<Module> getEnrolledModules();
    public ArrayList<Module> getUnSubscribedModules();

    public ArrayList<Module> getCompletedExams();
}
