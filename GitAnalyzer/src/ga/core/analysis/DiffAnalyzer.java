package ga.core.analysis;

import ga.core.model.CommitDrop;
import ga.util.ArrayUtil;
import ga.util.NormUtil;

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
		int bugFixes = 0, refactors = 0, features = 0;
		double maxSize = 0, minSize = -1;
		double maxColor = 0, minColor = -1, avgColor = 0;
		double maxDirty[] = {0, 0, 0};
		double commitSum = 0, commitSizeAvg = 0;
		System.out.println("Running Diff Analysis----------------");
		
		System.out.println("Loading preliminary data");
		for (CommitDrop d : input) {
			if (d.getDiff() == null) {
				System.out.println("NULL DIFF - Possible Root");
				continue;
			}
			int commitSize = findLinesChanged(d.getDiff(), d.getChangeTypes());
			if (!d.isMerge()) {
				commitSum += commitSize;
			}
			d.setSize(commitSize);
			
			if (commitSize > maxSize) { 
				maxSize = commitSize;
			}
			if (commitSize <= minSize || minSize <= 0) { //include zero to ignore 0 sized changes
				minSize = commitSize;
			}
		}
		commitSizeAvg = commitSum / input.size();

		for (CommitDrop d : input) {
			System.out.println("Running Diff Analysis on: " + d.getId());
			if (d.getDiff() == null) {
				System.out.println("NULL DIFF - Possible Root");
				continue;
			}

			//System.out.println("CHANGE TYPES: " + Arrays.toString(d.getChangeTypes()));

//			[0] = Refactor, [1] = Bug Fix, [2] = New Feature
			final double pointsDist[] = {0, 0, 0};
			
//			CHECK COMMENTS FOR HINTS
			final String commitmsg = d.getCommitMessage().toLowerCase();
			if (commitmsg.contains("bugfix") || commitmsg.contains("bug") || commitmsg.contains("jira")) {
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
			if (d.getSize() < 200) {
				pointsDist[1] += 1;
			} else if (d.getSize() >= 200 && d.getSize() <= 400) {
				pointsDist[2] += 1;
			} else if ((d.getSize() > 400 && d.getSize() < 1000)){
				pointsDist[0] += 1;
			}
			
//			TALLY UP VOTES!
			switch (ArrayUtil.getMaxIndex(pointsDist)) {
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
			
//			SET SIZE RATIO
			NormUtil util = new NormUtil(commitSizeAvg + 100, commitSizeAvg - 100, 0.25, 1.0);
			double trimmedSize = d.getSize();
			trimmedSize = Math.max(trimmedSize, commitSizeAvg - 100);
			trimmedSize = Math.min(trimmedSize,  commitSizeAvg + 100);
			double ratioSize = util.normalize(trimmedSize);
			
			d.setRatioSize(ratioSize);
			
//			SET POINTS DIST AND CALCULATE MAX DIRTY COMMIT FOR NORMALIZING
			d.setPointsDist(pointsDist);
			if(2*pointsDist[0] + pointsDist[1] > maxDirty[0]) {
				maxDirty[0] = 2*pointsDist[0] + pointsDist[1];
				maxDirty[1] = pointsDist[0];
				maxDirty[2] = pointsDist[1];
			}
		}
		
		double dirtyScale = 255 / (2*maxDirty[1] + maxDirty[2]);
		//Now get min-max
		for (CommitDrop d : input) {
			if (d.getDiff() == null) {
				System.out.println("NULL DIFF - Possible Root");
				continue;
			}
			double score[] = d.getPointsDist();
			double colorIntensity = 255 - dirtyScale*(2*score[0]+score[1]);
			d.setColorIntensity(colorIntensity);
			avgColor += colorIntensity;
			if (colorIntensity > maxColor) {
				maxColor = colorIntensity;
			}
			if (colorIntensity < minColor || minColor < 0) {
				minColor = colorIntensity;
			}
		}
		avgColor = avgColor / input.size();
		
		System.out.println("");
		System.out.println("Min Commit Size: " + minSize);
		System.out.println("Commit Size Avg: " + commitSizeAvg);
		System.out.println("Max Commit Size: " + maxSize);
		
		System.out.println("Max Dirty: " + maxDirty[0] + ", R: " + maxDirty[1] + ", B: " + maxDirty[2]);
		System.out.println("Dirty Scale: " + dirtyScale);
		System.out.println("Min Colour: " + minColor);
		System.out.println("Avg Colour: " + avgColor);
		System.out.println("Max Colour: " + maxColor);
		
		System.out.println("Refactors total: " + refactors);
		System.out.println("Bug Fixes Total: " + bugFixes);
		System.out.println("New Features Total: " + features);
		
		return input;
	}
	
	
	private int findLinesChanged(String difftext, int[] commitType) {
		String pattern = "(@)(@) ([-+]\\d+),(\\d+) ([-+]\\d+),(\\d+) (@)(@)";
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(difftext);
		//System.out.println("Diff Length: "+ difftext.length());
		int linesAdded = 0;
		int linesRemoved = 0;
		while (m.find())
		{   
			linesAdded += Integer.parseInt(m.group(6));
			linesRemoved += Integer.parseInt(m.group(4));
		}
		//if file deleted: don't count lines removed
		if (commitType[2] > 0) {
			return linesAdded;
		} else {
			return linesAdded + linesRemoved;
		}
	}

}
