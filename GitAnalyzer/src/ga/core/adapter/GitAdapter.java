package ga.core.adapter;

import java.io.File;
import java.util.List;

import edu.nyu.cs.javagit.api.commands.GitLogResponse.Commit;
import edu.nyu.cs.javagit.api.DotGit;

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
	public List<Commit> GetLog() throws Exception {
		File repositoryDirectory = new File(path);
		DotGit dotGit = DotGit.getInstance(repositoryDirectory);
		return dotGit.getLog();
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
