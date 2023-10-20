import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class TestApplet extends Applet implements ActionListener {  
	
	Button btnStart, btnPlay1,btnPlay2;
	String message = "";
	int p1Score = 0;
	int p2Score = 0;
	Label label,lLastBall,lP1Score, lP2Score;
	boolean matchCompleted = false;
	
	public void init(){
	    
//	    Frame frame = new Frame();
//	    TextArea textArea = new TextArea("Hellow");
//	    textArea.setBounds(30,30,300,300);
//	    frame.add(textArea);
//	    frame.setSize(900,900);
//	    frame.setLayout(null);
//	    frame.setVisible(true);
//	    add(frame);
	    Font font = new Font("Courier", Font.BOLD ,17);
		
	    btnStart = new Button("Start Game");
	    btnPlay1 = new Button("Hit P1");
	    btnPlay2 = new Button("Hit P2");
	    label = new Label("Game Yet to Start");
	    lLastBall = new Label("");
	    lP1Score = new Label("");
	    lP2Score = new Label("");
	    label.setFont(font);
	    lLastBall.setFont(font);
	    label.setForeground(Color.green);
	    lP1Score.setForeground(Color.blue);
	    lP2Score.setForeground(Color.blue);
	    
	    
	    btnStart.setBounds(60,90,100,30);
	    btnPlay1.setBounds(180,90,100,30);
	    btnPlay2.setBounds(300,90, 100,30);
	    lLastBall.setBounds(60,140,200,30);
	    lP1Score.setBounds(60,180,200,30);
	    lP2Score.setBounds(60,220,200,30);
	    btnStart.setFont(font);
	    btnPlay1.setFont(font);
	    btnPlay2.setFont(font);
	    lP1Score.setFont(font);
	    lP2Score.setFont(font);
	    label.setBounds(50,40,400,40);
	    btnStart.addActionListener(this);
	    btnPlay1.addActionListener(this);
	    btnPlay2.addActionListener(this);
	    add(btnStart);
	    add(label);
	    add(btnPlay1);
	    add(btnPlay2);
	    add(lLastBall);
	    add(lP1Score);
	    add(lP2Score);
	    setLayout(null);
//	    
	}
	
	public void actionPerformed(ActionEvent e) {
		Button source = (Button)e.getSource();
		
		if(source.getLabel() == "Start Game") {
			label.setText("Game Started! Its Player 1s Turn");
			lP1Score.setText("Player 1 Score : Yet to Play");
			lP2Score.setText("Player 2 Score : Yet to Play");
			btnStart.setEnabled(false);
		}
		else if(source.getLabel() == "Hit P1") {
			int score = (int)Math.floor(Math.random() * (6 - 0 + 1) + 0);
			
			if(score == 0) {
				lLastBall.setText("On Last Ball Ooops Wicket!!");
				label.setText("Game Started! Its Player 2s Turn");
				lLastBall.setForeground(Color.red);
				btnPlay1.setEnabled(false);
			}
			else if(score == 5) {
				score--;
				lLastBall.setText("On Last Ball : "+ score + " runs");
			}
			else {
				lLastBall.setText("On Last Ball : "+ score + " runs");
			}
			p1Score += score;
			System.out.println("p1"+score);
			lP1Score.setText("Player 1 Score "+p1Score);
		}
		else {
			lLastBall.setForeground(Color.black);
			int score = (int)Math.floor(Math.random() * (6 - 0 + 1) + 0);
			
			if(score == 0) {
				lLastBall.setText("On Last Ball Ooops Wicket!!");
				lLastBall.setForeground(Color.red);
				btnPlay2.setEnabled(false);
				showResult();
			}
			else if(score == 5) {
				score--;
				lLastBall.setText("On Last Ball : "+ score  + " runs");
			}
			else {
				lLastBall.setText("On Last Ball : "+ score + " runs");
			}
			p2Score += score;
			System.out.println("p2"+score);
			lP2Score.setText("Player 1 Score "+p2Score);
			if(p2Score > p1Score) {
				matchCompleted = true;
				showResult();
			}
		}
	}
	public void initGame() {
		btnStart.setEnabled(true);
	}
	
	public void showResult() {
		String result;
		if(p1Score == p2Score) 
			result = "It's a DRAW!";
		else if(p1Score < p2Score) 
			result = "Player 2 WON!!";
		else 
			result = "Player 1 WON!!";
		
		DBHandler.insertEntry(p1Score, p2Score, result);
		Frame frame = new Frame("Game Status");
		frame.setSize(200,200);
		frame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });   
		
		Font font = new Font("Courier", Font.BOLD ,20);
		
		Button button = new Button("Ok!");
		button.setFont(font);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		            System.exit(0);
			}
		});
		Label label = new Label(result);
		label.setFont(font);
		label.setForeground(Color.black);
		frame.setLayout(new FlowLayout());
		Panel panel  = new Panel();
	    panel.setLayout(new FlowLayout());
	    frame.add(label);
	    frame.add(button);
	    frame.add(panel);
	    frame.setVisible(true);
	    
	}

	public void paint(Graphics g){  
			Color red = new Color(255,0,0);
			Font myFontBold = new Font("Courier", Font.BOLD ,20);
			g.setFont(myFontBold);
			g.setColor(red);
			g.drawString("2 Player Cricket Game", 30,30);
			g.drawString(message, 100,100);
			
	}  
	  
}  
/* 
<applet code="First.class" width="900" height="900"> 
</applet> 
*/  