import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Extends Survey Class
 * Contains a correct answer sheet
 * @author Yuwei Zhou
 *
 */
@SuppressWarnings("serial")
public class Test extends Survey implements java.io.Serializable{
	private transient static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	public QuestionairType questionairType = QuestionairType.Test;
	//correct answer sheet
	private AnswerSheet casheet = new AnswerSheet();
	
	
	/**
	 * Constructor
	 */

	public Test(String surveyName) {
		super(surveyName);
	}
	
	
	/**
	 * Display a test. 
	 * Display both the qustion and the right answer
	 */
	public void Display() {
		System.out.println("name: " + getName());
		for(int i =0; i< mySurvey.size(); i++) {
			Question q = mySurvey.get(i);
			System.out.println( (i+1) + ") ");
			q.Display();
			if(!q.questiontype.equals(QuestionType.Essay)) {
				System.out.println("Correct answer: ");
				System.out.println(casheet.getAnswerAtIndex(i));
			}
			System.out.println("\n\n");
		}
	}
	
	
	/**
	 * Grade an answer sheet
	 * @throws IOException 
	 */
	public void Grade() throws IOException {
		if(asheets.size() > 0) {
			System.out.println("Whose answer sheet below do you want to grade on?");
			for(int i = 0; i< asheets.size(); i++) {
				System.out.println((i+1)+ ") " + asheets.get(i).getUserName());
			}
			int userIndex = Output.getNextInt(input, 1, asheets.size());
			AnswerSheet userAS = asheets.get(userIndex - 1);
			//now grade the test 
			int userGrade = 0;
			int totalGrade = 0;
			for(int i = 0; i< userAS.answers.size(); i++) {
				List<String> correctAs = casheet.getListAnswerAtIndex(i);
				List<String> userAs = userAS.getListAnswerAtIndex(i);
				List<String> temp = new ArrayList<String>();
				temp.add("");
				if(!correctAs.equals(temp)) {
					//this is not an essay
					totalGrade += 10;
					if(correctAs.equals(userAs)) {
						userGrade += 10;
					}
				}
			}
			String grade =  userGrade + "/" + totalGrade;			
			System.out.println("The grade of this test is: " + grade);
		}else {
			System.out.println("There is no answer sheet you can grade on");
			return;
		}
	}
	
	
	/**
	 * Modify correct answer
	 * @throws IOException 
	 */
	public void ModifyCorrectAnswer(Question q, int qId) throws IOException {
		System.out.println("Do you wish to modify the correct answer?");
		boolean modA = Output.getYesNo(input);
		if(modA) {
			if(q.questiontype.equals(QuestionType.MultChoice)) {
				System.out.println("This question can have " + q.expectedNumOfAns + " answers. Do you want to change it?");
				if(Output.getYesNo(input)) {
					System.out.println("Please enter the numer of answers this question can have:");
					q.expectedNumOfAns = Output.getNextInt(input, 1, ((MultChoice) q).getNumOfChoices());
				}
			}else if( q.questiontype.equals(QuestionType.ShortAns)){
				System.out.println("This question can have " + q.expectedNumOfAns + " answers. Do you want to change it?");
				if(Output.getYesNo(input)) {
					System.out.println("Please enter the numer of answers this question can have:");
					q.expectedNumOfAns = Output.getNextInt(input, 1, 10000);
				}
			}else if(q.questiontype.equals(QuestionType.Essay)) {
				System.out.println("This essay can have " + q.expectedNumOfAns + " paragraphs. Do you want to change it?");
				if(Output.getYesNo(input)) {
					System.out.println("Please enter the numer of paragraphs this question can have:");
					q.expectedNumOfAns = Output.getNextInt(input, 1, 10000);
				}
			}
			addRightAnswer(q, qId);
		}
	}
	
	
	
	/**
	 * Prompt the user for the right answer to a question and add it to the correct answer sheet
	 * @param q   the corresponding question
	 * @throws IOException
	 */
	public void addRightAnswer(Question q, Integer index) throws IOException {
		int numOfAnswers = q.expectedNumOfAns;
		List<String> allAnswers = new ArrayList<String>();
		
		if(q.questiontype.equals(QuestionType.MultChoice)) {
	        System.out.println("Please enter the corresponding letter of the right choices");
		}else if(q.questiontype.equals(QuestionType.ShortAns)) {
	        System.out.println("Please enter the answer(s):");
		}else if(q.questiontype.equals(QuestionType.Matching)) {
			System.out.println("Please enter " + numOfAnswers + " pairs of correct answers of your question in the format of \"Letter Number\"");
		}
		else if(q.questiontype.equals(QuestionType.Ranking)) {
			System.out.println("Please enter the numbers one by one in the right order:");
		}
		else if(q.questiontype.equals(QuestionType.TrueFalse)) {
			System.out.println("Please enter A for True or B for False");
		}
		else {
			//Essay. No answer needed. Just to take a space so the list of questions and answers are paired up
			allAnswers.add("");
			casheet.addAnswer(allAnswers, true, index);
			return;
		}
        
		//Get the list of answers one by one
        for (int i = 0; i< numOfAnswers; i++) {
        	if(numOfAnswers > 1) {
        		System.out.println("Enter answer # " + (i+1));
        	}else {
        		System.out.println("Enter answer: ");
        	}
        	
        	String answer = Output.getNotNullInput(input);
        	if(q.questiontype.equals(QuestionType.MultChoice)|| q.questiontype.equals(QuestionType.Matching)) {
        		answer = answer.toUpperCase();
        	}else if(q.questiontype.equals(QuestionType.ShortAns)) {
        		answer = answer.toLowerCase();
        	}else if(q.questiontype.equals(QuestionType.TrueFalse)) {
        		if (answer.equals("a") || answer.equals("A")) {
        			answer = "True";
        		}else if(answer.equals("b") || answer.equals("B")){
        			answer = "False";
        		}
        	}

        	//Check if the answer is valid
        	while(!checkAnswerValid(answer, q)) {
        		Output.invalidInputWarning();
        		answer = Output.getNotNullInput(input);
        		if(q.questiontype.equals(QuestionType.MultChoice)|| q.questiontype.equals(QuestionType.Matching)) {
            		answer = answer.toUpperCase();
            	}else if(q.questiontype.equals(QuestionType.ShortAns)) {
            		answer = answer.toLowerCase();
            	}else if(q.questiontype.equals(QuestionType.TrueFalse)) {
            		if (answer.equals("a") || answer.equals("A")) {
            			answer = "True";
            		}else if(answer.equals("b") || answer.equals("B")){
            			answer = "False";
            		}
            	}
        	}
        	if(q.questiontype.equals(QuestionType.MultChoice) || q.questiontype.equals(QuestionType.ShortAns) || q.questiontype.equals(QuestionType.Matching)) {
				Collections.sort(allAnswers);
			}
        	allAnswers.add(answer);
        }
        //add the list of strings(answer) to the correct answer sheet          
        casheet.addAnswer(allAnswers, true, index);
	}
	
	
	/**
	 * Check if an answer if valid
	 * @param ans  admin input answer
	 * @param q  corresponding question
	 * @return   true if the answer is valid, false otherwise
	 */
	public static boolean checkAnswerValid(String ans, Question q) {
		if(q.questiontype.equals(QuestionType.MultChoice)) {
			//The answer for multiple choice question has to be one or more of the corresponding letter for
			//The choices
			int range = ((MultChoice) q).getNumOfChoices();
			if(! Output.letterInRange(ans, range)) {
				return false;
			}
		}else if(q.questiontype.equals(QuestionType.ShortAns)) {
			//The answer for short answer questions has to be within the length limit set by the admin
			int charLimit = ((ShortAns) q).getMaxLength();
			if (ans.length() > charLimit) {
				return false;
			}
		}else if(q.questiontype.equals(QuestionType.TrueFalse)) {
			//The answer for the T/F question has to be either A or B
			if(! ans.equals("True") && !ans.equals("False") ) {
				return false;
			}
		}else if(q.questiontype.equals(QuestionType.Matching)) {
			//The answer for matching questions has to be of the format "letter number" 
			//And the letter and number has to be the corresponding letter and number for the left and right choices
			try {
				String letter = ans.substring(0, 1);
				int number = Integer.parseInt(ans.substring(2, ans.length()));
				if(! Output.letterInRange(letter, ((Matching)q).qChoicesLeft.size())){
					return false;
				}
				if(number < 1 || number > ((Matching)q).qChoicesLeft.size()) {
					return false;
				}
			}catch(NumberFormatException e){
				return false;
			}catch(IndexOutOfBoundsException e) {
				return false;
			}
		}else if(q.questiontype.equals(QuestionType.Ranking)) {
			//The answer of ranking questions has to in the range of the number of items to rank
			try{
				int enteredNum = Integer.parseInt(ans);
				if(enteredNum <= 0 || enteredNum > ((Ranking) q).qChoicesLeft.size()) {
					return false;
				}
			}catch(NumberFormatException  e){
				return false;
			}
		}
		return true;
	}
}
