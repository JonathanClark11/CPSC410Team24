using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;

public class Drop : MonoBehaviour {

	private ParticleSystem ps = new ParticleSystem();
	private List<Dictionary<string, string>> commits;
	private DateTime timeToStopAt;
	private DateTime firstCommitDate;
	private int stopIndex = 0;
	
	void Start () {
		commits = XMLParser.GetDrops();
		commits.Reverse();
		//Debug.Log ("--------------------------Commits Size: " + commits.Count);
		string firstdate = commits[0]["Date"];
		firstCommitDate = DateTime.Parse (firstdate);
		//Date.setDT(firstCommitDate);

		timeToStopAt = firstCommitDate;
	}
	
	// Update is called once per frame
	void Update () {
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
		}
	}
}
