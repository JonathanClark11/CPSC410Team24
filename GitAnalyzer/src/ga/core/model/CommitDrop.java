package ga.core.model;

import java.util.Date;

public class CommitDrop {

	private String id;
	private int size;
	private Date date;
	private String user;
	private boolean conflict;
	private int bugFixes;
	private int refactors;
	private int newFeatures;

	/**
	 * Constructs the CommitDrop class
	 * @param id
	 * @param size
	 * @param date
	 * @param user
	 * @param conflict
	 * @param bugFixes
	 * @param refactors
	 * @param newFeatures
	 */
	public CommitDrop(String id, int size, Date date, String user,
			boolean conflict, int bugFixes, int refactors, int newFeatures) {
		super();
		this.id = id;
		this.size = size;
		this.date = date;
		this.user = user;
		this.conflict = conflict;
		this.bugFixes = bugFixes;
		this.refactors = refactors;
		this.newFeatures = newFeatures;
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

}
