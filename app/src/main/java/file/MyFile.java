package file;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

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

    public Object getObjectFromFile(String fileName) {

        try {

            File file = new File(Environment.getExternalStorageDirectory()
                                    + File.separator + context.getResources().getString(R.string.app_name)
                                        + File.separator
                                            , context.getResources().getString(R.string.folder_ser));

            for(File f: file.listFiles()) {
                if(f.getName().equals(fileName)) {
                    file = f;
                }
            }

            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);

            return objectInputStream.readObject();

        } catch (Exception e) {
            Log.e("Exception: ", e.getCause() + " " +e.getMessage());
            return null;
        }finally {
            try {
                fileInputStream.close();
                objectInputStream.close();
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
