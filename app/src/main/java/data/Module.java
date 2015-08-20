package data;

import java.io.Serializable;


public class Module implements Serializable{

	
	/** 
	 # ############# #
	 #   Attributes  #
	 # ############# #
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String title,
				   number,
				   studyPath,
				   moduleCode,
				   units,
				   level,
				   contentualPrerequisite,
				   participatePrerequisite,
				   examinationPrerequisite,
				   pageOfModuleDescription,
				   moduleExamination,
				   teachForm,
				   language,
				   frequency,
				   moduleCoordination,
				   furtherInformation,
				   totalWorkload,
				   duration,
				   creditPoints,
	               mark,
				   semester,
				   semesterWeekHours;		//SWS example 10-12 Uhr => 2 SWS
	
	
	/** # ############# #
	 #  Constructor  #
	 # ############# #
	 */
	
	public Module() {}
	
	
	
	/** # ############# #
	 #    Methods    #
	 # ############# #
	 */
	
	public String info() {
	
		
		return "semester: " +semester +System.getProperty("line.separator") +
				 "title: " +title + System.getProperty("line.separator") + 
				 "duration: " +duration + System.getProperty("line.separator") +
				 "CP'S: " +creditPoints +System.getProperty("line.separator") +
				 "SWS: " +semesterWeekHours + System.getProperty("line.separator") +
				 "Language: " +language +  System.getProperty("line.separator") +
				 "ModulExam: " +moduleExamination +  System.getProperty("line.separator") +
				 "TeachForm: " +teachForm+  System.getProperty("line.separator") +
				 "Page: " +pageOfModuleDescription + System.getProperty("line.separator") +
				 "AssumptionForParticipate: " +getParticipatePrerequisite() + System.getProperty("line.separator") +
				 "ContentualAssumption: " +getContentualPrerequisite() + System.getProperty("line.separator") +
				 "AssumptionForModuleExamination: " +getExaminationPrerequisite() + System.getProperty("line.separator");
		
		
	}
	
	
	
	/**
	 * Setter-/getter
	 * */
	
	public void setSemesterWeekHours(String semesterWeekHours) {
		this.semesterWeekHours = semesterWeekHours;
	}
	
	public String getSemesterWeekHours() {
		return this.semesterWeekHours;
	}
	
	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getSemester() {
		return semester;
	}
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public String getStudyPath() {
		return studyPath;
	}


	public void setStudyPath(String studyPath) {
		this.studyPath = studyPath;
	}


	public String getModuleCode() {
		return moduleCode;
	}


	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}


	public String getUnits() {
		return units;
	}


	public void setUnits(String units) {
		this.units = units;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getContentualPrerequisite() {
		return contentualPrerequisite;
	}


	public void setContentPrerequisite(String contentualPrerequisite) {
		this.contentualPrerequisite = contentualPrerequisite;
	}


	public String getParticipatePrerequisite() {
		return participatePrerequisite;
	}


	public void setParticipatePrerequisite(String participatePrerequisite) {
		this.participatePrerequisite = participatePrerequisite;
	}


	public String getExaminationPrerequisite() {
		return examinationPrerequisite;
	}


	public void setExaminationPrerequisite(String examinationPrerequisite) {
		this.examinationPrerequisite = examinationPrerequisite;
	}


	public String getModuleExamination() {
		return moduleExamination;
	}


	public void setModuleExamination(String moduleExamination) {
		this.moduleExamination = moduleExamination;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getFrequency() {
		return frequency;
	}


	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}


	public String getModuleCoordination() {
		return moduleCoordination;
	}


	public void setModuleCoordination(String moduleCoordination) {
		this.moduleCoordination = moduleCoordination;
	}


	public String getFurtherInformation() {
		return furtherInformation;
	}


	public void setFurtherInformation(String furtherInformation) {
		this.furtherInformation = furtherInformation;
	}


	public String getTotalWorkload() {
		return totalWorkload;
	}


	public void setTotalWorkload(String totalWorkload) {
		this.totalWorkload = totalWorkload;
	}


	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCreditPoints() {
		return creditPoints;
	}

	public void setCreditPoints(String creditPoints) {
		this.creditPoints = creditPoints;
	}

	public String getTeachForm() {
		return teachForm;
	}

	public void setTeachForm(String teachForm) {
		this.teachForm = teachForm;
	}



	public String getPageOfModuleDescription() {
		return pageOfModuleDescription;
	}



	public void setPageOfModuleDescription(String pageOfModuleDescription) {
		this.pageOfModuleDescription = pageOfModuleDescription;
	}


	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
