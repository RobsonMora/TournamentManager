package view;

import java.awt.Dimension;
import java.sql.Connection;

public class CadastroJogos extends MasterDialogCad{
	
	
	private void create() {

		setSize(550,684);
		setTitle("Cadastro de Jogos");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);

	}
	
	public CadastroJogos(Connection conn) {
		
		super(conn);
	}
	
	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
	
}
