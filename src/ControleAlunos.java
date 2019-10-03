import java.awt.Dimension;

import javax.swing.JInternalFrame;



public class ControleAlunos  extends JInternalFrame {

	
		public ControleAlunos(Menu menu) {
		
		
		
		setSize(690, 538);
		setTitle("Controle de Alunos");
		setLayout(null);
		setResizable(false);
		setIconifiable(true);
		setVisible(true);

	}
	
	
	public void setPosicao() {
		
		Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
	
	}
	
}
