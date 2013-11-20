package ga.core.adapter;

import ga.core.model.CommitDrop;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	List<CommitDrop> commits = new ArrayList<CommitDrop>();
	
	public UnityAdapter(String filepath) {
		this.filepath = filepath;
	}

	public void saveFile(List<CommitDrop> commits) {

		try {
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();

			Element root = doc.createElement("Commits");
			doc.appendChild(root);

			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			for (CommitDrop d : commits) {
				Element commit = doc.createElement("Commit");
				
				Element date = doc.createElement("Date");
				date.appendChild(doc.createTextNode(String.valueOf(df.format(d.getDate()))));
				commit.appendChild(date);

				Element user = doc.createElement("User");
				user.appendChild(doc.createTextNode(String.valueOf(d.getUser())));
				commit.appendChild(user);

				Element id = doc.createElement("Id");
				id.appendChild(doc.createTextNode(String.valueOf(d.getId())));
				commit.appendChild(id);

				Element size = doc.createElement("Size");
				size.appendChild(doc.createTextNode(String.valueOf(d.getSize())));
				commit.appendChild(size);

				Element merge = doc.createElement("Merge");
				merge.appendChild(doc.createTextNode(String.valueOf(d.isMerge())));
				commit.appendChild(merge);

				Element conflict = doc.createElement("Conflict");
				conflict.appendChild(doc.createTextNode(String.valueOf(d.isConflict())));
				commit.appendChild(conflict);

				Element bFN = doc.createElement("BugFixNumber");
				bFN.appendChild(doc.createTextNode(String.valueOf(d.getBugFix())));
				commit.appendChild(bFN);
				
				Element nFN = doc.createElement("NewFeatureNumber");
				nFN.appendChild(doc.createTextNode(String.valueOf(d.getNewFeature())));
				commit.appendChild(nFN);
				
				Element rN = doc.createElement("RefactorNumber");
				rN.appendChild(doc.createTextNode(String.valueOf(d.getRefactor())));
				commit.appendChild(rN);
				
				Element rS = doc.createElement("RatioSize");
				rS.appendChild(doc.createTextNode(String.valueOf(d.getRatioSize())));
				commit.appendChild(rS);
				
				Element cI = doc.createElement("ColorIntensity");
				cI.appendChild(doc.createTextNode(String.valueOf(d.getColorIntensity())));
				commit.appendChild(cI);
				
				Element numRefs = doc.createElement("NumRefs");
				numRefs.appendChild(doc.createTextNode(String.valueOf(d.getNewFeature())));
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
