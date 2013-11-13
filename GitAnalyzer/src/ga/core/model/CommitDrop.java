package ga.core.model;

import java.util.Date;

/**
 * The CommitDrop Model for our git analyzer
 * @author Ioana Bercea
 * @author Jonathan Clark
 */
public class CommitDrop {

	private String id;
	private int size;
	private Date date;
	private String user;
	private boolean conflict;
	private boolean merge;
	private int bugFixes;
	private int refactors;
	private int newFeatures;
	
	private String diff;
	/**
	 * ChangeTypes are an array to simulate DiffEntry.ChangeType enum, but instead this is a tally
	 * See: http://download.eclipse.org/jgit/docs/jgit-2.0.0.201206130900-r/apidocs/org/eclipse/jgit/diff/DiffEntry.ChangeType.html
	 * [0] = ADD, [1] = COPY, [2] = DELETE, [3] = MODIFY, [4] = RENAME
	 */
	private int[] changeTypes = new int[5];

	/**
	 * Constructs the CommitDrop class
	 * @param id - The SHA id of the Commit
	 * @param size - The number of lines changed
	 * @param date - The date of the commit
	 * @param user - The author of the commit
	 */
	public CommitDrop(String id, int size, Date date, String user) {
		super();
		this.id = id;
		this.size = size;
		this.date = date;
		this.user = user;
	}

	/**
	 * Get the commit id
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the commit id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the commit size
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Set the commit size based on lines changed
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Get the commit date
	 * @return date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the commit date
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Get the user of the commit
	 * @return user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Set the user of the commit
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Was this commit in conflict?
	 * @return conflict
	 */
	public boolean isConflict() {
		return conflict;
	}

	/**
	 * Set commit's conflict status
	 * @param conflict
	 */
	public void setConflict(boolean conflict) {
		this.conflict = conflict;
	}

	/**
	 * Get the number of bug fixes in this commit
	 * @return num bugFixes
	 */
	public int getBugFix() {
		return bugFixes;
	}

	/**
	 * Set the number of bug fixes in this commit
	 * @param bugFixes
	 */
	public void setBugFix(int bugFix) {
		this.bugFixes = bugFix;
	}

	/**
	 * Get the number of refactors in this commit
	 * @return num refactors
	 */
	public int getRefactor() {
		return refactors;
	}
	/**
	 * Set the number of refactors in this commit
	 * @param refactors
	 */
	public void setRefactor(int refactor) {
		this.refactors = refactor;
	}
	
	/**
	 * Get the number of new features in this commit
	 * @return num newFeatures
	 */
	public int getNewFeature() {
		return newFeatures;
	}
	
	/**
	 * Set the number of new features in this commit
	 * @param newFeatures
	 */
	public void setNewFeature(int newFeature) {
		this.newFeatures = newFeature;
	}

	/**
	 * Is this commit a merged branch?
	 * @return merge status
	 */
	public boolean isMerge() {
		return merge;
	}

	/**
	 * Set the status of the commit as merge or not
	 * @param merge
	 */
	public void setMerge(boolean merge) {
		this.merge = merge;
	}
	
	/**
	 * @return
	 */
	public String getDiff() {
		return diff;
	}
	
	/**
	 * @param diff
	 */
	public void setDiff(String diff) {
		this.diff = diff;
	}
	
	/**
	 * @return
	 */
	public int[] getChangeTypes() {
		return changeTypes;
	}
	/**
	 * @param changeTypes
	 */
	public void setChangeTypes(int[] changeTypes) {
		this.changeTypes = changeTypes;
	}
	/**
	 * @param index
	 * @param value
	 */
	public void setChangeTypes(int index, int value) {
		this.changeTypes[index] = value;
	}
	/**
	 * @param index
	 */
	public void incrementChangeType(int index) {
		this.changeTypes[index]++;
	}
}
