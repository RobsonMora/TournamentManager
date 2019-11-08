package view;

import java.awt.Dimension;

import javax.swing.JInternalFrame;

public class Ranking extends JInternalFrame {

	
	
	public Ranking() {
		
		setSize(550, 184);
		setTitle("Ranking");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);
	}
	
	
	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
	
}
