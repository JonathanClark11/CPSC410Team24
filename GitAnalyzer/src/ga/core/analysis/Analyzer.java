package ga.core.analysis;

import ga.core.model.CommitDrop;

import java.util.List;

/**
 * Definition of a generic analyzer
 * @author jonclark
 *
 */
public interface Analyzer {
	public List<CommitDrop> RunAnalysis(List<CommitDrop> input);
}
