import java.awt.Dimension;

import javax.swing.JInternalFrame;

public class FaturasAberto  extends JInternalFrame {

public void setPosicao() {
		
		Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
	
	}
	
}
