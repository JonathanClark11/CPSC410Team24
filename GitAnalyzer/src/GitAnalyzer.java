import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import ga.core.adapter.GitAdapter;
import ga.core.model.CommitDrop;

/**
 * The main access point for the program.  
 * This function opens up the UI.
 * @author jonclark
 *
 */
public class GitAnalyzer {
	public static void main(String[] args) {
		String inputDirectory = "/Users/jonclark/ws/java";
		String outputFilepath = "...";
		
		GitAdapter adapter = new GitAdapter(inputDirectory);
		try {
			List<CommitDrop> commits = adapter.GetLog();
			for(CommitDrop d : commits) {
				System.out.println("Author: (" + d.getUser() + ") CID: "+ d.getId());
			}
		} catch (JavaGitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
