import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Extends Matching and Question
 * @author Yuwei Zhou
 *
 */
@SuppressWarnings("serial")
public class Ranking extends Matching{
	private transient static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Constructor
	 */
	public Ranking() {
		super();
		questiontype = QuestionType.Ranking;
	}
	
	/**
	 * modify question
	 */
	//@Override
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
			System.out.println("Please enter the corresponding number index of choice you want to modify: ");
			int index = Output.getNextInt(input, 1, qChoicesLeft.size());
			System.out.println("Please enter the new choice: ");
			String newC = Output.getNotNullInput(input);
			qChoicesLeft.set(index - 1, newC); 
		}
	}
	
	/**
	 * Prompt the user for the question and items to be ranked
	 */
	@Override
	public void promptForQuestion() throws IOException {
		System.out.println("Enter the prompt of your ranking question:");
		String inputQuestion = Output.getNotNullInput(input);
        question = inputQuestion + "\n";
        System.out.println("Enter the number(up to 10) of items you want to rank:");
        int numOfChoices = Output.getNextInt(input, 1, 10); //Accept up to 10 choices
        expectedNumOfAns = numOfChoices;
        //For the ranking items  
        System.out.println("Please enter all the items you want to rank:");
        for (int i = 0; i< numOfChoices; i++) {
        	System.out.println("Enter item #" + (i+1));
        	String choice = Output.getNotNullInput(input);
        	qChoicesLeft.add( choice); 
        }
        
       //For the right side 
        for (int i = 0; i< numOfChoices; i++) {
        	qChoicesRight.add((i+1) + ""); 
        }
	}
	
	/**
	 * Display the ranking question
	 */
	@Override
	public void Display() {
		System.out.println(question);
		for (int i = 0; i< getNumOfChoices(); i++) {
			System.out.print((i+1) + ") " + qChoicesLeft.get(i)+ "\n");
		}
	}
}
