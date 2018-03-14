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
public class MultChoice extends Question{

	private transient static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	private List<String> qChoices = new ArrayList<String>();

	/**
	 * Constructor
	 */
	public MultChoice() {
		super();
		questiontype = QuestionType.MultChoice;
	}
	
	
	/**
	 * Ask the user for the question and all choices
	 * @throws IOException
	 */
	@Override
	public void promptForQuestion() throws IOException {
		System.out.println("Enter the prompt of your multiple choice question:");
		String inputQuestion = Output.getNotNullInput(input);
        question = inputQuestion + "\n";
        System.out.println("Enter the number(up to 10) of choices for your multiple choice question:");
        int numOfChoices = Output.getNextInt(input, 1, 10); //Accept up to 10 choices
        
        for (int i = 0; i< numOfChoices; i++) {
        	System.out.println("Enter choice #" + (i+1) + " (" + Output.getUpperCaseLetterFromNumber(i+1) + ")");
        	String choice = Output.getNotNullInput(input);
        	addNewChoice(choice); 
        }
        
        System.out.println("How many answers do you want this multiple choice to have?");
        expectedNumOfAns = Output.getNextInt(input, 1, getNumOfChoices());
	}
	
	
	/**
	 * Display the multiple choice question
	 */
	@Override
	public void Display() {
		System.out.println(question);
		for (int i = 0; i< getNumOfChoices(); i++) {
			System.out.print(Output.getUpperCaseLetterFromNumber(i+1) + ") " + qChoices.get(i) + "\t\t");
		}
		System.out.println("\n");
	}
	
	/**
	 * Return the number of items in the qChoices
	 * @return the desired number of answers
	 */
	public int getNumOfChoices() {
		return qChoices.size();
	}
	
	
	/**
	 * Add a new choice to the list of choices
	 * @param ans
	 */
	public void addNewChoice(String ans) {
		qChoices.add(ans);
	}


	/**
	 * Modify the multiple choice question
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
			System.out.println("Which choice do you want to modify?");
			for (int i = 0; i< qChoices.size(); i++) {
				System.out.print(Output.getUpperCaseLetterFromNumber(i+ 1) + ") " + qChoices.get(i) + "\t");
			}
			int index = Output.getLetterInRange(input, qChoices.size()) - 1;
			System.out.println("Please enter new choice:");
			String newC = Output.getNotNullInput(input);
			qChoices.set(index, newC);
		}
	}

}
