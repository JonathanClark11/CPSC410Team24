package ga.core.analysis;

/**
 * This will analyze Git logs
 * @author jonclark
 *
 */
public class LogAnalyzer implements Analyzer {

	@Override
	public <E> E RunAnalysis(E input) {
		String GitLog = (String)input;
		System.out.println("DEBUG: " + GitLog);
		return null;
	}
}
