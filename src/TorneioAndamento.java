import java.awt.Dimension;

import javax.swing.JInternalFrame;

public class TorneioAndamento  extends JInternalFrame {


	public TorneioAndamento(){

		setSize(600, 600);
		setTitle("Torneios em Andamento");
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
