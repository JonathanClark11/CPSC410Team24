package ga.core.adapter;

import ga.core.model.CommitDrop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.text.View;
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

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

/**
 * 
 * @author jonclark
 *
 */

public class FileAdapter{

	private UnityPlayer mUnityPlayer;
	List<CommitDrop> commits = new ArrayList<CommitDrop>();

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

			/*	Element Details = doc.createElement("Details");
		root.appendChild(Details);*/


			for(int i=0; i<commits.size(); i++) {

				Element date = doc.createElement("Date");
				date.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getDate())));
				root.appendChild(date);

				Element user = doc.createElement("User");
				user.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getUser())));
				root.appendChild(user);

				Element id = doc.createElement("Id");
				id.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getId())));
				root.appendChild(id);

				Element size = doc.createElement("Size");
				size.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getSize())));
				root.appendChild(size);

				Element merge = doc.createElement("Merge");
				merge.appendChild(doc.createTextNode(String.valueOf(commits.get(i).isMerge())));
				root.appendChild(merge);

				Element conflict = doc.createElement("Conflict");
				conflict.appendChild(doc.createTextNode(String.valueOf(commits.get(i).isConflict())));
				root.appendChild(conflict);

				Element bFN = doc.createElement("BugFixNumber");
				bFN.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getBugFix())));
				root.appendChild(bFN);

				Element numRefs = doc.createElement("NumRefs");
				numRefs.appendChild(doc.createTextNode(String.valueOf(commits.get(i).getNewFeature())));
				root.appendChild(id);

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
				FileWriter fos = new FileWriter("/home/commits.xml");
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
