using UnityEngine;
using System.Collections;
using System;
 
public class Date : MonoBehaviour {
	public static DateTime dt;
	//private	int d, m, y, h;
	private int c = 0;
	private float timeClock = 0.0f;
	private float timeAnim = 0.0f;
	private float scaleClock = 0.1f;
	private float scaleAnim = 0.1f;
	private int angle = 0;
	
	void Start(){

	}
	
	public static void setDT(DateTime time) {
		dt = time;
	}
	
	// Update is called once per frame
	void Update () {

		if(Time.time > timeAnim) { 
			
			timeAnim += scaleAnim;
		}
		if(Time.time > timeClock) { 
			//angle += 15;
			//GameObject.Find("animation").transform.rotation = Quaternion.Euler(angle, 90, -90);
			angle += 15;
			GameObject.Find("animation").transform.rotation = Quaternion.Euler(angle, 90, -90);
			c++;
			timeClock += scaleClock;
		
			if(c == 1){
				dt = dt.AddHours(1);
				//Debug.Log(dt.Hour);
				/*
				if(h >  23) { 
					h = 0;
					d++;
				}
				if((m == 1 || m == 3 || m == 5 || m ==  7 || m ==  8 || m ==  10 || m ==  12) && d > 31) {
					d = 1;
					m++;
				}
				else if((m == 4 || m ==  6 || m ==  9 || m ==  11) && d > 30) {
					d = 1;
					m++;
				}
				else if(m == 2 && d > 28) {
					d = 1;
					m++;
				}
				Debug.Log(m);
				if(m > 12) {
					m = 1;
					y++;
				}
				dt = new DateTime(y, m, d, h, 0, 0);
				*/
			}
		}
		else { c = 0;}

		GameObject.Find("date").GetComponent<GUIText>().text = dt.Day + "/" + dt.Month + "/" + dt.Year;
		guiText.text = dt.Hour + ":00" ;
	}
}
