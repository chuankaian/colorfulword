package browser;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AboutDialog extends JDialog{
	private TextArea text=new TextArea("About: ColorfulWord\nAuthor: 刘智猷  张泰之  徐源盛  安传凯\nVersion：1.0b\n",5,10,3);
	private Button button=new Button("Confirm");
	AboutDialog(Dialog owner){
		super(owner,"About",true);
		setPreferredSize(new Dimension(300,200));
		text.setEditable(false);
		
		add(text);
		add(button,BorderLayout.SOUTH);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}

		});
		pack();
		setVisible(true);
	}
	public static void main(String[] argv){
		new AboutDialog(null);
	}
}
