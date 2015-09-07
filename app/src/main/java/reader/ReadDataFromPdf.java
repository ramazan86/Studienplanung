package reader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.MainActivity;
import data.Module;
import data.ModuleManual;
import com.cinardere_ramazan_ba_2015.studienplanung.R;

import helper.MyHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;


/**
 * 
 * @author Ramazan Cinardere
 * @category Bachelor Thesis
 * @version 1
 * 
 * This class is for the use of my Bachelor-Thesis respective 
 * for the University "Frankfurt University of Applied Science"
 */

public class ReadDataFromPdf extends AsyncTask<String,Void,Void>{
	
	/** 
	 # ############# #
	 #   Attributes  #
	 # ############# #
	 */

	private final String TAG = getClass().getPackage().getName() + " " +getClass().getName();

	private File file = null;
	private PDDocument document = null;
	private PDFTextStripper textStripper = null;
	
	private ArrayList<String> contentList = null;
	private ArrayList<Module> moduleList = null;
	private ArrayList<String> pageList = null;
	
	private int numberOfPages = 0,
			beginColumn_Modultitel,
			beginColumn_Pruefungsform,
			beginColumn_Language,
			beginColumn_creditPoints,
			beginColumn_lehrformen,
			beginColumn_duration;
	
	private ModuleManual moduleManual = null;
	private Module module = null;
	
	private boolean moduleDescriptionBegin = false,
					  moduleDescriptionEnd = false;
	
	private int position_moduleDescriptionBegin,
				position_moduleDescriptionEnd;
	
	private String currentPage;

	private Context context = null;


	/** # ############# #
	 #  Constructor  #
	 # ############# #
	 */

	public ReadDataFromPdf(File file, MainActivity mainActivity) {
		this.file = file;
		this.contentList = new ArrayList<String>();
		this.pageList = new ArrayList<>();
		this.context = mainActivity.getApplicationContext();


		try{
			//this.numberOfPages = getNumberOfPages();
			extractModuleManual();
			//setPages();
			//setAssumptions();
		}catch (Exception e) {
			Log.e(TAG, e.getCause() + " /// " +e.getMessage());
		}

	}


	/** # ############# #
	 #    Methods    #
	 # ############# #
	 */


	private void checkIfObjectIsInitiated(Object obj) {
		if(obj != null) obj = null;
	}
	
	private void extractModuleManual() {

		if(this.file != null) {
			
			if(document != null || textStripper != null) {
				document = null;
				textStripper = null;
			}
			//check if whether array list has already content
			if(contentList.size() != 0) {
				contentList.clear();
			}



            Log.e(" ======>>> "," <<<======== ");
			
			try {

				//PDFBoxResourceLoader.init(context);

				document = PDDocument.load(file);
				
				textStripper = new PDFTextStripper();
				textStripper.setStartPage(1);
				//textStripper.setEndPage(numberOfPages);
				textStripper.setEndPage(121);

				
				String contentOfModuleManual = textStripper.getText(document);
				StringTokenizer st = new StringTokenizer(contentOfModuleManual, " \n");

				int i = 0;
				int countOfPages = 0;
				int indexOfPageSix = 0;
				
				while(st.hasMoreTokens()) {
					
					/*content shows each worth/character in the whole moduleManual*/
					String content = st.nextToken();
					contentList.add(content);

					Log.e("" +i,content);

                    if(i >= 14 && i<= 40) {
                        extractTitlePage(i, content);
                    }


					
					i++;			//move to the next string in moduleManual
				}//while loop closing
				document.close();
			} catch (IOException e) {
				Log.e(TAG, e.getCause() + " ./. " +e.getMessage());
			}
			
		}//if(this.file != null)
	}
	
	/**
	 * This method eliminates duplicates, which occurred during initializing module_objects
	 */
	private void eliminateDuplicates() {

		String[] titles;
		String[] modulExams;

		for(int i = 0; i<moduleList.size(); i++) {
			
		//check title
			titles = moduleList.get(i).getTitle().split(" ");
			modulExams = moduleList.get(i).getModuleExamination().split(" ");
			
			for(int k = 0; k<titles.length; k++) {
				
				if(k>0) {
					
					if(titles[k].equals(titles[k-1])) {
						titles[k] = null;
						moduleList.get(i).setTitle(StringUtils.join(titles, " ").replace("  ", " "));	//StringUtils "Apache" commons
					}
				}
			}
			
		//check if title contains creditPoints of respective module
			if(titles[titles.length-1].contains(moduleList.get(i).getCreditPoints())) {
				titles[titles.length-1] = null;
				moduleList.get(i).setTitle(StringUtils.join(titles, " ").replace("  ", " "));	//StringUtils "Apache" commons
			}
			
		//check teachForm
			if(moduleList.get(i).getTeachForm().contains(modulExams[0]) && !modulExams[0].equals("Projekt")) {
				
				int max = moduleList.get(i).getTeachForm().indexOf(modulExams[0]);
				String teachForm = moduleList.get(i).getTeachForm().substring(0, max);
				
				moduleList.get(i).setTeachForm(teachForm);
			}

		}//for(int i = 0 ...
		
		
	}

	private void extractWorkloadOverview(int lastIndex) {
		
	//Local attributes
		ArrayList<Integer> diffList = new ArrayList<>();
		ArrayList<Integer> posList = new ArrayList<>();
		
		String semester 	= "", 
			   moduleTitle  = "", 
			   creditPoints = "", 
			   duration		= "", 
			   teachForm	= "", 
			   examForm		= "", 
			   language		= "", 
			   sws			= "",
			   currentWord	= "",
			   lastWord     = "",
			   nextWord     = "",
			   nextToLastWord 	    = "",
			   word_indexMinusFour  = "",
			   word_indexMinusSix   = "",
			   word_indexMinusThree = "";
		

	//Get the position number of each ModulTitle and store it into "posList"
		for(int i = 0; i<contentList.size(); i++) {
			if(contentList.get(i).matches(MyHelper.MODULE_SHORTCUT_PATTERN) && contentList.get(i-4).equals(MyHelper.AND)) {
				posList.add(i-6);
			}else if(contentList.get(i).matches(MyHelper.MODULE_SHORTCUT_PATTERN) && contentList.get(i-1).charAt(0) == 13) {
				posList.add(i-2);
			}else if(contentList.get(i).matches(MyHelper.MODULE_SHORTCUT_PATTERN) && Pattern.matches("\\d", contentList.get(i-1))) {
				posList.add(i-1);
			}
		}
		
		
	//Get the number of character/ length of each module row
		for(int i = 0; i<posList.size(); i++) {
			if(i>0) {
				int diff = posList.get(i) - posList.get(i-1);
				
				//ModuleTitle "M28"
				try {
					if(String.valueOf(contentList.get(posList.get(i)+1)).equals(MyHelper.MODULE_M28)) {
						diff = lastIndex - posList.get(i)+1;
						diffList.add(diff);
					}
				} catch (Exception e) {
				}
				diffList.add(diff);
			}
		}
		
		
	/**
	 * module objects will initialized and assigned with logical values extracted from the table "ECTS-Workload-Übersicht"
	 */
		
		//this list will filled with module objects
		//each object is a module
		moduleList = new ArrayList<Module>();
		
		for(int i = 0; i<posList.size(); i++) {
			checkIfObjectIsInitiated(module);
			module = new Module();
			
			for(int k = 0; k<diffList.get(i); k++) {
				
			//	
				currentWord = contentList.get(posList.get(i)+k);
				
				if(k>0) {
					lastWord = contentList.get(posList.get(i)+(k-1));
				}if(k>1) {
					nextToLastWord = contentList.get(posList.get(i)+(k-2));
				}if(k>2){
					word_indexMinusThree = contentList.get(posList.get(i)+(k-3));
				}if(k>3) {
					word_indexMinusFour = contentList.get(posList.get(i)+(k-4));
				}if(k>4) {
					word_indexMinusSix = contentList.get(posList.get(i)+(k-6));
				}
			//-------------------------	
			
				
			//Column Modultitel, ECTS, Dauer[Sem]
				if (currentWord.equals("5") || currentWord.equals("10") || currentWord.equals("12") || currentWord.equals("15") || currentWord.equals("18")) {

					beginColumn_creditPoints= (posList.get(i) + k);

					// assign creditpoints
					creditPoints = currentWord;

					// get the duration
					if (!contentList.get(beginColumn_creditPoints + 1).matches(MyHelper.MODULE_SHORTCUT_PATTERN) && !contentList.get(beginColumn_creditPoints + 1).matches(MyHelper.CARRIAGE_RETURN)) {
						duration = contentList.get(beginColumn_creditPoints + 1);
						beginColumn_duration = beginColumn_creditPoints+1;
					}

					if (contentList.get(beginColumn_Modultitel).equals("M27")) {
						beginColumn_creditPoints = 999;
						beginColumn_duration = beginColumn_creditPoints+1;
					}

					// get the moduleTitle
					// remove the carriage-return and replace it with a
					// whitespace
					for (int c = beginColumn_Modultitel; c < beginColumn_creditPoints; c++) {

						if (contentList.get(c).matches(MyHelper.CARRIAGE_RETURN)) {
							contentList.remove(c);
							contentList.add(c,contentList.get(c).replace("\r", " "));
						}

						// get moduleTitle
						moduleTitle += contentList.get(c) + " ";
					}// for(int c
					module.setTitle(moduleTitle);
					moduleTitle = "";

				}// if(content.equals ...
			//--------------------------------------		
			
			//Column Sem. additional "und"
				if(k == 0 && contentList.get(posList.get(i)+2).equals(MyHelper.AND)) {
					semester = contentList.get(posList.get(i)) + " " +contentList.get(posList.get(i)+2) + " " +contentList.get(posList.get(i)+4);	//[1-6] und [1-6]
					beginColumn_Modultitel = posList.get(i)+5;
					
				}else if(k == 0 && currentWord.matches(MyHelper.ONE_DIGIT_PATTERN)){
					semester = contentList.get(posList.get(i));
					beginColumn_Modultitel = posList.get(i)+1;
					
				}else if(k == 1 && contentList.get(posList.get(i)+1).charAt(0) == 13 && contentList.get(posList.get(i)+2).matches(MyHelper.MODULE_SHORTCUT_PATTERN)) {
					semester = contentList.get(posList.get(i));
					beginColumn_Modultitel = posList.get(i)+2;
				}
			//--------------------------- 	
				
				
				
			//Column SWS, Sprache	
				if(currentWord.matches(MyHelper.FROM_ONE_TO_EIGHT_PATTERN) && (lastWord.contains(MyHelper.LANGUAGES[0]) || lastWord.contains(MyHelper.LANGUAGES[1]))) {
					sws = currentWord;
					language = lastWord;
					
					beginColumn_Language = (posList.get(i)+(k-1));
				}// "Deutsch oder Englisch"
				else if(currentWord.matches(MyHelper.FROM_ONE_TO_EIGHT_PATTERN) 
						&& (nextToLastWord.contains(MyHelper.LANGUAGES[0]) || nextToLastWord.contains(MyHelper.LANGUAGES[1])
						&& word_indexMinusSix.contains(MyHelper.LANGUAGES[0]) || word_indexMinusSix.contains(MyHelper.LANGUAGES[1]))) {
					
					sws = currentWord;
					language = word_indexMinusSix + " " +word_indexMinusFour + " " +nextToLastWord ;
					
					beginColumn_Language = posList.get(i)+(k-6);
				}//Deutsch,Englisch
				else if(currentWord.equals("bel")) {
					language = word_indexMinusThree;
					
					sws = lastWord+currentWord;
					sws = sws.replace(MyHelper.CARRIAGE_RETURN, "");

					beginColumn_Language = (posList.get(i)+(k-3));
				}
			//-------------------------	
				
				
				
			//Column Prüfungsform	
				if((k+posList.get(i)) > beginColumn_creditPoints) {
					
					for(int p = 0; p < MyHelper.PRUEFUNGSFORMEN.length; p++) {
						
						if(MyHelper.PRUEFUNGSFORMEN[p].equals(currentWord)) {
							beginColumn_Pruefungsform = (k+posList.get(i));
							
							for(int b = (k+posList.get(i)); b< posList.get(i)+diffList.get(i);b++){
								String content = contentList.get(b);
									
								if (content.matches(MyHelper.CARRIAGE_RETURN)) {
									content = content.replace(MyHelper.CARRIAGE_RETURN, " ");
								}
								if(content.equals(MyHelper.LANGUAGES[0]) || content.equals(MyHelper.LANGUAGES[1])) {
									break;
								}
								examForm += content + " ";
								examForm = examForm.replace("  ", " ");
							}
							module.setModuleExamination(examForm);
							examForm = "";
							
							if(module.getTitle().contains("M4") || module.getTitle().contains("M14")) {
								module.setModuleExamination("Eigenständige Programmierung in Form einer Klausur");
							}
							
						//Teachform	
							List<String> lehrFormenenList = contentList.subList(beginColumn_duration+1, posList.get(i)+k);
							
								for(int l = 0; l<lehrFormenenList.size();l++) {
									teachForm += lehrFormenenList.get(l) + " ";
								}
								
								if(teachForm.contains(MyHelper.CARRIAGE_RETURN)) {
									teachForm = teachForm.replace(MyHelper.CARRIAGE_RETURN, " ");
									teachForm = teachForm.replace("  ", " ");
								}
								module.setTeachForm(teachForm);
								teachForm = "";
						 //----------------
								
						}//if(MyHelper.PRUEFUNGSFORM...
					}//for(int p...
				}//if(... > beginColumn_creditPoints
			//------------------------
				
				
			}//inner loop
			
				module.setSemester(semester);
				module.setDuration(duration);
				module.setSemesterWeekHours(sws);
				module.setLanguage(language);
				
				if(module.getTitle().contains("M27 Praxisphase")) {
					module.setCreditPoints("18");
				}else {
					module.setCreditPoints(creditPoints);
				}
				moduleList.add(module);
		}//outer loop
	}//extractWorkloadOverview

	private void extractTitlePage(int i, String input) {
		
		if(moduleManual == null) {
			moduleManual = new ModuleManual();
		}
		
		if(i>0 && i == 14 && !input.equals("")) {
			moduleManual.setSubject(input);
		}
		
		if(i>0 && i == 20) {
			moduleManual.setGraduation(contentList.get(i-3)+ " " +contentList.get(i-2)+ " " +contentList.get(i-1)+ " " +contentList.get(i));
		}
		
		if(i>0 && i == 32) {
			
			String faculty = contentList.get(i-10) +contentList.get(i-9) + " " 
								+ contentList.get(i-8) + " " +contentList.get(i-7) + " " +contentList.get(i-6)
									+ " " + contentList.get(i-5)
										+ " " +contentList.get(i-3) + " " +contentList.get(i-2) + " " +contentList.get(i-1)+" " +contentList.get(i);
			
			moduleManual.setFaculty(faculty);
		}
		
		if(i>0 && i == 40) {
			String university = contentList.get(i-4) + " " + contentList.get(i-3) + " " +contentList.get(i-2) + " " +contentList.get(i-1);
			moduleManual.setUniversity(university);
		}



        Log.e("extractTitelpage: "," " +moduleManual.getFaculty());
		
		
		
	}
	
	private int getNumberOfPages() {

		if(this.file != null) {
			
			try {
				document = PDDocument.load(file);
				
				textStripper = new PDFTextStripper();
				textStripper.setStartPage(1);
				textStripper.setEndPage(2);
				
				String content = textStripper.getText(document);
				
				StringTokenizer st = new StringTokenizer(content, " \n");
				int i = 0;

				while(st.hasMoreTokens()) {
					contentList.add(st.nextToken());
					if(i>0 && contentList.get(i-1).equals("Seite")) {
						return Integer.valueOf(contentList.get(i).split("/")[1]);
					}
					i++;
				}
			} catch (IOException e) {
				Log.e(TAG +".getNumberOfPages() " , e.getCause() + " " +e.getMessage());
				return 0;
			} finally {
				try {
					document.close();
				} catch (IOException e) {
				}
			}
			
		}else {
			return 0;
		}
		return 0;


	}
	
	private void setPages() {

		
		int counter = 0;
		for(int i = position_moduleDescriptionBegin; i<contentList.size(); i++) {
			
			String currentWord = contentList.get(i);
			
			String currentWord_indexPlusOne = "";
			if(i+1 < contentList.size()) currentWord_indexPlusOne = contentList.get(i+1);
			
			if(currentWord.matches(MyHelper.MODULE_DESCRIPTION) && currentWord_indexPlusOne.equals("zum")) {
				
				if(counter < pageList.size()) {
					moduleList.get(counter).setPageOfModuleDescription(pageList.get(counter));
				}
				counter++;
			}
		}
	}

	private void setAssumptions() {
		
		String currentWord  = "",
			   assembleWord = "",
			   assumptionForParticipate = "",
			   contentualAssumption     = "",
			   assumptionForModuleExam  = "";
		
		int indexOfParticipateModule = 0,
			indexOfContentual 		 = 0,
			indexOfModuleExam		 = 0,
			beginContentual			 = 0,
			beginOfModuleExam		 = 0,
			counterModule			 = 0;
		
		//Gets the content from 4.Moduledescription till end of modulemanual
		for(int i = position_moduleDescriptionBegin; i<contentList.size(); i++) {
			
			currentWord = contentList.get(i);
			
		//Get the respective indexes
			//MyHelper.ASSUMPTIONS[0].split(" ")[0] == Voraussetzungen
			if(currentWord.equals(MyHelper.ASSUMPTIONS_GERMAN[0].split(" ")[0]) || currentWord.equals(MyHelper.ASSUMPTIONS_ENGLISH[0].split(" ")[0])) {
				
				assembleWord = new String(currentWord + MyHelper.WHITESPACE + 
											contentList.get(i+1) + MyHelper.WHITESPACE + 
												contentList.get(i+2) + MyHelper.WHITESPACE + 
													contentList.get(i+3) + MyHelper.WHITESPACE + 
														contentList.get(i+4) + MyHelper.WHITESPACE +	
															contentList.get(i+5) + MyHelper.WHITESPACE + 
																contentList.get(i+6) + MyHelper.WHITESPACE + 
																	contentList.get(i+8)).replace(MyHelper.CARRIAGE_RETURN, "");
			//eliminiates double whitespaces
				assembleWord = MyHelper.eliminateDoubleWhitespaces(assembleWord);
				
				//Voraussetzungen für die Teilnahme am Modul
				if(assembleWord.contains(MyHelper.ASSUMPTIONS_GERMAN[0]) || assembleWord.contains(MyHelper.ASSUMPTIONS_ENGLISH[0])) {
					indexOfParticipateModule = i+8;
				}
				
				
				//Voraussetzungen für die Teilnahme an der Modulprüfung
				if(assembleWord.contains(MyHelper.ASSUMPTIONS_GERMAN[2]) || assembleWord.contains(MyHelper.ASSUMPTIONS_ENGLISH[2])) {
					indexOfModuleExam = i;
					beginOfModuleExam = i+10;
					
				}
			}
			
			
			//split(" ")[0] == Inhaltlich, split("  ")[1] == erforderliche
			if((currentWord.equals(MyHelper.ASSUMPTIONS_GERMAN[1].split(" ")[0]) || currentWord.equals(MyHelper.ASSUMPTIONS_ENGLISH[1].split(" ")[0])) 
					&& (contentList.get(i+1).equals(MyHelper.ASSUMPTIONS_GERMAN[1].split(" ")[1]) || contentList.get(i+1).equals(MyHelper.ASSUMPTIONS_ENGLISH[1].split(" ")[1]))) {
				
				indexOfContentual = i;
				beginContentual = i+5;
				
			}
			
			
		//Second column; get the respective content	
			//Eigenschaft(en) von "Voraussetzungen für die Teilnahme am Modul"
			if(indexOfParticipateModule != 0 && indexOfContentual != 0) {
				for(int z = indexOfParticipateModule; z<indexOfContentual; z++) {
					assumptionForParticipate += contentList.get(z) + " ";
				}
				
				indexOfParticipateModule = indexOfContentual = 0;
				assumptionForParticipate = assumptionForParticipate.replace(MyHelper.CARRIAGE_RETURN, MyHelper.WHITESPACE);
				
				moduleList.get(counterModule-1).setParticipatePrerequisite(assumptionForParticipate);
				assumptionForParticipate = "";
				
			}
			
			//Eigenschaft(en) von "Inhaltlich erforderliche Voraussetzungen"
			if(beginContentual != 0 && indexOfModuleExam != 0) {
				
				for(int z = beginContentual; z<indexOfModuleExam; z++) {
					contentualAssumption += contentList.get(z) +" ";
				}
				
				contentualAssumption = contentualAssumption.replace(MyHelper.CARRIAGE_RETURN, "");
				beginContentual = indexOfModuleExam = 0;
				
				moduleList.get(counterModule-1).setContentPrerequisite(contentualAssumption);
				contentualAssumption = "";
			}
			
			//Eigenschaft(en) von "Vorraussetzungen für die Teilnahme an der Modulprüfung"
			if(beginOfModuleExam != 0) {
				
				
				while(!contentList.get(beginOfModuleExam).equals(MyHelper.MODULE_EXAM_GERMAN) && !contentList.get(beginOfModuleExam).equals(MyHelper.MODULE_EXAM_ENGLISH)) {
					assumptionForModuleExam += contentList.get(beginOfModuleExam) + " ";
					beginOfModuleExam++;
				}
				
				
				moduleList.get(counterModule-1).setExaminationPrerequisite(MyHelper.replacesCarriageReturn(assumptionForModuleExam, MyHelper.CARRIAGE_RETURN, ""));
				assumptionForModuleExam = "";
				beginOfModuleExam = 0;
			}
			
			
			
			
			if(contentList.get(i).equals(MyHelper.MODULE_DESCRIPTION) && contentList.get(i+1).equals("zum")) {
				counterModule++;
			}
			
			
		}
		
	}



	@Override
	protected Void doInBackground(String... params) {


		Log.e("ReadDataFromPdf: " , params[0]);

		return null;
	}
}
