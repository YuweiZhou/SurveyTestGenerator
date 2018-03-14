import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Extends essay, Question
 * @author Yuwei Zhou
 *
 */
@SuppressWarnings("serial")
public class ShortAns extends Essay{
	private transient static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private int maxLength;
	
	
	/**
	 * Constructor
	 */
	public ShortAns() {
		super();
		questiontype = QuestionType.ShortAns;
		maxLength = 10;
	}
	
	
	/**
	 * Set the maximum length of the short answer
	 * @param l   length
	 */
	public void setMaxLength(int l) {
		maxLength = l;
	}
	
	
	/**
	 * get the maximum length of the short answer
	 * @return
	 */
	public int getMaxLength() {
		return maxLength;
	}
	
	
	/**
	 * Prompt the user for the question and and desired max length of answer
	 * @throws IOException
	 */
	@Override
	public void promptForQuestion() throws IOException {
		System.out.println("Enter the prompt of your short answer question: ");
		String inputQuestion = Output.getNotNullInput(input);
        question = inputQuestion + "\n";
        System.out.println("Enter the maximum characters allowed for each answer: ");
        maxLength = Output.getNextInt(input, 1, 500);
        System.out.println("How many answers do you want this short answer question to have?");
        expectedNumOfAns = Output.getNextInt(input, 1, 10000);
	}
	
	
	/**
	 * Modify the short answer question
	 */
	public void modify() throws IOException{
		System.out.println(question);
		System.out.println("Do you wish to modify the prompt?");
		boolean modP = Output.getYesNo(input);
		if(modP) {
			System.out.println("Enter a new prompt:");
			question = Output.getNotNullInput(input);
		}
		System.out.println("The maximum length of the answer now is: " + maxLength + ". Do you want to change it?");
		if(Output.getYesNo(input)) {
			System.out.println("Enter a length:");
			setMaxLength(Output.getNextInt(input, 1, 10000));
		}
	}
}
