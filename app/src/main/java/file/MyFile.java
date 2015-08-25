package file;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import data.Module;
import data.ModuleManual;
import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Ramazan Cinardere} on 19.08.2015.
 */
public class MyFile {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private static FileOutputStream fileOutputStream = null;
    private static ObjectOutputStream objectOutputStream = null;

    private static FileInputStream fileInputStream = null;
    private static ObjectInputStream objectInputStream = null;

    private File wrapFolder, serFolder;

    private Context context = null;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////

    public MyFile(Context context) {
        this.context = context;
    }

    /////////////////////////////
    //         Methods         //
    /////////////////////////////

    public boolean checkIfFileExists(String fileName) {

        try {

            File file = new File(Environment.getExternalStorageDirectory() +
                                File.separator +
                                    context.getResources().getString(R.string.app_name) +
                                        File.separator,
                                            context.getResources().getString(R.string.folder_ser));

            for(File f: file.listFiles()) {
                if(f.getName().equals(fileName)) {
                    return true;
                }
            }
            return true;
        }catch (Exception e) {
            Log.e("Exception in checkIfFileExists: ", e.getCause() + " " +e.getMessage());
            return false;
        }
    }

    public boolean deleteFile(String fileName) {

        try {
            File file = new File(Environment.getExternalStorageDirectory() +
                    File.separator +
                    context.getResources().getString(R.string.app_name) +
                    File.separator,
                    context.getResources().getString(R.string.folder_ser));

            for(File f: file.listFiles()) {
                if(f.getName().equals(fileName)) {
                    f.delete();
                }
            }
            return true;
        }catch (Exception e) {
            Log.e("Exception in checkIfFileExists: ", e.getCause() + " " +e.getMessage());
            return false;
        }
    }

    public boolean createFileAndWriteObject(String fileName, Object obj) {

        try {

            File file = new File(Environment.getExternalStorageDirectory() + File.separator +
                    context.getResources().getString(R.string.app_name) +
                    File.separator +
                    context.getResources().getString(R.string.folder_ser), fileName);

            if(checkIfFileExists(fileName)) {
                deleteFile(fileName);
            }

            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);


            if(obj instanceof ModuleManual) {
                ModuleManual tmp = (ModuleManual) obj;
                objectOutputStream.writeObject(tmp);
            }else if(obj instanceof Module) {
                Module tmp = (Module) obj;
                objectOutputStream.writeObject(tmp);
            }

        }catch (Exception e) {
            Log.e("Exception in createFileAndWriteObject", e.getCause() + " " +e.getMessage());
            e.printStackTrace();
            return false;
        }finally {
            try {
                fileOutputStream.close();
                objectOutputStream.close();

                fileOutputStream = null;
                objectOutputStream = null;
            }catch (Exception e) {
                Log.e("Exception in createFileAndWrite.finally {}"," " +e.getCause() + " " +e.getMessage());
                e.printStackTrace();
            }
        }
        return true;
    }


    public Object getObjectFromFile(String fileName) {

        try {

            File file = new File(Environment.getExternalStorageDirectory()
                                    + File.separator + context.getResources().getString(R.string.app_name)
                                        + File.separator
                                            , context.getResources().getString(R.string.folder_ser));

            Log.e("<<<< here >>>> "," <<<< here >>>>");

            for(File f: file.listFiles()) {
                if(f.getName().equals(fileName)) {
                    file = f;
                }
            }

            Log.e("#################"," ##################### ");


            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);

            Log.e("XXXXXXXXXXXXXXXX","XXXXXXXXXXXXXXXXXXXX");


            return objectInputStream.readObject();

        } catch (Exception e) {
            Log.e("Exception in getObjectFromFile: ", e.getCause() + " " +e.getMessage());
            e.printStackTrace();
            return null;
        }finally {
            try {
                fileInputStream.close();
                objectInputStream.close();

                fileOutputStream = null;
                objectOutputStream = null;
            } catch (IOException e) {
                Log.e("getObjectFromFile", e.getCause() + " /// " +e.getMessage());
            }
        }

    }

    public void createFolders() {


        wrapFolder = new File(Environment.getExternalStorageDirectory(), context.getResources().getString(R.string.app_name));

        if(!wrapFolder.exists()) {
            wrapFolder.mkdirs();

            serFolder = new File(wrapFolder.getAbsolutePath(), "ser");

            if(!serFolder.exists()) {
                serFolder.mkdirs();
            }
        }


    }

    public boolean writeObjectIntoFile(String fileName) {

        try {
            return true;
        }catch (Exception e) {
            return false;
        }

    }


}
