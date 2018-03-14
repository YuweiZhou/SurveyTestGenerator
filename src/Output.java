import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class that contains all the frequently used text outputs
 * @author Yuwei Zhou
 *
 */
public class Output {
	
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * The main menus
	 * @param menu
	 */
    public static void Menu(String menu)
    {
        switch (menu) {
            case "main":
                System.out.println();
                System.out.println("1) Survey Menu.");
                System.out.println("2) Test Menu.");
                System.out.println("E) Exit.");
                break;
            case "createLoadSurvey":
            	System.out.println();
                System.out.println("1) Create a new Survey."); 
                System.out.println("2) Load a Survey."); 
                System.out.println("3) Back.");
                System.out.println("E) Exit."); //e, E, Exit, exit will work
                break;
            case "createLoadTest":
                System.out.println("1) Create a new Test.");
                System.out.println("2) Load a Test.");
                System.out.println("3) Back.");
                System.out.println("E) Exit"); //e, E, will work
                break;
            case "detailSurvey":
                System.out.println("1) Display Survey."); 
                System.out.println("2) Save Survey."); 
                System.out.println("3) Modify Survey."); 
                System.out.println("4) Take Survey."); 
                System.out.println("5) Tabulate Survey."); 
                System.out.println("6) Back.");
                System.out.println("E) Exit."); //e, E will work
                break;
            case "detailTest":
                System.out.println("1) Display Test.");
                System.out.println("2) Save a Test.");
                System.out.println("3) Modify a Test."); 
                System.out.println("4) Take a Test."); 
                System.out.println("5) Grade a Test."); 
                System.out.println("6) Tabulate Test."); 
                System.out.println("7) Back.");
                System.out.println("E) Exit"); //e, E will work
                break;
            case "questionToAdd":
            	System.out.println("\n\nPlease choose from below:");
                System.out.println("1) Add a new T/F question");
                System.out.println("2) Add a new multiple choice question");
                System.out.println("3) Add a new short answer question"); 
                System.out.println("4) Add a new essay question"); 
                System.out.println("5) Add a new ranking question");
                System.out.println("6) Add a new matching question");
                System.out.println("7) Back.");
                System.out.println("E) Exit"); //e, E will work
                break;
        }
    }
    
    /**
     * Get the full name of a tyoe of question given the enum question type
     * @param qt  Question type
     * @return
     */
    public static String getFullNameByQuestionType(QuestionType qt) {
    	switch(qt) {
    		case Essay:
    			return "essay";
    		case MultChoice:
    			return "multiple choice question";
    		case Matching:
    			return "matching question";
    		case Ranking:
    			return "ranking question";
    		case ShortAns:
    			return "short answer question";
    		case TrueFalse: 
    			return "true false question";
    		default:
    			return "";
    	}
    }
    
    
    /**
     * get the ith Upper case letter in the alphabet
     * @param i  a number less or equal to 10
     * @return
     */
    static String getUpperCaseLetterFromNumber(int i) {
		switch(i) {
			case 1:
				return "A";
			case 2:
				return "B";
			case 3:
				return "C";
			case 4:
				return "D";
			case 5:
				return "E";
			case 6:
				return "F";
			case 7:
				return "G";
			case 8:
				return "H";
			case 9:
				return "I";
			case 10:
				return "J";
			default:
				return "";
		}
				
	}
    
    /**
     * get the index of a letter in the alphabet
     * @param letter  A letter from A to J
     * @return the index
     */
    static int getNumberFromLetter(String letter) {
		switch(letter) {
			case "A":
			case "a":
				return 1;
			case "B":
			case "b":
				return 2;
			case "C":
			case "c":
				return 3;
			case "D":
			case "d":
				return 4;
			case "E":
			case "e":
				return 5;
			case "F":
			case "f":
				return 6;
			case "G":
			case "g":
				return 7;
			case "H":
			case "h":
				return 8;
			case "I":
			case "i":
				return 9;
			case "J":
			case "j":
				return 10;
			default:
				return 0;
		}
				
	}
    
    
    /**
     * get the ith lower case letter in the alphabet
     * @param i
     * @return
     */
    static String getLowerCaseLetterFromNumber(int i) {
		switch(i) {
			case 1:
				return "a";
			case 2:
				return "b";
			case 3:
				return "c";
			case 4:
				return "d";
			case 5:
				return "e";
			case 6:
				return "f";
			case 7:
				return "g";
			case 8:
				return "h";
			case 9:
				return "i";
			case 10:
				return "j";
			default:
				return "";
		}
				
	}
    
    
    /**
     * Check wether the input letter is the first ith letters in the alphabet
     * @param letter   the letter to check 
     * @param num    range to check [1, num]
     * @return    true if in the range, false otherwise
     */
    static boolean letterInRange(String letter, int num) {
    	List<String> allLettersInRange = new ArrayList<String>();
    	for(int i =0; i< num; i++) {
    		allLettersInRange.add(getUpperCaseLetterFromNumber(i+1));
    		allLettersInRange.add(getLowerCaseLetterFromNumber(i+1));
    	}
		
    	if(allLettersInRange.contains(letter)) {
    		return true;
    	}else {
    		return false;
    	}
	}
    
    
    /**
     * Prompt user for the name of the survey or file
     * @param type 
     * @return  the name
     * @throws IOException
     */
    public static String getName(QuestionairType t) throws IOException {
    	if(t.equals(QuestionairType.Survey)) {
    		System.out.println("\n\nPlease enter a name for your survey:");
    	}else {
    		System.out.println("\n\nPlease enter a name for your test:");
    	}
    	
        String name = input.readLine();
        return name;
    }
    
    
    /**
     * File saved message
     */
    public static void filesSaved()
    {
        System.out.println("\n\nFiles has been saved.");
    }
    
    
    /**
     * File loaded message
     */
    public static void filesUploaded()
    {
        System.out.println("\n\nFiles loaded successfully.");
    }

    
    /**
     * Invalid input warning
     */
    public static void invalidInputWarning() {
        System.out.println("\n\nSorry, input is invalid. Please input in the indicated format."); 
    }
    
    
    /**
     * Display the loaded file names and prompt the user for the file the user wants to load
     * @param fileNames  A  list of files found
     * @return   the index of the file the user wants to upload
     * @throws IOException
     */
    public static int loadFileMenu(List<File> fileNames) throws IOException
    {
    	System.out.println("\n\nPlease choose a file to load:");
        for(int i = 0; i< fileNames.size(); i++) {
        	System.out.println( (i+1) + ") " + fileNames.get(i).getName());
        }
        int choice = Output.getNextInt(input, 1, fileNames.size());

        return choice;
    }
    
    
    /**
     * Gets the next integer input from console
     * @param input  bufferred reader
     * @param lowerBound   the lower bound of the desired range of input integer
     * @param upperBound  the upper bound of the desired range of input intege
     * @return
     * @throws IOException
     */
    public static int getNextInt(BufferedReader input, int lowerBound, int upperBound) throws IOException {
    	int x;
    	try {
    		x = Integer.parseInt(input.readLine());
    	}catch(NumberFormatException  e) {
    		System.out.println("Invalid input: Please input an integer between " + lowerBound + " and " + upperBound);
    		x = Output.getNextInt(input, lowerBound, upperBound);
    	}
    	
    	if (x < lowerBound || x > upperBound) {
        	System.out.println("Invalid input. Please input a number between " + lowerBound + " and " + upperBound);
        	x = Output.getNextInt(input, lowerBound, upperBound);
        }
    	
    	return x;
    }
    
    
    /**
     * Get a not null user input
     * @param input  buffer reader
     * @return  the input string
     * @throws IOException
     */
    public static String getNotNullInput(BufferedReader input) throws IOException{
    	String inputQuestion = input.readLine();
        while(inputQuestion.equals("") || inputQuestion == null) {
        	System.out.println("Invalid input. Input cannot be null.");
        	inputQuestion = input.readLine();
        }
        return inputQuestion;
    }
    
    
    /**
     * Get a letter in a number range
     * @param input  buffer reader, and the range [1, size]
     * @return  the index of the letter
     * @throws IOException
     */
    public static int getLetterInRange(BufferedReader input, int size) throws IOException{
    	String letter = getNotNullInput(input);
		while(! Output.letterInRange(letter, size)) {
			System.out.println("Invalid input. Plese input a letter in range");
			letter = getNotNullInput(input);
		}
        return getNumberFromLetter(letter);
    }
    
    
    
    /**
     * Get a yes or no answer
     * @param input  buffer reader
     * @return  the true if the answer is yea and false if the answer is no
     * @throws IOException
     */
    public static boolean getYesNo(BufferedReader input) throws IOException{
    	String inputQuestion = getNotNullInput(input);
        while(!inputQuestion.equals("Yes") && !inputQuestion.equals("No") &&
        		!inputQuestion.equals("yes") && !inputQuestion.equals("no") &&
        		!inputQuestion.equals("y") && !inputQuestion.equals("n")) {
        	System.out.println("Invalid input. Plese input either [Yes/yes/y] for Yes, or [No/no/n] for No");
        	inputQuestion = getNotNullInput(input);
        }
        if(inputQuestion.equals("Yes") || inputQuestion.equals("yes") || inputQuestion.equals("y")) {
        	return true;
        }else {
        	return false;
        }
    }

}
