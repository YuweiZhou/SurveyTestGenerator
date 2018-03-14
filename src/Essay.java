import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * extends Question class
 * @author Yuwei 
 *
 */
@SuppressWarnings("serial")
public class Essay extends Question{
	private transient static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	public Essay() {
		super();
		questiontype = QuestionType.Essay;
	}
	
	@Override
	public void promptForQuestion() throws IOException {
		System.out.println("Enter the requirement of your essay:");
		String inputQuestion = Output.getNotNullInput(input);
        question = inputQuestion + "\n";
        System.out.println("How many paragraphs do you want the essay to have?");
        expectedNumOfAns = Output.getNextInt(input, 1, 10000);
	}
}
