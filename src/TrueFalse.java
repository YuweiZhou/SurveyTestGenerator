import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Extends Question
 * @author Yuwei Zhou
 *
 */
@SuppressWarnings("serial")
public class TrueFalse extends MultChoice{

	private transient static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Constructor
	 */
	public TrueFalse() {
		super();
		questiontype = QuestionType.TrueFalse;
	}
	
	
	/**
	 * Prompt the user for the question
	 */
	@Override
	public void promptForQuestion() throws IOException {
		System.out.println("Enter the prompt of your T/F question:");
		String inputQuestion = Output.getNotNullInput(input);
        question = inputQuestion + "\n";
        addNewChoice("True");
        addNewChoice("False");
        expectedNumOfAns = 1;
	}
	
	
	/**
	 * Modify a true false question
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
	}
}
