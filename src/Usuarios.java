
import java.awt.Dimension;

import javax.swing.JInternalFrame;

@SuppressWarnings("serial")
public class Usuarios extends JInternalFrame {



	public Usuarios() {

		setSize(600, 600);
		setTitle("Usuários");
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
