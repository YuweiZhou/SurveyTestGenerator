import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A helper class that load and save serialized files
 * @author Yuwei Zhou
 *
 */
public class Saving {
	
	/**
	 * Load survey/test from a given directory
	 * @param filePath   input directory
	 * @param extension   ".sury" for survey  or ".test" for test
	 * @param t     questionair type t
	 * @return  a list of surveys or tests
	 * @throws IOException
	 */
	public static List<File> LoadFile(String filePath, String extension, QuestionairType t) throws IOException {
		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();
		List<File> finalList = new ArrayList<File>();

		//Get all the file under the directory
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	int fileNameLength = listOfFiles[i].getName().length();
	    	String fileExtension = listOfFiles[i].getName().substring(fileNameLength - 5, fileNameLength);

	    	//Only load serialized file
		    if ( listOfFiles[i].isFile() && fileExtension.equals(extension)) {
		    	finalList.add(listOfFiles[i]);
		    }
	    }
	    
	    if(finalList.size() > 0) {
	    	return finalList;
	    }
	    else {
	    	System.out.println("There is no file in the directory.");
	    	if(t.equals(QuestionairType.Survey)) {
	    		Main.displayCreateLoadSurveyMenu();
	    	}else{
	    		Main.displayCreateLoadTestMenu();
	    	}
	    	return null;
	    }
    }
	
	
	/**
	 * Deserialize a survey
	 * @param file  the file to be deserialized
	 * @return  the deserialzed object (a survey or a test)
	 */
	public static Survey OpenSurvey (File file) {
		try (
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
		){
			// Read objects
			Survey s = (Survey) ois.readObject();
			Output.filesUploaded();
			ois.close();
			fis.close();
			return s;
		}catch (FileNotFoundException e) {
			System.out.println("File not found");
			return null;
		} catch (IOException e) {
			System.out.println("Failed to load file. Error initializing stream");
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load file.");
			return null;
		}
	}
	
	
	/**
	 * Deserialize a test
	 * @param file  the file to be deserialized
	 * @return  the deserialzed object (a survey or a test)
	 */
	public static Test OpenTest (File file) {
		try (
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
		){
			// Read objects
			Test t = (Test) ois.readObject();
			Output.filesUploaded();
			ois.close();
			fis.close();
			return t;
		}catch (FileNotFoundException e) {
			System.out.println("File not found");
			return null;
		} catch (IOException e) {
			System.out.println("Failed to load file. Error initializing stream");
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load file.");
			return null;
		}
	}
	
	
	/**
	 * Serialize a survey and save to a file
	 * @param s  input object(survey) to be serialized
	 * @param name   the name of the file to be saved 
	 * @return true if file is successfully saved
	 */
	public static boolean SaveSurvey (Survey s, String name, boolean append) {
		name += ".sury";
		File tempF = new File(name);
		if(!append && tempF.exists()) {
			System.out.println("File already exists. Please choose a  different name");
			return false;
		}
		else if(append && tempF.exists()) {
			try
	        {
	            Files.deleteIfExists(Paths.get(name));
	        }
	        catch(NoSuchFileException e)
	        {
	            System.out.println("No such file/directory exists");
	        }
	        catch(DirectoryNotEmptyException e)
	        {
	            System.out.println("Directory is not empty.");
	        }
	        catch(IOException e)
	        {
	            System.out.println("Invalid permissions.");
	        }
		}
		
		try (
			FileOutputStream fos = new FileOutputStream(new File(name));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		){
			// Write objects to file
			oos.writeObject(s);
			Output.filesSaved();
			oos.close();
			fos.close();
			return true;
		}catch (FileNotFoundException e) {
			System.out.println("File not found");
			return false;
		} catch (IOException e) {
			System.out.println("Failed to save file. Error initializing stream");
			return false;
		}
	}
	
	
	/**
	 * Serialize a test and save to a file
	 * @param s  input object(test) to be serialized
	 * @param name   the name of the file to be saved 
	 * @return true if file is successfully saved
	 */
	public static boolean SaveTest (Test t, String name, boolean append) {
		name += ".test";
		File tempF = new File(name);
		if(!append && tempF.exists()) {
			System.out.println("File already exists. Please choose a  different name");
			return false;
		}
		
		else if(append && tempF.exists()) {
			try
	        {
	            Files.deleteIfExists(Paths.get(name));
	        }
	        catch(NoSuchFileException e)
	        {
	            System.out.println("No such file/directory exists");
	        }
	        catch(DirectoryNotEmptyException e)
	        {
	            System.out.println("Directory is not empty.");
	        }
	        catch(IOException e)
	        {
	            System.out.println("Invalid permissions.");
	        }
		}

		try (
			FileOutputStream fos = new FileOutputStream(new File(name));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		){
			// Write objects to file
			oos.writeObject(t);
			Output.filesSaved();
			oos.close();
			fos.close();
			return true;
		}catch (FileNotFoundException e) {
			System.out.println("File not found");
			return false;
		} catch (IOException e) {
			System.out.println("Failed to save file. Error initializing stream");
			return false;
		}
	}
	
}
