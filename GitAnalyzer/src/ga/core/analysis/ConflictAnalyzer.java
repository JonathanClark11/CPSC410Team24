package ga.core.analysis;

import ga.core.model.CommitDrop;

import java.util.List;

/**
 * This class analyzes the diff from two commits and checks for conflicts
 * @author jonclark
 *
 */
public class ConflictAnalyzer implements Analyzer {
	private int conflictSize = 3;
	private int conflictCI = 255;
	private int maxYear = 0;
	@Override
	public List<CommitDrop> RunAnalysis(List<CommitDrop> input) {
		int totalConflicts = 0;
		for (CommitDrop d : input) {
			if (d.isMerge()) {
				d.setConflict(true);
				totalConflicts++;
				d.setColorIntensity(conflictCI);
				d.setSize(conflictSize);
			}
		}
		System.out.println("Total Conflicts: " + totalConflicts);
		return input;
	}

}
