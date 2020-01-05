/*
	JavaSwingCalendar.java
 
	Copyright (c) 2014 Arinyan Madobe
	
	This software is released under the MIT License.
	
	http://opensource.org/licenses/mit-license.php
*/

package JavaSwingCalendar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author 窓辺ありにゃん
 */
public class JavaSwingCalendar extends JFrame {

	public static void main(String[] args) {
		JavaSwingCalendar frame = new JavaSwingCalendar("カレンダー");
		frame.setVisible(true);
	}

	JavaSwingCalendar (String title) {
		

		setTitle(title);
		setSize(250, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel p = new JPanel();

		
	}


}