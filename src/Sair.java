
import java.awt.Dimension;

import javax.swing.JInternalFrame;

@SuppressWarnings("serial")
public class Sair  extends JInternalFrame  {


	public Sair() {
		
		System.exit(EXIT_ON_CLOSE);
		
	}
	
	public void setPosicao() {
		
		Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
	
	}
	
}
