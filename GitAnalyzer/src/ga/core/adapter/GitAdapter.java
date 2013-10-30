package ga.core.adapter;

/**
 * This file should be the access point to the GitHub API.
 * @author jonclark
 *
 */
public class GitAdapter {
	private String path;
	/**
	 * Creates an adapter with a local git repository.
	 * @param directory
	 */
	public GitAdapter(String directory) {
		path = directory;
	}
	
	/**
	 * Retrieves the Git log of the current repository.
	 * @return
	 */
	public String GetLog() {
		//TODO: retrieve the git log
		return null;
	}
	
	/**
	 * Retrieves the Git diff of the current repository
	 * @param CommitID
	 * @return
	 */
	public String GetDiff(String CommitID) {
		return null;
	}
}
