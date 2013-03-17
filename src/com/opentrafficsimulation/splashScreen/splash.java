package com.opentrafficsimulation.splashScreen;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;

import com.opentrafficsimulation.gui.MainGUI;

public class splash extends JWindow {

    /**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	static boolean isRegistered;
    private static JProgressBar progressBar = new JProgressBar();
    private static splash execute;
    private static int count;
    private static Timer timer1;

    public splash() {

        Container container = getContentPane();
        container.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new javax.swing.border.CompoundBorder());
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(10, 10, 430, 250);
        panel.setLayout(null);
        container.add(panel);
        
        JLabel label = new JLabel("<html><body>" +
        		"<h1>Open Traffic Simulation</h1>" +
        		"<ul>" +
        		"<li>Abdalla Alkhalaf</li>"+
        		"<li>Mahmut Canga</li> " +
        		"<li>Malak Alamri</li>" +
        		"<li>Yamama Al-Naimi</li>" +
        		"<li>Jiayi Guo</li>" +
        		"<li>Bingjia Xie</li></ul>"+
        		"<p style=\"margin-top:20px\">7CCSMGPR - Group Project</p>"+
        		"</body></html>");
        label.setFont(new Font("Verdana", Font.BOLD, 11));
        label.setBounds(60, 0, 420, 200);
        panel.add(label);

        progressBar.setMaximum(150);
        progressBar.setBounds(25, 300, 400, 15);
        container.add(progressBar);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setVisible(true);
        loadProgressBar();
        
    }

    private void loadProgressBar() {
        ActionListener al = new ActionListener() {

            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                count++;

                progressBar.setValue(count);

                //System.out.println(count);

                if (count == 160) {
                    execute.setVisible(false);//swapped this around with timer1.stop()
                   // execute.show(false);
                    MainGUI.getInstance().init();
                    timer1.stop();                    

                    //execute.setVisible();//swapped this around with timer1.stop()
                }

            }

    		//MainGUI.getInstance().init();

        };
        timer1 = new Timer(50, al);
        timer1.start();
    }

    public void main(String[] args) {
        execute = new splash();
        //execute.setVisible(false);
    }
    
    private static splash main= new splash();
    public static splash getInstance() {
		return main;
		
	}
}