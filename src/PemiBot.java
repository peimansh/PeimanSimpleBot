import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PemiBot extends JFrame implements KeyListener  {
	JPanel panel = new JPanel ();
	JTextArea dialog = new JTextArea (20,50);
	JTextArea userInput = new JTextArea (1,50);
	JScrollPane scroll = new JScrollPane (
			dialog,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
			);

	String [][] chatbot = {
			//greeting1
			{"hey","hi","hej","hello","hola","hi bot","hello bot","hej bot","howdy"},
			{"Hi there","Hello !","Hi User!","Hey !"},
			//greeting2
			{"how are you","how r u","how r you","how are u","how are ya"},
			{"Fine!","Doing well!","Great!"},
			//tricky msg !
			{"peiman"},
			{"WOW ! How did you know my name ?!!"},
			//default response
			{"What !?","Sorry ?!","What did you say ?","!!!","I don't Understand."}
	};

	public static void main(String[] args) {
		new PemiBot();
	}

	public PemiBot() {
		super("Peiman Bot Test");
		setSize(600,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		dialog.setEditable(false);
		userInput.addKeyListener(this);

		panel.add(scroll);
		panel.add(userInput);
		panel.setBackground(new Color(147,112,219)); //purple color for frame (my favorite!)
		add(panel);

		setVisible(true);
	}

	public void keyPressed(KeyEvent e) {
		try {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				userInput.setEditable(false);
				//grab the quote
				String quote = userInput.getText();
				userInput.setText("");
				addText("-> You: \t"+quote);
				quote = quote.trim();
				while (//there is punctuation
						quote.charAt(quote.length()-1)=='!' ||
						quote.charAt(quote.length()-1)=='.' ||
						quote.charAt(quote.length()-1)=='?'
						) {
					//take away the punctuation
					quote = quote.substring(0,quote.length()-1);
				}
				quote = quote.trim();
				byte response = 0;
				/*
				 * 0: we're searching through chatbot for matches
				 * 1: we didn't find anything in chatbot
				 * 2: we did find something in chatbot
				 */
				//check for matches
				int j = 0; //which group we're checking
				while (response == 0) {
					if (inArray(quote.toLowerCase(),chatbot[j*2])) {
						response = 2;
						int r = (int)Math.floor(Math.random()*chatbot[(j*2)+1].length);
						addText("\n-> Pemi: \t"+chatbot[(j*2)+1][r]);
					}
					j++;
					if(j*2==chatbot.length-1 && response == 0) {
						response = 1;
					}
				}
				//if no match > default
				if (response == 1) {
					int r = (int)Math.floor(Math.random()*chatbot[chatbot.length-1].length);
					addText("\n-> Pemi: \t"+chatbot[chatbot.length-1][r]);
				}
				addText("\n");
			}
		}
		catch (StringIndexOutOfBoundsException ex) {
			addText("\n-> Pemi: \tSay Something !");
			addText("\n");
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			userInput.setEditable(true);
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void addText(String str) {
		dialog.setText(dialog.getText()+str);
	}

	public boolean inArray(String in,String[] str) {
		boolean match = false;
		for (int i =0;i < str.length;i++) {
			if(str[i].equals(in)) {   //contains can be used to for better compatibility
				match = true;
			}
		}
		return match;
	}
}
