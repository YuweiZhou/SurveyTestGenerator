import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/**
 * Has a list of Questions
 * Has a list of user answer sheets.
 * @author Yuwei Zhou
 *
 */
@SuppressWarnings("serial")
public class Survey implements java.io.Serializable{

	public List<Question> mySurvey = new ArrayList<Question>();
	private transient static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private String name;
	public QuestionairType questionairType = QuestionairType.Survey;
	//Record all the user answers for tabulating uses
	public List<AnswerSheet> asheets = new ArrayList<AnswerSheet>();
	// Create a hash map
    private HashMap<Integer, HashMap<String, Integer>> ListOfHashMap = new HashMap<Integer, HashMap<String, Integer>>();
	
	
	/**
	 * Constructor
	 */
	public Survey(String surveyName) {
		name = surveyName;
	}
	
	
	/**
	 * Get the name of the survey
	 * @return  survey name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * set the name for the survey
	 * @param n   name
	 */
	public void setName(String n) {
		name = n;
	}
	
	
	/**
	 * Add a question to the survey
	 * @param t   question type
	 * @return   the created question
	 * @throws IOException
	 */
	public Question addNewQuestion(QuestionType t) throws IOException {
		if (t.equals(QuestionType.MultChoice)) {
			//Create a new multiple choice question and add to the survey
			MultChoice mt = new MultChoice();
			mt.promptForQuestion();
			mySurvey.add(mt);
			return mt;
		}
		else if (t.equals(QuestionType.TrueFalse)) {
			//Create a new T/F question and add to the survey
			TrueFalse tf = new TrueFalse();
			tf.promptForQuestion();
			mySurvey.add(tf);
			return tf;
		}
		else if (t.equals(QuestionType.ShortAns)) {
			//Create a new short answer question and add to the survey
			ShortAns sa = new ShortAns();
			sa.promptForQuestion();
			mySurvey.add(sa);
			return sa;
		}
		else if (t.equals(QuestionType.Essay)) {
			//Create a new essay and add to the survey
			Essay es = new Essay();
			es.promptForQuestion();
			mySurvey.add(es);
			return es;
		}
		else if (t.equals(QuestionType.Matching)) {
			//Create a new matching question and add to the survey
			Matching ma = new Matching();
			ma.promptForQuestion();
			mySurvey.add(ma);
			return ma;
		}
		else{
			//Create a new ranking question and add to the survey
			Ranking rk = new Ranking();
			rk.promptForQuestion();
			mySurvey.add(rk);
			return rk;
		}
	
	}
	
	
	/**
	 * Add a user input answer sheet to the answer sheet list
	 * @param as   user answer sheet
	 */
	public void addUserAnswerSheet(AnswerSheet as) {
		asheets.add(as);
	}
	
	
	
	/**
	 * Display a survey
	 */
	public void Display() {
		for(int i =0; i< mySurvey.size(); i++) {
			Question q = mySurvey.get(i);
			System.out.println( (i+1) + ") ");
			q.Display();
			System.out.println("\n");
		}
	}
	
	
	/**
	 * Modify a survey
	 * @throws IOException 
	 */
	public int Modify() throws IOException {
		System.out.println("Below are the questions in this survey.");
		this.Display();
		System.out.println("Please enter the question number which you want to modify:");
        int qIndex = Output.getNextInt(input, 1, mySurvey.size()); 
        Question q = mySurvey.get(qIndex-1);
        q.modify();
        mySurvey.set(qIndex-1, q);
        return qIndex-1;
	}

	
	/**
	 * Get the ith question in the survey
	 * @param index
	 * @return
	 */
	public Question getQuestionWithIndex(int index) {
		Question q = mySurvey.get(index);
		return q;
	}
	
	
	/**
	 * Tabulate a survey
	 */
	public void Tabulate() {
		if(asheets.size() > 0) {
			for(int i = 0; i< mySurvey.size(); i++) {
				Question q = mySurvey.get(i);
				System.out.print((i+1 )+ ") ");
				System.out.println(q.getQuestion());
				QuestionType qt = q.questiontype;
				HashMap<String, Integer> hm = ListOfHashMap.get(i);
				
				if(qt.equals(QuestionType.ShortAns) || qt.equals(QuestionType.MultChoice) || qt.equals(QuestionType.TrueFalse)) {
					for (HashMap.Entry<String, Integer> entry : hm.entrySet()) {
						if(q.expectedNumOfAns > 1) {
							System.out.println(entry.getValue() + ") \n" + entry.getKey() + "\n");
						}else {
							System.out.println(entry.getKey()+" "+entry.getValue() + "\n");
						}
					}
				}else if(qt.equals(QuestionType.Essay)){
					for (HashMap.Entry<String, Integer> entry : hm.entrySet()) {
					    System.out.println(entry.getKey());
					}
				}else if(qt.equals(QuestionType.Matching) || qt.equals(QuestionType.Ranking)) {
					for (HashMap.Entry<String, Integer> entry : hm.entrySet()) {
					    System.out.println(entry.getValue()+ ") \n" + entry.getKey() + "\n");
					}
				}
				System.out.println("\n\n");
			}
		}else {
			System.out.println("There is no answer sheets available for this test/survey. Cannot be tabulated.");
			return;
		}
	}
	
	
	/**
	 * Take a survey
	 * @throws IOException 
	 */
	public void Take() throws IOException {
		AnswerSheet as = new AnswerSheet();
		System.out.println("Please enter your name: ");
		as.setUserName(Output.getNotNullInput(input));
		
		for(int i = 0; i< mySurvey.size(); i++) {
			Question q = mySurvey.get(i);
			System.out.print((i+1 )+ ") ");
			q.Display();
			QuestionType qt = q.questiontype;
			List<String> allAnswers = new ArrayList<String>();

			if(qt.equals(QuestionType.MultChoice)) {
				System.out.println("Please enter " + q.expectedNumOfAns + " answers: ");
			}else if(qt.equals(QuestionType.Matching)) {
				System.out.println("Please enter " + q.expectedNumOfAns + " pairs of answers of your question in the format of \"Letter Number\"");
			}else if(qt.equals(QuestionType.Ranking)) {
				System.out.println("Note: Please enter the numbers one by one in the right order:");
			}else if(qt.equals(QuestionType.ShortAns)) {
				System.out.println("Please enter " + q.expectedNumOfAns + " answers(less than " + ((ShortAns)q).getMaxLength() + " characters for each answer):");
			}
			//one hash map per question
			HashMap<String, Integer> hm = new HashMap<String, Integer>();
			if(ListOfHashMap.containsKey(i)) {
				hm = ListOfHashMap.get(i);
			}
			for (int j = 0; j< q.expectedNumOfAns; j++)
			{
				System.out.println((j+1) + ": ");
	        	String answer = Output.getNotNullInput(input);
	        	if(qt.equals(QuestionType.MultChoice)|| qt.equals(QuestionType.Matching)) {
	        		answer = answer.toUpperCase();
	        	}else if(qt.equals(QuestionType.ShortAns)) {
	        		answer = answer.toLowerCase();
	        	}else if(qt.equals(QuestionType.TrueFalse)) {
	        		if(answer.equals("a") ||answer.equals("A")) {
	        			answer = "True";
	        		}else if(answer.equals("b") ||answer.equals("B")) {
	        			answer = "False";
	        		}
	        	}
	        	
	        	//Check if the answer is valid
	        	while(!Test.checkAnswerValid(answer, q)) {
	        		Output.invalidInputWarning();
	        		answer = Output.getNotNullInput(input);
	        		if(qt.equals(QuestionType.MultChoice)|| qt.equals(QuestionType.Matching)) {
		        		answer = answer.toUpperCase();
		        	}else if(qt.equals(QuestionType.ShortAns)) {
		        		answer = answer.toLowerCase();
		        	}else if(qt.equals(QuestionType.TrueFalse)) {
		        		if(answer.equals("a") ||answer.equals("A")) {
		        			answer = "True";
		        		}else if(answer.equals("b") ||answer.equals("B")) {
		        			answer = "False";
		        		}
		        	}
	        	}
	        	allAnswers.add(answer);
			}
			if(qt.equals(QuestionType.MultChoice) || qt.equals(QuestionType.ShortAns) || qt.equals(QuestionType.Matching)) {
				Collections.sort(allAnswers);
			}
			as.addAnswer(allAnswers, false, i);
			//Check if this answer is already in the hash map
			String stringAns = as.getAnswerAtIndex(i);
        	if(hm.get(stringAns) != null) {
        		hm.put(stringAns, hm.get(stringAns) + 1);
        	}else {
        		hm.put(stringAns, 1);
        	}
        	ListOfHashMap.put(i, hm);
		}
		//Add answer sheet to all user answer sheets 
		addUserAnswerSheet(as);
		System.out.println("\n\n");
	}

}
