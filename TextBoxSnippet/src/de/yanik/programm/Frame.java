package de.yanik.programm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


/*
 * Ein Code Snippet mit folgendem Sinn:
 * Wenn man in die Box A z.B. ein B einträgt, dann soll in die Box
 * B auch ein A eingetragen werden.
 * Sehr viel Error und User-Input Handling.
 * 
 * Zur Implementierung in "Anwendung zur Graphentheorie" vorgesehen
 */
public class Frame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	private char maxC;
	private int max;
	private JTextField[] textFields;
	private JLabel[] textLabels;
	
	private JButton finish;
	

	public Frame(int max) {
		this.setTitle("Input window for neighbours");
		this.max = max;
		
		switch(max) {
		case 1: maxC = 'A';
		break;
		
		case 2: maxC = 'B';
		break;
		
		case 3: maxC = 'C';
		break;
		
		case 4: maxC = 'D';
		break;
		
		case 5: maxC = 'E';
		break;
		
		case 6: maxC = 'F';
		break;
		}
		
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(max * 110, 140));
		
		// TODO change so it does not leave ~60 slots empty
		textFields = new JTextField[maxC + 1];
		
		textLabels = new JLabel[max];
		
		// TODO obergrenze variabel machen
		char ch = 'A';
		for(int i = 0; i < max; i++)
		{
			textLabels[i] = new JLabel("Neighbours of " + ch);
			textLabels[i].setPreferredSize(new Dimension(100, 20));
			this.add(textLabels[i]);
			ch++;
		}
		
		// TODO Obergrenze variable machen
		for(char c = 'A'; c <= maxC; c++)
		{
			textFields[c] = textFieldCreator(c);
			this.add(textFields[c]);
		}
		
		finish = new JButton("Done");
		finish.setPreferredSize(new Dimension(100, 35));
		finish.setVisible(true);
		finish.addActionListener(this);
		this.add(finish);
		
		this.pack();
		this.setVisible(true);
	}
	
	
	private boolean onEnter(char tbChar)
	{
		if(checkInput(textFields[tbChar].getText(), max, tbChar))
		{
			completeOtherFields(textFields[tbChar].getText(), tbChar);
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean inputCorrect = true;
		
		if(max >= 1 && e.getSource() == textFields['A']) 
			if(!onEnter('A'))
				inputCorrect = false;
			
			
		if(max >= 2 && e.getSource() == textFields['B']) 
			if(!onEnter('B'))
				inputCorrect = false;
			
			
		if(max >= 3 && e.getSource() == textFields['C']) 
			if(!onEnter('C'))
				inputCorrect = false;
			
			
		if(max >= 4 && e.getSource() == textFields['D']) 
			if(!onEnter('D'))
				inputCorrect = false;
		
		
		if(max >= 5 && e.getSource() == textFields['E']) 
			if(!onEnter('E'))
				inputCorrect = false;
		
		
		if(max >= 6 && e.getSource() == textFields['F']) 
			if(!onEnter('F'))
				inputCorrect = false;
		
		
		if(e.getSource() == finish) {
			for(char c = 'A'; c <= maxC; c++)
				if(!onEnter(c))
					inputCorrect = false;
			
			if(inputCorrect)
			{
				;//TODO content der textboxen übertragen
				try {
					Thread.sleep(500);		// simulated delay
				} catch (Exception e2) {
					// TODO: handle exception
				}
				this.dispose();
			}
			else
				System.out.println("Konnte nicht beenden, Fehler in einer der Textboxen");
		}
			

		// Am Ende prüfen, außerhalb, da sonst Dopplug
		if(!inputCorrect)
		{
			System.out.println("Input bitte überprüfen. Mögliche Fehlerquellen:");
			System.out.println(" - Es gibt gar nicht so viele Ecken/Knoten");
			System.out.println(" - Irgendwo sind Leerzeichen");
			System.out.println(" - Ein Buchstabe größer als " + maxC + " wurde eingegeben");
		}
		
	}
	
	
	/*
	 * Das Format des Inputs prüfen
	 */
	public boolean checkInput(String input, int nodesNum, char tbChar) {
		input = input.replaceAll(",", "");
		
		
		// Länge, Inhalt und auf whitespaces prüfen
		 
		if((input.length() >= nodesNum) || (input.contains(Character.toString(tbChar)) || (input.contains(" "))))
			return false;
		
		
		// Falls ein Buchstabe größer als 'F' eingegeben wurde
		// TODO verallgemeinern falls mehr als 6 Knoten möglich gemacht werden sollen
		for(int i = 0; i < input.length(); i++)
			if(input.charAt(i) > maxC)
				return false;
		
		return true;
	}
	
	
	/*
	 * Die anderen Felder ausfüllen, wenn z.B. A als Nachbarn
	 * B hat, dann hat B als Nachbarn auch A und somit muss in
	 * der Textbox von B auch A stehen
	 */
	public void completeOtherFields(String input, char tbNum) {
		input = input.replaceAll(",", "");
		
		// TODO replace F with highest NODE letter -> Obergrenze variabel machen
		for(char c = 'A'; c <= maxC; c++)
		{
			if((c != tbNum) && (input.contains(Character.toString(c))))
				if(!textFields[c].getText().contains(Character.toString(tbNum)))
					if(textFields[c].getText().equals(""))
						textFields[c].setText(tbNum + "");
					else
						textFields[c].setText(textFields[c].getText() +  "," + tbNum);	
				else
					;	// wenn Buchstabe schon vorhanden ist
		}
		
	}
	
	
	/*
	 * Textboxen erstellen
	 */
	public JTextField textFieldCreator(char letter) {
		JTextField tf;
		tf = new JTextField();
		tf.setPreferredSize(new Dimension(100,30));
		tf.addActionListener(this);
		
		return tf;
	}
	
	
}
