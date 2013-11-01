package ga.core.adapter;

import ga.core.model.CommitDrop;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import com.unity3d.player.UnityPlayer;
//import com.unity3d.player.UnityPlayerActivity;

/**
 * 
 * @author jonclark
 *
 */

public class UnityAdapter{
	private String filepath;
	//private UnityPlayer mUnityPlayer;
	List<CommitDrop> commits = new ArrayList<CommitDrop>();
	
	public UnityAdapter(String filepath) {
		this.filepath = filepath;
	}
	

	/*public void SendMessageToUnity()
	{
		//mUnityPlayer = new UnityPlayer(getActivity()); // Put your activity object here
	    //int glesMode = mPlayer.getSettings().getInt("gles_mode", 1);
	    //boolean trueColor8888 = false;
	    //mPlayer.init(glesMode, trueColor8888);

	    // Get the view - e.g. to return from the fragment method onCreateView:
	    //View view = mPlayer.getView();


	    UnitySendMessage(commits, "ShowDroplets", commits); //.UnitySendMessage("ShowDroplets",commits);


	//commits.add("test");

	}*/

	public void saveFile(List<CommitDrop> commits){

		try {
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();

			Element root = doc.createElement("Commits");
			doc.appendChild(root);

			for(int i=0; i<commits.size(); i++) {
				
				Element commit = doc.createElement("Commit");
				
				Element date = doc.createElement("Date");
				date.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getDate())));
				commit.appendChild(date);

				Element user = doc.createElement("User");
				user.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getUser())));
				commit.appendChild(user);

				Element id = doc.createElement("Id");
				id.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getId())));
				commit.appendChild(id);

				Element size = doc.createElement("Size");
				size.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getSize())));
				commit.appendChild(size);

				Element merge = doc.createElement("Merge");
				merge.appendChild(doc.createTextNode(String.valueOf(commits.get(i).isMerge())));
				commit.appendChild(merge);

				Element conflict = doc.createElement("Conflict");
				conflict.appendChild(doc.createTextNode(String.valueOf(commits.get(i).isConflict())));
				commit.appendChild(conflict);

				Element bFN = doc.createElement("BugFixNumber");
				bFN.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getBugFix())));
				commit.appendChild(bFN);

				Element numRefs = doc.createElement("NumRefs");
				numRefs.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getNewFeature())));
				commit.appendChild(id);
				
				root.appendChild(commit);
			}

			// Save the document to the disk file
			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();

			// format the XML nicely
			aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

			aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");



			DOMSource source = new DOMSource(doc);
			try {
				FileWriter fos = new FileWriter(filepath);
				StreamResult result = new StreamResult(fos);
				aTransformer.transform(source, result);

			} catch (IOException e) {

				e.printStackTrace();
			}



		} catch (TransformerException ex) {
			System.out.println("Error outputting document");

		} catch (ParserConfigurationException ex) {
			System.out.println("Error building document");
		}

	}
}
