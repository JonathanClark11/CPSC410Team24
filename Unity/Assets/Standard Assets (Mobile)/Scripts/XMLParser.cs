/* using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System.Xml;

public class XMLParser : MonoBehaviour
{

	List<Dictionary<string,string>> drops = new List<Dictionary<string,string>>();
	Dictionary<string,string> obj;

    // Use this for initialization
    void Start()
    {
        GetDrop();
    }

    public void GetDrop() {

        string xmlfile = "/home/commits.xml";
		XmlDocument xmlDoc = new XmlDocument();  //xmlDoc is the new xml Document
		xmlDoc.LoadXml(xmlfile); // load the file
		XmlNodeList dropsList = xmlDoc.GetElementsByTagName("Commits"); // array of commits

        foreach (XmlNode drop in dropsList)
        {
            XmlNodeList droplet = drop.ChildNodes;
            obj = new Dictionary<string, string>();

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
 */