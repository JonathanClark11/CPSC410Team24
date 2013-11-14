package ga.core.analysis;

import ga.core.model.CommitDrop;
import ga.util.NormUtil;

import java.util.Arrays;
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
		int maxSize = 0, minSize = -1;
		System.out.println("Running Diff Analysis----------------");
		for (CommitDrop d : input) {
			System.out.println("Running Diff Analysis on: " + d.getId());
			if (d.getDiff() == null) {
				System.out.println("NULL DIFF - Possible Root");
				continue;
			}
			int commitSize = findLinesChanged(d.getDiff());
			d.setSize(commitSize);
			if (commitSize > maxSize) { 
				maxSize = commitSize;
			}
			if (commitSize <= minSize || minSize <= 0) { //include zero to ignore 0 sized changes
				minSize = commitSize;
			}
			System.out.println("Commit Size: " + commitSize);
			System.out.println("CHANGE TYPES: " + Arrays.toString(d.getChangeTypes()));
			
			if (commitSize < 200) {
				d.setBugFix(d.getBugFix()+1);
				bugFixes++;
			} else if (commitSize >= 200 && commitSize <= 400) {
				d.setNewFeature(d.getNewFeature()+1);
				features++;
			} else if ((commitSize > 400 && commitSize < 1000) || d.getChangeTypes()[4] > 0){
				refactors++;
				d.setRefactor(d.getRefactor()+1);
			} else {
				uncategorized++;
			}
		}
		
		for (CommitDrop d : input) {
			NormUtil util = new NormUtil((double)maxSize, (double)minSize, 0.1, 1.0);
			double ratioSize = util.normalize(d.getSize());
			System.out.println("Ratio: " + ratioSize);
			d.setRatioSize(ratioSize);
		}
		
		System.out.println("");
		System.out.println("Min Commit Size: " + minSize);
		System.out.println("Max Commit Size: " + maxSize);
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
