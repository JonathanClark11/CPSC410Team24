package ga.core.analysis;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.api.commands.GitLogResponse.Commit;
import ga.core.model.CommitDrop;

/**
 * This will analyze Git logs
 * @author jonclark
 *
 */
public class LogParser {

	public List<CommitDrop> GenerateCommits(List<Commit> input) {
		List<CommitDrop> returnList = new ArrayList<CommitDrop>();
		for (Commit c : input) {
			CommitDrop d = new CommitDrop();
			returnList.add(d);
		}
		return returnList;
	}
}
