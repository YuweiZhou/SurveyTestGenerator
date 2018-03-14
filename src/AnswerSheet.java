import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Yuwei Zhou
 * Answer sheet that contains a list of answers.
 * Each answer is consist of a list of strings
 * An answer sheet can be either a correct answer sheet or user answer sheet
 */
@SuppressWarnings("serial")
public class AnswerSheet implements java.io.Serializable{
	public boolean isCorrectAnswer;
	public List<List<String>> answers = new ArrayList<List<String>>(); 
	private String userName; //the name of the user who take the survey
	
	
	
	public AnswerSheet() {
		userName = "";
	}

	
	/**
	 * Getter for user name
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Setter for user name
	 */
	public void setUserName(String name) {
		userName = name;
	}
	
	/**
	 * Add an answer to the answer sheet
	 * @param a
	 * @param isCA
	 */
	public void addAnswer(List<String> a, boolean isCA, Integer index) {
		if(index == null || !isCA) {
			answers.add(a);
		}else {
			if(answers.get(index) != null) {
				answers.set(index, a);
			}else {
				answers.add(a);
			}
		}
		isCorrectAnswer = isCA;
	}

	
	/**
	 * @return the answer sheet
	 */
	public List<List<String>> getAnswerSheet() {
		return answers;
	}
	
	/**
	 * @param x   index
	 * @return the xth answer in the answer sheet
	 */
	public String getAnswerAtIndex(int x) {
		String finalAns = "";
		try {
			List<String> tempA = answers.get(x);
			for (int i =0; i< tempA.size(); i++) {
				if(i < tempA.size() - 1) {
					finalAns += tempA.get(i) + "\n";
				}else {
					finalAns += tempA.get(i);
				}
			}
		}catch(IndexOutOfBoundsException e) {
			return "";
		}catch(NullPointerException e) {
			return "";
		}
		return finalAns;
	}
	
	
	/**
	 * Get the answers at an index in the List of Strings format
	 * @param x  the index
	 * @return a list of Strings
	 */
	public List<String> getListAnswerAtIndex(int x) {
		return answers.get(x);
	}
}
