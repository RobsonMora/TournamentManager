package view;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Categoria extends MasterDialogCad {

	private JLabel LblCodigoID, LblCategoria;
	private JTextField txtFCodigoID, txtFCategoria;

	private void create() {

		setSize(550, 154);
		setTitle("Categorias");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);

	}

	public Categoria(Connection conn) {

		super(conn);
		create();
	}

	protected void subComponents() {

		LblCodigoID = new JLabel("Código da categoria:");
		LblCodigoID.setBounds(11, 20, 200, 100);
		getContentPane().add(LblCodigoID);
		
		LblCategoria = new JLabel("Nome da categoria:");
		LblCategoria.setBounds(18, 50, 200, 100);
		getContentPane().add(LblCategoria);	
		
		txtFCodigoID = new JTextField();
		txtFCodigoID.setBounds(140, 57, 387, 26);
		txtFCodigoID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					txtFCategoria.requestFocus();
				}

			}
		});
		getContentPane().add(txtFCodigoID);

		txtFCategoria = new JTextField();
		txtFCategoria.setBounds(140, 87, 387, 26);
		txtFCategoria.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					btnSave.doClick();
				}

			}
		});
		getContentPane().add(txtFCategoria);
		
		childContainer = getContentPane();
	
	}
	
	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
