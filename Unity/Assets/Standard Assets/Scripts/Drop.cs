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
		}
		if(Date.dt.Hour != 3 && Date.dt.Hour != 10 && Date.dt.Hour != 12 && Date.dt.Hour != 17 && Date.dt.Hour != 21) {c = 0;}
	}
}
