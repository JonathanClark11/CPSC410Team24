using UnityEngine;
using System.Collections;
//using System;

public class Drop : MonoBehaviour {

	private ParticleSystem ps = new ParticleSystem();
	//private DateTime dt = DateTime.Now;
	private int c = 0;
	
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
<<<<<<< HEAD
		//Debug.Log ("Current Date: " + Date.dt.ToLongDateString() + " " + Date.dt.ToLongTimeString());
		//Debug.Log ("NEXT DATE: " + timeToStopAt.ToLongDateString() + " " + timeToStopAt.ToLongTimeString());
		if (Date.dt >= timeToStopAt) {
			Dictionary<string,string> commit = commits[stopIndex];
			byte color =  (byte)Convert.ToDouble(commit["ColorIntensity"]);
			float size =  (float)Convert.ToDouble(commit["RatioSize"]);
			particleSystem.Emit(new Vector3(UnityEngine.Random.Range(-4.25f, 4.25f),UnityEngine.Random.Range(-1.5f, 1.5f), 0), new Vector3(0, 0, 25), size/45, 10f, new Color32(color, color, color, 255));
			
			//Update the index to the next commit
			stopIndex++;
			if (stopIndex > commits.Count) {
				Application.Quit(); //reached the end of commits
			}
			timeToStopAt = DateTime.Parse(commits[stopIndex]["Date"]);
=======
		//GameObject o = GameObject.Find("Date");
		//GUIText t = o.GetComponent<GUIText>();
		if(c == 0){
			//for(int i = 0; i < XMLParser.drops.length; i++) {
			//drops.get(i)
			//}
			if(Date.dt.Hour == 3 || Date.dt.Hour == 10 || Date.dt.Hour == 12 || Date.dt.Hour == 17 || Date.dt.Hour == 21) {
			byte color = (byte)Random.Range(0, 255);
				particleSystem.Emit(new Vector3(Random.Range(-4.25f, 4.25f),Random.Range(-1.5f, 1.5f), 0), new Vector3(0, 0, 25), Random.Range(0.1f, 4f), 10f, new Color32(color, color, color, 255));
				c++;
			}	
>>>>>>> parent of 21f7150... Unity Drops working
		}
		if(Date.dt.Hour != 3 && Date.dt.Hour != 10 && Date.dt.Hour != 12 && Date.dt.Hour != 17 && Date.dt.Hour != 21) {c = 0;}
	}
}
