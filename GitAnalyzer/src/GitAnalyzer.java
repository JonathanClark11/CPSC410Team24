import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import ga.core.adapter.GitAdapter;
import ga.core.adapter.UnityAdapter;
import ga.core.analysis.Analyzer;
import ga.core.analysis.DiffAnalyzer;
import ga.core.model.CommitDrop;

/**
 * The main access point for the program.  
 * This function opens up the UI.
 * @author jonclark
 *
 */
public class GitAnalyzer {
	public static void main(String[] args) {
		String inputDirectory = "/Users/jonclark/ws/CPSC410Team24";
		String outputFilepath = "/Users/jonclark/ws/CPSC410Team24/output/test.xml";
		System.out.println("Input Repo: " + inputDirectory);
		System.out.println("Output File: " + outputFilepath);
		GitAdapter adapter = new GitAdapter(inputDirectory);
		List<CommitDrop> commits = null;
		try {
			commits = adapter.GetLog();
		} catch (JavaGitException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (commits == null) {
			return;
		}
		Analyzer diffAnalysis = new DiffAnalyzer();
		commits = diffAnalysis.RunAnalysis(commits);
		for(CommitDrop d : commits) {
			System.out.println("Author: " + d.getUser());
			System.out.println("CID: "+ d.getId());
			System.out.println("Date: " + d.getDate().toString());
			System.out.println("Size (SLOC): "+ d.getSize());
			System.out.println("Refactors: "+ d.getRefactor());
			System.out.println("New Features: "+ d.getNewFeature());
		}
		
		UnityAdapter uadapter = new UnityAdapter(outputFilepath);
		System.out.println("Exporting to file");
		uadapter.saveFile(commits);
		System.out.println("File Saved");
	}
}
