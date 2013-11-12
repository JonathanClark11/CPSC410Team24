package ga.core.adapter;

import ga.core.model.CommitDrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;


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
			AddDiffData(repository, rev, d, git);
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
	void AddDiffData(Repository repository, RevCommit commit, CommitDrop newCommit, Git git) {
		if (commit.getParentCount() <= 0) {
			return;
		}
		OutputStream out = new ByteArrayOutputStream();
		DiffCommand diff = null;
		List<DiffEntry> entries = null;
		try {
			diff = git.diff().setOutputStream(out)
					.setOldTree(getTreeIterator(commit.name(), repository))
					.setNewTree(getTreeIterator(commit.getParent(0).name(), repository));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			entries = diff.call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		if (entries == null) {
			return;
		}
		//CHECK FOR RENAMES
		RenameDetector rn = new RenameDetector(repository);
		rn.addAll(entries);
		try {
			entries = rn.compute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (DiffEntry d : entries) {
			ChangeType type = d.getChangeType();
			switch(type) {
			case ADD: 
				newCommit.incrementChangeType(0);
				break;
			case COPY: 
				newCommit.incrementChangeType(1);
				break;
			case DELETE: 
				newCommit.incrementChangeType(2);
				break;
			case MODIFY: 
				newCommit.incrementChangeType(3);
				break;
			case RENAME: 
				newCommit.incrementChangeType(4);
				break;
			default:
				break;
			}
		}
		System.out.println("Diff Entries: " + entries.size());
		newCommit.setDiff(out.toString());
	}
	
	private AbstractTreeIterator getTreeIterator(String name, Repository db)
			throws IOException {
		final ObjectId id = db.resolve(name);
		if (id == null)
			throw new IllegalArgumentException(name);
		final CanonicalTreeParser p = new CanonicalTreeParser();
		final ObjectReader or = db.newObjectReader();
		try {
			p.reset(or, new RevWalk(db).parseTree(id));
			return p;
		} finally {
			or.release();
		}
	}
}
