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

    boolean subScribeModule(Module module);
    boolean unSubscribeModule(Module module);

    ArrayList<Module> getEnrolledModules();
    ArrayList<Module> getUnSubscribedModules();
}
