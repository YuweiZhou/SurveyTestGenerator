import java.lang.String;
import java.util.List;
import java.io.*;
/**
 * Prints the menu and lead though the main logic of the menu
 * @author Yuwei Zhou
 */
public class Main {
	
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static String filePath;
	
	/**
	 * Main function
	 * @param args the file path to where the survey/test should be saved
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException //initial procedure
    {
		if(args.length <1) {
			filePath = "." + File.separator +"src" + File.separator + "resource" + File.separator;
			System.out.println("No input directory. Using default directory " + filePath);
		}else {
			filePath = args[0];
		}
		System.out.println("Welcome!\n");
		displayMainMenu();
    }
	
	/**
	 * Display the main menu
	 * @throws IOException
	 */
	public static void displayMainMenu() throws IOException {
		//Display the main menu, ask the user for file type (Survey/Test)
        Output.Menu("main");

        String choice = input.readLine();
        
        //Check for invalid input
        while  (inputNotInRange(choice, 2) && !choice.equals("E") && !choice.equals("e")) {
        	System.out.println("Invalid input. Please choose again:");
        	choice = input.readLine();
        }

        //Display Survey or Test Menu based on user choice
        switch (choice) {
            case "1":
            	//survey menu
            	displayCreateLoadSurveyMenu();
                break;
            case "2":
            	//test menu
            	displayCreateLoadTestMenu();
                break;
            case "e":
            case "E":
            	//exit
            	input.close();
            	System.out.println("Bye!");
                return;
        }
	}
	
	/**
	 * Display the menu for upload or create a new survey
	 * @throws IOException
	 */
	public static void displayCreateLoadSurveyMenu() throws IOException{
		Output.Menu("createLoadSurvey");
        String choice = input.readLine();
        
      //Check for invalid input
        while  (inputNotInRange(choice, 3) && !choice.equals("E") && !choice.equals("e")) {
        	System.out.println("Invalid input. Please choose again:");
        	choice = input.readLine();
        }

      //Ask user to create or load a survey
        switch (choice) {
            case "1":
            	//Create a new survey
            	String name = Output.getName(QuestionairType.Survey);
            	Survey s = new Survey(name);
            	if(Saving.SaveSurvey(s, filePath+ name, false)) {
            		displayQuestionTypeSurvey(s);
            	}else {
            		displayCreateLoadSurveyMenu();
            	}
                break;
            case "2":
            	//load a survey
            	List<File> files = Saving.LoadFile(filePath, ".sury", QuestionairType.Survey);
            	if(files != null) {
	            	int index = Output.loadFileMenu(files);
	            	Survey s1 = Saving.OpenSurvey(files.get(index - 1));
	            	if(s1 != null) {
		            	displayDetailedSurveyMenu(s1);
	            	}else {
	            		displayCreateLoadSurveyMenu();
	            	}
            	}
                break;
            case "3":
            	//back
            	displayMainMenu();
                break;
            case "e":
            case "E":
            	//exit
            	input.close();
            	System.out.println("Bye!");
                return;
        }
	}
	
	
	/**
	 * Display the menu for create or upload a test
	 * @throws IOException
	 */
	public static void displayCreateLoadTestMenu() throws IOException {
		Output.Menu("createLoadTest");
        String choice = input.readLine();
        
        //Check for invalid input
        while  (inputNotInRange(choice, 3) && !choice.equals("E") && !choice.equals("e")) {
        	System.out.println("Invalid input. Please choose again:");
        	choice = input.readLine();
        }

        //Ask user to create or load a test
        switch (choice) {
            case "1":
            	//create a new test
            	String name = Output.getName(QuestionairType.Test);
            	Test t = new Test(name);
            	if(Saving.SaveTest(t, filePath + name, false)) {
            		displayQuestionTypeTest(t);
            	}else {
            		displayCreateLoadTestMenu();
            	}
                break;
            case "2":
            	//load a test
            	List<File> files = Saving.LoadFile(filePath, ".test", QuestionairType.Test);
            	if(files != null) {
	            	int index = Output.loadFileMenu(files);
	            	Test t1 = Saving.OpenTest(files.get(index- 1));
	            	if(t1 != null) {
	            		displayDetailedTestMenu(t1);
	            	}else {
	            		displayCreateLoadTestMenu();
	            	}
            	}
                break;
            case "3":
            	//back
            	displayMainMenu();
                break;
            case "e":
            case "E":
            	//exit
            	input.close();
            	System.out.println("Bye!");
                return;
        }
	}
	
	
	/**
	 * Ask the user what to do with the current survey
	 * @param s    A survey
	 * @throws IOException
	 */
	public static void displayDetailedSurveyMenu(Survey s) throws IOException {
		Output.Menu("detailSurvey");
        String choice = input.readLine();

		//Check for invalid input
        while  (inputNotInRange(choice, 6) && !choice.equals("E") && !choice.equals("e")) {
        	System.out.println("Invalid input. Please choose again:");
        	choice = input.readLine();
        }
        
        //Display different choices for survey
        switch (choice) {
            case "1":
            	//display survey
            	s.Display();
            	displayDetailedSurveyMenu(s);
                break;
            case "2":
            	//save survey
            	String name = Output.getName(QuestionairType.Survey);
            	s.setName(name);
            	Saving.SaveSurvey(s, filePath+ name, false);
            	displayDetailedSurveyMenu(s);
                break;
            case "3":
            	//modify survey
            	s.Modify();
            	Saving.SaveSurvey(s, filePath+ s.getName(), true);
            	displayDetailedSurveyMenu(s);
                break;
            case "4":
            	//take survey
            	s.Take();
            	Saving.SaveSurvey(s, filePath+ s.getName(), true);
            	displayDetailedSurveyMenu(s);
                break;
            case "5":
            	//tabulate a survey
            	s.Tabulate();
            	displayDetailedSurveyMenu(s);
                break;
            case "6":
            	//back
            	displayCreateLoadSurveyMenu();
                break;
            case "e":
            case "E":
            	//exit
            	input.close();
            	Saving.SaveSurvey(s, filePath+ s.getName(), true);
            	System.out.println("Bye!");
                return;
        }
	}
	
	
	/**
	 * Ask the user what to do with the current test file
	 * @param t    a test
	 * @throws IOException
	 */
	public static void displayDetailedTestMenu(Test t) throws IOException {
		Output.Menu("detailTest");
        String choice = input.readLine();

		//Check for invalid input
        while  (inputNotInRange(choice, 7) && !choice.equals("E") && !choice.equals("e")) {
        	System.out.println("Invalid input. Please choose again:");
        	choice = input.readLine();
        }
        
        //Display different choices for survey
        switch (choice) {
            case "1":
            	//display test
            	t.Display();
            	displayDetailedTestMenu(t);
                break;
            case "2":
            	//save test
            	String name = Output.getName(QuestionairType.Test);
            	t.setName(name);
            	Saving.SaveTest(t, filePath+ name, false);
            	displayDetailedTestMenu(t);
                break;
            case "3":
            	//modify test
            	int qIndex = t.Modify();
            	Question q = t.getQuestionWithIndex(qIndex);
            	t.ModifyCorrectAnswer(q, qIndex);
            	Saving.SaveTest(t, filePath+ t.getName(), true);
            	displayDetailedTestMenu(t);
                break;
            case "4":
            	//take test
            	t.Take();
            	Saving.SaveTest(t, filePath+ t.getName(), true);
            	displayDetailedTestMenu(t);
                break;
            case "5":
            	//grade test
            	t.Grade();
            	Saving.SaveTest(t, filePath+ t.getName(), true);
            	displayDetailedTestMenu(t);
                break;
            case "6":
            	//tabulate test
            	t.Tabulate();
            	displayDetailedTestMenu(t);
                break;
            case "7":
            	//back
            	displayCreateLoadTestMenu();
                break;
            case "e":
            case "E":
            	//exit
            	input.close();
            	Saving.SaveTest(t, filePath+ t.getName(), true);
            	System.out.println("Bye!");
                return;
        }
	}
	
	
	/**
	 * Ask the user which kind of question to add to the survey
	 * @param s    the survey
	 * @throws IOException
	 */
	public static void displayQuestionTypeSurvey(Survey s) throws IOException {
		Output.Menu("questionToAdd");
        String choice = input.readLine();

		//Check for invalid input
        while  (inputNotInRange(choice, 7) && !choice.equals("E") && !choice.equals("e")) {
        	System.out.println("Invalid input. Please choose again:");
        	choice = input.readLine();
        }
        
        //Display different choices for survey
        switch (choice) {
            case "1":
            	//Add a T/F question
            	s.addNewQuestion(QuestionType.TrueFalse);
            	Saving.SaveSurvey(s, filePath+ s.getName(), true);
                displayQuestionTypeSurvey(s);
                break;
            case "2":
            	//Add a mult choice question
            	s.addNewQuestion(QuestionType.MultChoice);
            	Saving.SaveSurvey(s, filePath+ s.getName(), true);
                displayQuestionTypeSurvey(s);
                break;
            case "3":
            	//Add a short Ans question
            	s.addNewQuestion(QuestionType.ShortAns);
            	Saving.SaveSurvey(s, filePath+ s.getName(), true);
                displayQuestionTypeSurvey(s);
                break;
            case "4":
            	//Add an essay
            	s.addNewQuestion(QuestionType.Essay);
            	Saving.SaveSurvey(s, filePath+ s.getName(), true);
                displayQuestionTypeSurvey(s);
                break;
            case "5":
            	//Add a ranking question
            	s.addNewQuestion(QuestionType.Ranking);
            	Saving.SaveSurvey(s, filePath+ s.getName(), true);
                displayQuestionTypeSurvey(s);
                break;
            case "6":
            	//Add a matching matching
            	s.addNewQuestion(QuestionType.Matching);
            	Saving.SaveSurvey(s, filePath+ s.getName(), true);
                displayQuestionTypeSurvey(s);
                break;
            case "7":
            	//back
            	displayCreateLoadSurveyMenu();
                break;
            case "e":
            case "E":
            	//exit
            	input.close();
            	System.out.println("Bye!");
                return;
        }
	}
	
	
	/**
	 * Ask the user which kind of question to add to the test
	 * @param t    the test
	 * @throws IOException
	 */
	public static void displayQuestionTypeTest(Test t) throws IOException {
		Output.Menu("questionToAdd");
        String choice = input.readLine();

		//Check for invalid input
        while  (inputNotInRange(choice, 7) && !choice.equals("E") && !choice.equals("e")) {
        	System.out.println("Invalid input. Please choose again:");
        	choice = input.readLine();
        }
        
        //Display different choices for survey
        switch (choice) {
            case "1":
            	//Add a T/F question
            	TrueFalse tf = (TrueFalse) t.addNewQuestion(QuestionType.TrueFalse);
        		t.addRightAnswer(tf, null);
        		Saving.SaveTest(t, filePath + t.getName(), true);
                displayQuestionTypeTest(t);
                break;
            case "2":
            	//Add a mult choice question
            	MultChoice mt = (MultChoice) t.addNewQuestion(QuestionType.MultChoice);
        		t.addRightAnswer(mt, null);
        		Saving.SaveTest(t, filePath+ t.getName(), true);
                displayQuestionTypeTest(t);
                break;
            case "3":
            	//Add a short Ans question
            	ShortAns sa = (ShortAns) t.addNewQuestion(QuestionType.ShortAns);
        		t.addRightAnswer(sa, null);
        		Saving.SaveTest(t, filePath+ t.getName(), true);
                displayQuestionTypeTest(t);
                break;
            case "4":
            	//Add an essay
            	Essay es = (Essay) t.addNewQuestion(QuestionType.Essay);
        		//No actual answer is added(no user input. Just taking a space on the answersheet)
        		t.addRightAnswer(es, null);
        		Saving.SaveTest(t, filePath+ t.getName(), true);
                displayQuestionTypeTest(t);
                break;
            case "5":
            	//Add a ranking question
            	Ranking rk = (Ranking) t.addNewQuestion(QuestionType.Ranking);
        		t.addRightAnswer(rk, null);
        		Saving.SaveTest(t, filePath+ t.getName(), true);
                displayQuestionTypeTest(t);
                break;
            case "6":
            	//Add a matching matching
            	Matching ma = (Matching) t.addNewQuestion(QuestionType.Matching);
        		t.addRightAnswer(ma, null);
        		Saving.SaveTest(t, filePath+ t.getName(), true);
                displayQuestionTypeTest(t);
                break;
            case "7":
            	//back
            	displayCreateLoadTestMenu();
                break;
            case "e":
            case "E":
            	//exit
            	input.close();
            	System.out.println("Bye!");
                return;
        }
	}
	
	
	
	/**
	 * Check if the input is a number in a specific range of 1 to n
	 * @param in    input number in String format
	 * @param n   the range to check [1, n]
	 * @return   true if in range, false otherwise
	 */
	public static boolean inputNotInRange(String in, Integer n) {
		try {
			int input = Integer.parseInt(in);
			if(input >= 1 && input <= n) {
				return false;
			}
			else {
				return true;
			}
		}catch(NumberFormatException  e) {
			return true;
		}
	}
}
