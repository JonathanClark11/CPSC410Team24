package ga.core.analysis;

import ga.core.model.CommitDrop;
//import ga.util.NormUtil;

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

			//[0] = Refactor, [1] = Bug Fix, [2] = New Feature
			final int pointsDist[] = {0, 0, 0};
			
//			CHECK COMMENTS FOR HINTS
			final String commitmsg = d.getCommitMessage().toLowerCase();
			if (commitmsg.contains("merge")) {
				//We need to check for conflict 
			}
			if (commitmsg.contains("bugfix")) {
				pointsDist[1] += 3; //clear definition of bugfix
			}
			
//			CHECK CHANGE TYPE
			if (d.getChangeTypes()[4] > 0) {	//if renamed files
				pointsDist[0] += 2;	//refactor+2
			}
			if (d.getChangeTypes()[0] > 0) {	//if added files
				pointsDist[2] += 2; //newfeatures+2
			}

//			CHECK FOR COMMIT SIZE
			if (commitSize < 200) {
				pointsDist[1] += 1;
			} else if (commitSize >= 200 && commitSize <= 400) {
				pointsDist[2] += 1;
			} else if ((commitSize > 400 && commitSize < 1000)){
				pointsDist[0] += 1;
			}
			
//			TALLY UP VOTES!
			switch (getMaxIndex(pointsDist)) {
			case 0:
				refactors++;
				d.setRefactor(d.getRefactor()+1);
				break;
			case 1:
				bugFixes++;
				d.setBugFix(d.getBugFix()+1);
				break;
			case 2:
				features++;
				d.setNewFeature(d.getNewFeature()+1);
				break;
			default:
				break;
			}
			
		}
		
//		for (CommitDrop d : input) {
//			NormUtil util = new NormUtil((double)maxSize, (double)minSize, 0.1, 1.0);
//			double ratioSize = util.normalize(d.getSize());
//			System.out.println("Ratio: " + ratioSize);
//			d.setRatioSize(ratioSize);
//		}
		
		System.out.println("");
		System.out.println("Min Commit Size: " + minSize);
		System.out.println("Max Commit Size: " + maxSize);
		System.out.println("Refactors total: " + refactors);
		System.out.println("Bug Fixes Total: " + bugFixes);
		System.out.println("New Features Total: " + features);
		System.out.println("Uncategorized Total: " + uncategorized);
		
		return input;
	}
	
	private int getMaxIndex(int arr[]) {
		int maxIndex = 0, max=0;
		for (int i = 0; i < arr.length; i++) {
		    if (arr[i] > max) {
		        max = arr[i];
		        maxIndex = i;
		    }
		}
		return maxIndex;
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
