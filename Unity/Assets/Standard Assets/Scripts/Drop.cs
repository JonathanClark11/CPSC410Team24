using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;

public class Drop : MonoBehaviour {

	private ParticleSystem ps = new ParticleSystem();
	private List<Dictionary<string, string>> commits;
	private Dictionary<string, int> leaderboard;
	private DateTime timeToStopAt;
	private DateTime firstCommitDate;
	private DateTime parsedFirstDate;
	private int stopIndex = 0;
	
	void Start () {
		leaderboard = new Dictionary<string, int>();
		commits = XMLParser.GetDrops();
		commits.Reverse();
		//Debug.Log ("--------------------------Commits Size: " + commits.Count);
		string firstdate = commits[0]["Date"];
		parsedFirstDate = DateTime.Parse (firstdate);
		firstCommitDate = new DateTime(parsedFirstDate.Year, parsedFirstDate.Month, parsedFirstDate.Day, 6, 0, 0);
		Date.setDT(firstCommitDate);
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
			particleSystem.Emit(new Vector3(UnityEngine.Random.Range(-4.25f, 4.25f),UnityEngine.Random.Range(-2f, 2f), 0), new Vector3(0, 0, 25), size/45, 10f, new Color32(color, color, color, 255));
			
			//update leaderboard
			if (leaderboard.ContainsKey(commit["User"]) == true) {
				leaderboard[commit["User"]] += Convert.ToInt32 (commit["Size"]);
			} else {
				leaderboard.Add(commit["User"], Convert.ToInt32 (commit["Size"]));
			}
			updateTopList();

			
			//Update the index to the next commit
			stopIndex++;
			if (stopIndex > commits.Count) {
				Application.Quit(); //reached the end of commits
			}
			timeToStopAt = DateTime.Parse(commits[stopIndex]["Date"]);
		}
	}
	
	public void updateTopList() {
		Dictionary<string, int> top3 = new Dictionary<string, int>(3);
		var items = from pair in leaderboard
		    orderby pair.Value descending
		    select pair;
		int counter = 0;
		foreach (KeyValuePair<string, int> pair in items)
		{
			if (counter > 3) { break; }
			top3.Add (pair.Key, pair.Value);
			counter++;
		}
		
		if (top3.Count > 0) {
			GameObject.Find("user1").GetComponent<GUIText>().text = top3.ElementAt(0).Key + " " + top3.ElementAt(0).Value;
		}
		if (top3.Count > 1) {
			GameObject.Find("user2").GetComponent<GUIText>().text = top3.ElementAt(1).Key + " " + top3.ElementAt(1).Value;
		}
		if (top3.Count > 2) {
			GameObject.Find("user3").GetComponent<GUIText>().text = top3.ElementAt(2).Key + " " + top3.ElementAt(2).Value;
		}
	}
}
