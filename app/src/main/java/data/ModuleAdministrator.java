package data;

import java.util.ArrayList;

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
    boolean unSubscribeFromModule(Module module);

    ArrayList<Module> getEnrolledModules();
    ArrayList<Module> getUnSubscribedModules();
}
