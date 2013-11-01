package ga.core.analysis;

import ga.core.model.CommitDrop;

import java.util.List;

/**
 * This will analyze Git diffs
 * @author jonclark
 *
 */
public class DiffAnalyzer implements Analyzer{

	public List<CommitDrop> RunAnalysis(List<CommitDrop> input) {
		for (CommitDrop d : input) {
			d.setSize(23); //FOR TESTING PURPOSES
			if (d.getSize() <= 10) {
	 			d.setBugFix(d.getBugFix()+1);
			} else if (d.getSize() > 10 && d.getSize() <= 100) {
				
			} else {
				d.setNewFeature(1);
			}
		}
		return input;
	}

}
