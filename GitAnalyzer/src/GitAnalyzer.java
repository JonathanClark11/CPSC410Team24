import ga.core.adapter.GitAdapter;
import ga.core.analysis.DiffAnalyzer;
import ga.core.analysis.LogAnalyzer;
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
		DiffAnalyzer diffAnalysis = new DiffAnalyzer();
		LogAnalyzer logAnalysis = new LogAnalyzer();
		
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
