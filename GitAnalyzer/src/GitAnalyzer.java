import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import ga.core.adapter.GitAdapter;
import ga.core.model.CommitDrop;
import ga.ui.MainWindow;

import javax.swing.JFrame;

/**
 * The main access point for the program.  
 * This function opens up the UI.
 * @author jonclark
 *
 */
public class GitAnalyzer {
	public static void main(String[] args) {
		String inputDirectory = "...";
		String outputFilepath = "...";
		
		GitAdapter adapter = new GitAdapter(inputDirectory);
		try {
			List<CommitDrop> commits = adapter.GetLog();
			
		} catch (JavaGitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//diffAnalysis.RunAnalysis(adapter.GetDiff(CommitID))
		/*
		 * each commit contains
		 * id
		 * flag - conflict
		 * size
		 * files used
		 * time
		 */
		
		/*
		 * make adapter
		 * pull information and run through diffanalayzer, loganalyzer
		 * send the output through a file saver
		 */
	    JFrame f = new MainWindow();
	    f.setVisible(true);
	}
}
