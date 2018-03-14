import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SuppressWarnings("serial")
public abstract class Question implements java.io.Serializable{

	private transient BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	public QuestionType questiontype;
	protected String question;
	protected int expectedNumOfAns;
	
	/**
	 * Constructor
	 */
	public Question() {
		question = "";
	}
	
	
	/**
	 * @return the question in string
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * Set the question 
	 * @param q    the question
	 */
	public void setQuestion(String q) {
		question = q;
	}
	
	/**
	 * An genral method to modify different types of questions' prompt
	 * @throws IOException 
	 */
	public void modify() throws IOException{
		System.out.println(question);
		System.out.println("Do you wish to modify the prompt?");
		boolean modP = Output.getYesNo(input);
		if(modP) {
			System.out.println("Enter a new prompt:");
			question = Output.getNotNullInput(input);
		}
	}
	
	
	/**
	 * print the question
	 */
	public void Display() {
		System.out.println(question);
		System.out.println();
	}
	

	/**
	 * Prompyt the user for question
	 * @param t  question type
	 * @throws IOException
	 */
	public abstract void promptForQuestion() throws IOException;
}
