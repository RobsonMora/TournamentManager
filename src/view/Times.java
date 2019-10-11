package view;
import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.JogoDAO;
import dao.TimeDAO;
import dao.TorneioDAO;
import dao.TorneioTimeDAO;

@SuppressWarnings("serial")
public class Times extends MasterDialogCad{
	
	private JLabel LblCodigoID, LblTime;
	private JTextField txtFCodigoID, txtFTime;
			
	private void create() {

		setSize(550,154);
		setTitle("Times");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);

	}
	
	public Times(Connection conn) {
		
		super(conn);
		create();
	}
	protected void subComponents() {
		
		LblCodigoID = new JLabel("Código do torneio:");
		LblCodigoID.setBounds(11, 20, 200, 100);
		getContentPane().add(LblCodigoID);
		
		LblTime = new JLabel("Nome do time:");
		LblTime.setBounds(33, 50, 200, 100);
		getContentPane().add(LblTime);	
		
		txtFCodigoID = new JTextField();
		txtFCodigoID.setBounds(130, 57, 397, 26);
		getContentPane().add(txtFCodigoID);

		txtFTime = new JTextField();
		txtFTime.setBounds(130, 87, 397, 26);
		getContentPane().add(txtFTime);
		
		
		childContainer = getContentPane();
	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
