package ga.core.adapter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.commands.GitLogResponse.Commit;
import edu.nyu.cs.javagit.api.DotGit;
import edu.nyu.cs.javagit.api.JavaGitException;

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
	 * Retrieves the Git diff of the specified ID
	 * @param CommitID
	 * @return
	 */
	public Commit GetDiff(String CommitID) throws JavaGitException, IOException{
		File repositoryDirectory = new File(path);
		DotGit dotGit = DotGit.getInstance(repositoryDirectory);
		for (Commit c : dotGit.getLog()) {
			if (c.getSha() == CommitID) {
				return c;
			}
		}
		return null;
	}
}
