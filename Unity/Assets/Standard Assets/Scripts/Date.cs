using UnityEngine;
using System.Collections;
using System;
 
public class Date : MonoBehaviour {
	public static DateTime dt = new DateTime(2008, 1, 1, 0, 0, 0);
	private	int d, m, y, h;
	private int c = 0;
	private float time = 0.0f;
	private float scale = 0.5f;
	
	void Start(){
	d = dt.Day;
	m = dt.Month;
	y = dt.Year;
	h = dt.Hour;

	}
	
	// Update is called once per frame
	void Update () {

		//day++;
		
		if(Time.time > time) { 
			h++;
			c++;
			time += scale;
		
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

 
		guiText.text = d.ToString() + "/" + m.ToString() + "/" + y.ToString() + " - " + h.ToString() + ":00" ;
	}
}
