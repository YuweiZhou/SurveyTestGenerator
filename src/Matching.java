import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Extends Question Class
 * @author Yuwei Zhou
 *
 */
@SuppressWarnings("serial")
public class Matching extends Question{
	private transient static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	public List<String> qChoicesLeft = new ArrayList<String>();
	public List<String> qChoicesRight = new ArrayList<String>();
	
	/**
	 * Constructor
	 */
	public Matching() {
		super();
		questiontype = QuestionType.Matching;
	}

	/**
	 * As the user for the question, the choices on the left side and the choices of the right side
	 * @throws IOException
	 */
	@Override
	public void promptForQuestion() throws IOException {
		System.out.println("Enter the prompt of your matching question:");
        String inputQuestion = Output.getNotNullInput(input);
        question = inputQuestion + "\n";
        System.out.println("Enter the number(up to 10) of pairs of choices you want to match:");
        int numOfChoices = Output.getNextInt(input, 1, 10); //Accept up to 10 choices
        expectedNumOfAns = numOfChoices;
        //For the left side 
        System.out.println("Please enter all the choices for the left side:");
        for (int i = 0; i< numOfChoices; i++) {
        	System.out.println("Enter item #" +(i+1) + " (" + Output.getUpperCaseLetterFromNumber(i+1) + ")");
        	String choice = Output.getNotNullInput(input);
        	qChoicesLeft.add(choice); 
        }
        
        //For the right side 
        System.out.println("Please enter all the choices for the right side:");
        for (int i = 0; i< numOfChoices; i++) {
        	System.out.println("Enter item #" + (i+1));
        	String choice = Output.getNotNullInput(input);
        	qChoicesRight.add(choice); 
        }
	}
	
	/**
	 * Return the number of items in the qChoicesLeft
	 * @return the desired number of answers
	 */
	public int getNumOfChoices() {
		return qChoicesLeft.size();
	}
	
	/**
	 * Display the matching question
	 */
	@Override
	public void Display() {
		System.out.println(question);
		for (int i = 0; i< getNumOfChoices(); i++) {
			System.out.print(Output.getUpperCaseLetterFromNumber(i+1) + ") " + qChoicesLeft.get(i) + "\t\t"
        			+ (i+1) + ") " + qChoicesRight.get(i) + "\n");
		}
	}
	

	/**
	 * Modify the matching question
	 */
	@Override
	public void modify() throws IOException {
		System.out.println(question);
		System.out.println("Do you wish to modify the prompt?");
		boolean modP = Output.getYesNo(input);
		if(modP) {
			System.out.println("Enter a new prompt:");
			question = Output.getNotNullInput(input);
		}
		System.out.println("Do you wish to modify choices?");
		boolean modC = Output.getYesNo(input);
		if(modC) {
			System.out.println("Do you want to choices on the left or right? Please enter [l] for left or [r] for right:");
			for (int i = 0; i< qChoicesRight.size(); i++) {
	        	System.out.println(Output.getUpperCaseLetterFromNumber(i+1) + ") " + qChoicesLeft.get(i) + "\t\t"
	        			+ (i+1) + ") " + qChoicesRight.get(i) + "\n");
	        }  
			String leftRight = Output.getNotNullInput(input);
			while(!leftRight.equals("l") && !leftRight.equals("r")) {
				System.out.println("Please enter [l] or [r]");
				leftRight = Output.getNotNullInput(input);
			}
			if(leftRight.equals("l")) {
				System.out.println("Please enter the corresponding letter of choice you want to modify: ");
				int letterIndex = Output.getLetterInRange(input, qChoicesLeft.size());
				System.out.println("Please enter the new choice: ");
				String newC = Output.getNotNullInput(input);
				qChoicesLeft.set(letterIndex - 1, newC); 
			}else {
				System.out.println("Please enter the corresponding number index of choice you want to modify: ");
				int index = Output.getNextInt(input, 1, qChoicesRight.size());
				System.out.println("Please enter the new choice: ");
				String newC = Output.getNotNullInput(input);
				qChoicesRight.set(index - 1, newC); 
			}
		}
	}
}
