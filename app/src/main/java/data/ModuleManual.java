package data;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	public Module createInstance() {
		module = new Module();
		return module;
	}
	
	
	public void info() {
		System.out.println("Subject: " +this.subject + " graduation: " +this.graduation + " faculty: " +this.faculty + " university: " + this.university);
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
