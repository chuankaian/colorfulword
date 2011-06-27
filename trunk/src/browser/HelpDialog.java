package browser;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class HelpDialog extends JDialog{
	private TextArea text=new TextArea("Also See Documentation-_-y-~~~",5,10,3);
	private Button button=new Button("Confirm");
	HelpDialog(Dialog owner){
		super(owner,"Help",true);
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
		new HelpDialog(null);
	}
}
