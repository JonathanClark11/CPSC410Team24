using UnityEngine;
using System.Collections;
using System;
 
public class Date : MonoBehaviour {
	public static DateTime dt = new DateTime(2008, 1, 1, 6, 0, 0);
	private	int d, m, y, h;
	private int c = 0;
	private float timeClock = 0.0f;
	private float timeAnim = 0.0f;
	private float scaleClock = 0.5f;
	private float scaleAnim = 0.1f;
	private int angle = 0;
	
	void Start(){
		d = dt.Day;
		m = dt.Month;
		y = dt.Year;
		h = dt.Hour;
	}
	
	public static void setDT(DateTime time) {
		dt = time;
	}
	
	// Update is called once per frame
	void Update () {

		if(Time.time > timeAnim) { 
			angle += 3;
			GameObject.Find("animation").transform.rotation = Quaternion.Euler(angle, 90, -90);
			timeAnim += scaleAnim;
		}
		if(Time.time > timeClock) { 
			//angle += 15;
			//GameObject.Find("animation").transform.rotation = Quaternion.Euler(angle, 90, -90);
			h++;
			c++;
			timeClock += scaleClock;
		
			if(c == 1){
				if(h >  23) { 
					h = 0;
					d++;
				}
				if((m == 1 || m == 3 || m == 5 || m ==  7 || m ==  8 || m ==  10 || m ==  12) && d > 30) {
					d = 0;
					m++;
				}
				else if((m == 4 || m ==  6 || m ==  9 || m ==  11) && d > 29) {
					d = 0;
					m++;
				}
				else if(m == 2 && d > 27) {
					d = 0;
					m++;
				}
				if(m > 11) {
					m = 0;
					y++;
				}
				dt = new DateTime(y, m, d, h, 0, 0);
			}
		}
		else { c = 0;}		
		//if(DateTime.Now.Millisecond % 500 != 0) { c = 0;}

		GameObject.Find("date").GetComponent<GUIText>().text = d.ToString() + "/" + m.ToString() + "/" + y.ToString();
		guiText.text = h.ToString() + ":00" ;
	}
}
