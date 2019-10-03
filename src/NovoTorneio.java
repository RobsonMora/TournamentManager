import java.awt.Dimension;

import javax.swing.JInternalFrame;


public class NovoTorneio extends JInternalFrame  {
		
	public NovoTorneio() {

		setSize(600, 600);
		setTitle("Novo Torneio");
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
