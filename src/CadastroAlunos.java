import java.awt.Dimension;

import javax.swing.JInternalFrame;

public class CadastroAlunos  extends JInternalFrame {

	
	public CadastroAlunos() {
		
		setSize(690, 538);
		setTitle("Cadastro de Alunos");
		setLayout(null);
		setResizable(false);
		setClosable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
		setVisible(true);
		
	}
	
	
	
	
	
	public void setPosicao() {
		
		Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
	
	}
	
}
