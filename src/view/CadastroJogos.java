package view;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CadastroJogos extends MasterDialogCad {

	private JLabel LblCodigoID, LblCategoria, LblJogo;
	private JTextField txtFCodigoID, txtFNome;
	private JComboBox<String> ComboJogo;

	private void create() {

		setSize(550, 184);
		setTitle("Cadastro de Jogos");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);

	}

	public CadastroJogos(Connection conn) {

		super(conn);
		create();
	}

	protected void subComponents() {

		LblCodigoID = new JLabel("Código do jogo:");
		LblCodigoID.setBounds(11, 20, 200, 100);
		getContentPane().add(LblCodigoID);

		LblCategoria = new JLabel("Nome do jogo:");
		LblCategoria.setBounds(18, 50, 200, 100);
		getContentPane().add(LblCategoria);

		txtFCodigoID = new JTextField();
		txtFCodigoID.setBounds(120, 57, 407, 26);
		txtFCodigoID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					txtFNome.requestFocus();
				}

			}
		});
		getContentPane().add(txtFCodigoID);

		txtFNome = new JTextField();
		txtFNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					ComboJogo.requestFocus();
				}

			}
		});
		txtFNome.setBounds(120, 87, 407, 26);

		getContentPane().add(txtFNome);

		LblJogo = new JLabel("Tipo de jogo:");
		LblJogo.setBounds(27, 80, 200, 100);
		getContentPane().add(LblJogo);

		ComboJogo = new JComboBox<String>();
		ComboJogo.addItem("--Selecione--");
		ComboJogo.setBounds(120, 117, 407, 26);
		ComboJogo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					btnSave.doClick();
				}

			}
		});
		getContentPane().add(ComboJogo);
		childContainer = getContentPane();

	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}

}
