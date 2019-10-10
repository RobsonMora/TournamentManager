package view;

import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Categoria extends MasterDialogCad {

	private JLabel LblCodigoID, LblTime;
	private JTextField txtFCodigoID, txtFTime;

	private void create() {

		setSize(550, 684);
		setTitle("Categorias");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);

	}

	public Categoria(Connection conn) {

		super(conn);

	}

	protected void subComponents() {

		LblCodigoID = new JLabel("Nome do time:");
		LblCodigoID.setBounds(11, 20, 200, 100);
		getContentPane().add(LblCodigoID);
		
		LblTime = new JLabel("Código do torneio:");
		LblTime.setBounds(30, 50, 200, 100);
		getContentPane().add(LblTime);	
		
		txtFCodigoID = new JTextField();
		txtFCodigoID.setBounds(140, 57, 387, 26);
		getContentPane().add(txtFCodigoID);

		txtFTime = new JTextField();
		txtFTime.setBounds(140, 87, 387, 26);
		getContentPane().add(txtFTime);
	
	}
	
	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
