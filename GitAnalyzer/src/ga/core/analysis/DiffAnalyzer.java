package ga.core.analysis;

import ga.core.adapter.GitAdapter;
import ga.core.model.CommitDrop;

import java.util.List;

/**
 * This will analyze Git diffs
 * @author jonclark
 *
 */
public class DiffAnalyzer{

	public List<CommitDrop> RunAnalysis(List<CommitDrop> input) {
		for (CommitDrop d : input) {
//			List<CommitFile> files = d.
			if (d.getSize() <= 10) {
//				d.set
			} else if (d.getSize() > 10 && d.getSize() <= 100) {
				
			} else {
				d.setNewFeature(1);
			}
		}
		return null;
	}

}
