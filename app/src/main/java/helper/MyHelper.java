package helper;

public abstract class MyHelper {

	/** # ############# #
	 #   Attributes  #
	 # ############# #
	 */

	public static final String[] PRUEFUNGSFORMEN= {"Projekt","Klausur","Eigenständige","Erfolg.","Variabel,","Bericht,","Bachelor-Arbeit,"};
	public static final String[] LANGUAGES = {"Deutsch","Englisch"};
	public static final String[] ASSUMPTIONS_GERMAN = {"Voraussetzungen für die Teilnahme am Modul", "Inhaltlich erforderliche Voraussetzungen", "Voraussetzungen für die Teilnahme an der Modulprüfung"};
	public static final String[] ASSUMPTIONS_ENGLISH = {"Prerequisites for module participation","Recommended contents of previous modules","Prerequisites for module examination"};
	
	
	public static final String PAGE_STRING = "Seite";
	public static final String MODULE_SHORTCUT_PATTERN = "[M]{1}(\\d){1,2}";	
	public static final String ONE_DIGIT_PATTERN = "([1-6]{1})";				//from 1 to 6, only one digit
	public static final String FROM_ONE_TO_EIGHT_PATTERN = "([1-8]{1})";
	public static final String ECTS_WORKLOAD_OVERVIEW = "ECTS-/Workload-Übersicht";
	public static final String MODULE_DESCRIPTION = "Modulbeschreibung";
	public static final String FOURTH = "4.";
	public static final String AND = "und";
	public static final String MODULE_M28 = "M28";
	public static final String CARRIAGE_RETURN = "\r";
	public static final String PAGE_MIX_PATTERN = "Seite\\s[0-999]{1,3}\\/[0-999]{1,3}";
	public static final String PAGE_INT_PATTERN = "[0-999]{1,3}\\/[0-999]{1,3}";
	
	public static final String WHITESPACE = " ";
	public static final String DOUBLE_WHITESPACE = "  ";
	
	public static final String ASSUMPTION_FOR_PARTICIPATE_MODULE = ASSUMPTIONS_GERMAN[0];
	public static final String ASSUMPTION_FOR_CONTENTUAL 		 = ASSUMPTIONS_GERMAN[1];
	public static final String ASSUMPTION_FOR_PARTICIPATE_EXAM   = ASSUMPTIONS_GERMAN[2];
	
	
	public static final String MODULE_EXAM_GERMAN = "Modulprüfung";
	public static final String MODULE_EXAM_ENGLISH = "Module examination";

    public static final String CHECK_VALUE_ENROLL_EXAM = "checkEnroll_001";
	
	
	/** # ############# #
	 #  Constructor  #
	 # ############# #
	 */

	/** # ############# #
	 #    Methods    #
	 # ############# #
	 */
	
	
	public static String eliminateDoubleWhitespaces(String input) {
		
		String[] tmpArray = input.split(DOUBLE_WHITESPACE);
		String tmpString = "";
		
		for(int i = 0; i<tmpArray.length; i++) {
			tmpString += tmpArray[i] + " ";
		}
		
		return tmpString;
	}
	
	
	public static String replacesCarriageReturn(String input, String oldChar, String newChar) {
		return input.replace(oldChar, newChar);
	}
	
}
