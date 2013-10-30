package ga.core.adapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.api.commands.GitLogResponse.Commit;
import edu.nyu.cs.javagit.api.DotGit;
import edu.nyu.cs.javagit.api.JavaGitException;
import ga.core.model.CommitDrop;

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
	public List<CommitDrop> GetLog() throws JavaGitException, IOException {
		File repositoryDirectory = new File(path);
		DotGit dotGit = DotGit.getInstance(repositoryDirectory);
		
		List<CommitDrop> returnList = new ArrayList<CommitDrop>();
		for (Commit c : dotGit.getLog()) {
			CommitDrop d = new CommitDrop();
			returnList.add(d);
		}
		return returnList;
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
