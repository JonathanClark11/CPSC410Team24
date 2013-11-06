package ga.core.adapter;

import ga.core.model.CommitDrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;


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
	 * Gets the git log data from jgit
	 * @return
	 * @throws IOException
	 * @throws NoHeadException
	 * @throws GitAPIException
	 */
	public List<CommitDrop> GetJGitLog() throws IOException, NoHeadException, GitAPIException {
		List<CommitDrop> commits = new ArrayList<CommitDrop>();
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(new File(path))
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();
		Git git = new Git(repository);
		Iterable<RevCommit> log = git.log().call();
		
		for (Iterator<RevCommit> iterator = log.iterator(); iterator.hasNext();) {
	        RevCommit rev = iterator.next();
	        System.out.println("Loaded Commit: " + rev.getName());
	        CommitDrop d = new CommitDrop(rev.getName(), 0, new Date(rev.getCommitTime()), rev.getAuthorIdent().getName());
	        AddDiffData(repository, rev, d);
	        commits.add(d);
	    }
		
		return commits;
	}
	
	/**
	 * Adds missing data to our commit object
	 * @param repository
	 * @param commit
	 * @param newCommit
	 */
	void AddDiffData(Repository repository, RevCommit commit, CommitDrop newCommit) {
		RevWalk rw = new RevWalk(repository);
		RevCommit parent = null;
		if (commit.getParentCount() > 0 && commit.getParent(0) != null) {
			try {
				parent = rw.parseCommit(commit.getParent(0).getId());
			} catch (MissingObjectException e) {
				e.printStackTrace();
			} catch (IncorrectObjectTypeException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DiffFormatter df = new DiffFormatter(out);

		//DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
		df.setRepository(repository);
		df.setDiffComparator(RawTextComparator.DEFAULT);
		df.setDetectRenames(true);

		List<DiffEntry> diffs = null;
		try {
			diffs = df.scan(new EmptyTreeIterator(), new CanonicalTreeParser(null, rw.getObjectReader(), commit.getTree()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (DiffEntry diff : diffs) {
			try {
				df.format(diff);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String diffText = null;
			try {
				diffText = out.toString("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			newCommit.setDiff(newCommit.getDiff() + diffText);
			out.reset();
		}
	}
}
