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
	
	public static final String NOT_A_NUMBER = "NaN";


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


    /**
     * ^        Start of String
     * [1-5]    Number must between 1 und 5 and exactly once
     * (        Begin optional group
     * \.       The second char after [1-5] can be a digit
     * [0-9]{1} Digit between 0-9 and exactly once
     * )?       End of group, signify it's optional with '?'
     * $        End of String
     * */
    public static final String PATTERN_NOTE = "^[1-5]{1}(\\.[0-9]{1})?$";


    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static final String WHITESPACE = " ";
	public static final String DOUBLE_WHITESPACE = "  ";
	
	public static final String ASSUMPTION_FOR_PARTICIPATE_MODULE = ASSUMPTIONS_GERMAN[0];
	public static final String ASSUMPTION_FOR_CONTENTUAL 		 = ASSUMPTIONS_GERMAN[1];
	public static final String ASSUMPTION_FOR_PARTICIPATE_EXAM   = ASSUMPTIONS_GERMAN[2];
	
	
	public static final String MODULE_EXAM_GERMAN = "Modulprüfung";
	public static final String MODULE_EXAM_ENGLISH = "Module examination";

    public static final String CHECK_VALUE_ENROLL_EXAM          = "checkEnroll_001";
    public static final String CHECK_VALUE_MODULE_EDIT          = "moduleEdit";
    public static final String CHECK_VALUE_MODULE_UNSUBSCRIBE   = "moduleUnsubscribe";
    public static final String CHECK_VALUE_ENROLL_WARNING_DATE  = "dateNotAllowed";

    public static final String CHECK_VALUE_ENROLLED_FRAGMENT        = "fragment_enrolled";
    public static final String CHECK_VALUE_UNSUBSCRIBED_FRAGMENT    = "fragment_unsubscribed";
    public static final String CHECK_VALUE_COMPLETED_FRAGMENT       = "fragment_completed";
    public static final String CHECK_VALUE_OVERVIEW_FRAGMENT        = "fragment_overview";
    public static final String CHECK_VALUE_PROJECTS_FRAGMENT        = "fragment_overview";



    public static final String MODULE_PASSED_FIRST_TRY      = "m_001";
    public static final String MODULE_PASSED_SECOND_TRY     = "m_002";
    public static final String MODULE_PASSED_THIRD_TRY      = "m_003";
    public static final String MODULE_NOT_PASSED_FIRST_TRY  = "m_101";
    public static final String MODULE_NOT_PASSED_SECOND_TRY = "m_102";
    public static final String MODULE_NOT_PASSED_THIRD_TRY  = "m_103";

    public static final String MODULE_NOT_ENROLLED_YET      = "m_000";





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
