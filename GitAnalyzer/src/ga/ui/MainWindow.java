package ga.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * This is the UI of the main window.
 * @author jonclark
 *
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
  public MainWindow() {
	setTitle("Git Analyzer");
	setSize(300,200); // default size is 0,0
	setLocation(10,200); // default is 0,0 (top left corner)
	
	initializeListeners();
	initializeUI();
  }
  
  /**
   * Inititalizes the listeners for our window
   * -Currently only supports closing the window
   */
  private void initializeListeners() {
	addWindowListener(new WindowAdapter() {
	  	public void windowClosing(WindowEvent e) {
		   System.exit(0);
	  	}
	});
  }
  
  /**
   * Intializes the UI, creates and sets the buttons and form elements.
   */
  private void initializeUI() {
	  //TODO: create UI
  }
}