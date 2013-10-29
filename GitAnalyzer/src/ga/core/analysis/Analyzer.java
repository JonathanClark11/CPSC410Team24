package ga.core.analysis;

/**
 * Definition of a generic analyzer
 * @author jonclark
 *
 */
public interface Analyzer {
	public <E> E RunAnalysis(E input);
}
