package ga.core.analysis;

import ga.core.model.CommitDrop;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This will analyze Git diffs
 * @author jonclark
 *
 */
public class DiffAnalyzer implements Analyzer{

	public List<CommitDrop> RunAnalysis(List<CommitDrop> input) {
		int bugFixes = 0, refactors = 0, features = 0, uncategorized = 0;
		System.out.println("Running Diff Analysis----------------");
		for (CommitDrop d : input) {
			System.out.println("Running Diff on Commit: " + d.getId());
			int commitSize = findLinesChanged(d.getDiff());
			d.setSize(commitSize);
			System.out.println("Commit Size: " + commitSize);
			
			if (commitSize < 200) {
	 			d.setBugFix(d.getBugFix()+1);
	 			bugFixes++;
			} else if (commitSize >= 200 && commitSize <= 400) {
				d.setNewFeature(d.getNewFeature()+1);
				features++;
			} else if (commitSize > 400 && commitSize < 1000){
				refactors++;
				d.setRefactor(d.getRefactor()+1);
			} else {
				uncategorized++;
			}
		}
		System.out.println("Refactors total: " + refactors);
		System.out.println("Bug Fixes Total: " + bugFixes);
		System.out.println("New Features Total: " + features);
		System.out.println("Uncategorized Total: " + uncategorized);
		return input;
	}
	
	private int findLinesChanged(String difftext) {
	    String pattern = "(@)(@) ([-+]\\d+),(\\d+) ([-+]\\d+),(\\d+) (@)(@)";
	    Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(difftext);
	    System.out.println("Diff Length: "+ difftext.length());
	    int linesAdded = 0;
	    int linesRemoved = 0;
	    while (m.find())
	    {   
	        linesAdded += Integer.parseInt(m.group(6));
	        linesRemoved += Integer.parseInt(m.group(4));
	    }
	    return linesAdded + linesRemoved;
	}

}
