package data;

import android.content.Context;
import android.util.Log;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import file.MyFile;

public class ModuleManual implements Serializable{

	
	/** 
	 # ############# #
	 #   Attributes  #
	 # ############# #
	 */

	private static final long serialVersionUID = -9110947834328788678L;
	
	private String subject,
				   graduation,
				   faculty,
				   university;
	
	private Module module;
	private long id;
	private static long id_counter = 0;

	private ArrayList<Module> moduleList = null;

    private float[] notes = null;

    private Float[] floats = null;




    /** # ############# #
	 #  Constructor  #
	 # ############# #
	 */
	
	public ModuleManual() {
		id = id_counter++;
	}
	
	public ModuleManual(String subject, String graduation, String faculty, String university) {
		
		this();
		
		this.subject = subject;
		this.graduation = graduation;
		this.faculty = faculty;
		this.university = university;
	}
	

	/** # ############# #
	 #    Methods    #
	 # ############# #
	 */
	

    public static ModuleManual getInstance(Context context) {
        return (ModuleManual) new MyFile(context).getObjectFromFile(context.getString(R.string.moduleManualSer));
    }


	public static ArrayList<String> getSemesters(Context context) {

		MyFile myFile = new MyFile(context);
		ModuleManual moduleManual = (ModuleManual) myFile.getObjectFromFile(context.getResources().getString(R.string.moduleManualSer));

		ArrayList<String> tmpList = new ArrayList<>();

		if (moduleManual != null) {

			for (int i = 0; i < moduleManual.getModuleList().size(); i++) {

				String current = moduleManual.getModuleList().get(i).getSemester();
				String indexPlusOne = "";
				String indexPlusTwo = "";

				if (i < moduleManual.getModuleList().size() - 1) {
					indexPlusOne = moduleManual.getModuleList().get(i + 1).getSemester();
				}
				if (i < moduleManual.getModuleList().size() - 2) {
					indexPlusTwo = moduleManual.getModuleList().get(i + 2).getSemester();
				}

				//Log.e("current: " +current," indexPlusOne: " +indexPlusOne);
				if (!current.equals(indexPlusOne) && !current.equals(indexPlusTwo)) {
					tmpList.add(moduleManual.getModuleList().get(i).getSemester() + context.getResources().getString(R.string.dotPlusSemester));
				}
			}//for
		}

		return tmpList;
	}

	/**
	 * get modules of respective semester
	 * */
	public ArrayList<String> getModules(String semester) {

		ArrayList<String> tmpList = new ArrayList<>();

		for(int i = 0; i<getModuleList().size(); i++) {

			String sem = getModuleList().get(i).getSemester();

                /*
                 * Note that this takes a regular expression, so remember to escape special characters if necessary,
                 * e.g. if you want to split on period . which means "any character" in regex, use either split("\\.")
                 * or split(Pattern.quote("."))
                 */
			String semSplited = semester.split(Pattern.quote("."))[0];

			if(semSplited.equals(sem)) {
				tmpList.add(getModuleList().get(i).getTitle());
			}
		}
		return tmpList;
	}

	public Module searchModule(String title) {

		Module tmpModul = null;

		for(int i = 0; i<moduleList.size(); i++) {
			if(moduleList.get(i).getTitle().equals(title)) {
				tmpModul = moduleList.get(i);
                break;
            }
		}
		return tmpModul;
	}

    public ModuleManual replaceModuleInList(Module module) {

        for(int i = 0; i<moduleList.size(); i++) {
            if(moduleList.get(i).getTitle().equals(module.getTitle())) {
                moduleList.remove(i);
                moduleList.add(i, module);
                break;
            }
        }
        return this;
    }

    public int getTotalCreditPoints() {

        int creditPoints = 0;

        for(int i = 0; i<moduleList.size(); i++) {
            creditPoints += Integer.parseInt(moduleList.get(i).getCreditPoints());
        }

        return creditPoints;
    }




	public void info() {
		//System.out.println("Subject: " +this.subject + " graduation: " +this.graduation + " faculty: " +this.faculty + " university: " + this.university);
	}
	
	
	/**
	 * 
	 * Setter-/getter
	 * */
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGraduation() {
		return graduation;
	}

	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public Module getModul() {
		return module;
	}

	public void setModul(Module module) {
		this.module = module;
	}

	public ArrayList<Module> getModuleList() {
		return moduleList;
	}

	public void setModuleList(ArrayList<Module> moduleList) {
		this.moduleList = moduleList;
	}



}
