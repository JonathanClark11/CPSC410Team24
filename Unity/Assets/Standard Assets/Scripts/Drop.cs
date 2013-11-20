using UnityEngine;
using System.Collections;
using System;

public class Drop : MonoBehaviour {

	private ParticleSystem ps = new ParticleSystem();
	private DateTime dt = DateTime.Now;
	private int c = 0;
	
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		//GameObject o = GameObject.Find("Date");
		//GUIText t = o.GetComponent<GUIText>();
		if(c == 0){
			if(Date.dt.Hour == 3) {
				particleSystem.Emit(1);
				c++;
			}	
		}
		if(Date.dt.Hour != 3) {c = 0;}
	}
}
