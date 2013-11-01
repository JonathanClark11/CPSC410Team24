using UnityEngine;
using System.Collections;
using System.Xml;

public class XMLParser : MonoBehaviour
{

    Droplet<string, string, string, string, string, string, string, string> obj; // this can't be right...
    List<Droplet> drops = new List<Droplet>();


    // Use this for initialization
    void Start()
    {
        GetDrop();
    }

    public void GetDrop() {

        string xmlfile = "/home/commits.xml";
		XMLDocument xmlDoc = new XmlDocument();  //xmlDoc is the new xml Document
		xmlDoc.LoadXml(xmlfile); // load the file
		XmlNodeList dropsList = xmlDoc.GetElementsByTagName("Commits"); // array of commits

        foreach (XmlNode drop in dropsList)
        {
            XmlNodeList droplet = drop.ChildNodes;
            obj = new Droplet<string, string, string, string, string, string, string, string>();

            foreach (XmlNode dropInfo in droplet)
            {

                if (dropInfo.Name == "Date")
                {
                    obj.Add("Date", dropInfo.InnerText);
                }
                if (dropInfo.Name == "User")
                {
                    obj.Add("User", dropInfo.InnerText);
                }
                if (dropInfo.Name == "Id")
                {
                    obj.Add("Id", dropInfo.InnerText);
                }
                if (dropInfo.Name == "Size")
                {
                    obj.Add("Size", dropInfo.InnerText);
                }
                if (dropInfo.Name == "Merge")
                {
                    obj.Add("Merge", dropInfo.InnerText);
                }
                if (dropInfo.Name == "Conflict")
                {
                    obj.Add("Conflict", dropInfo.InnerText);
                }
                if (dropInfo.Name == "BugFixNumber")
                {
                    obj.Add("DaBugFixNumberte", dropInfo.InnerText);
                }
                if (dropInfo.Name == "NumRefs")
                {
                    obj.Add("NumRefs", dropInfo.InnerText);
                }
            }
            drops.Add(obj);
        }
	}

}
