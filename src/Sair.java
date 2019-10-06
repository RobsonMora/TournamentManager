
import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Sair extends JInternalFrame {

	public Sair() {
		if (JOptionPane.showConfirmDialog(null, "Deseja realmente sair do sistema?", "Aviso",
				JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
			System.exit(EXIT_ON_CLOSE);
		} else {
		}
	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
