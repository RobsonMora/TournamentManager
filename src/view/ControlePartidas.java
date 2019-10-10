package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.JogoDAO;
import dao.TimeDAO;
import dao.TorneioDAO;
import dao.TorneioTimeDAO;

public class ControlePartidas extends JInternalFrame {

	private JLabel LblTorneio, LblPartida, LblVS;
	private JTextField txtFTorneio, txtFPartida, txtFtime1, txtFtime2;
	private JButton btnCarregar, btnAnterior, btnProximo, btnSalvar;
	private JPanel panel1, panel2;

	public ControlePartidas(Connection conn) {

		setSize(535, 535);
		setTitle("Controle de Partidas");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		Componnents();
		setVisible(true);
	}

	public void Componnents() {

		LblTorneio = new JLabel("Torneio");
		LblTorneio.setBounds(12, 0, 200, 100);
		getContentPane().add(LblTorneio);

		LblPartida = new JLabel();
		LblPartida.setBounds(10, 100, 500, 330);
		LblPartida.setBorder(BorderFactory.createTitledBorder("Partida"));
		getContentPane().add(LblPartida);

		LblVS = new JLabel("VS");
		LblVS.setBounds(250, 170, 200, 100);
		getContentPane().add(LblVS);

		txtFTorneio = new JTextField();
		txtFTorneio.setBounds(70, 40, 187, 26);
		getContentPane().add(txtFTorneio);

		txtFtime1 = new JTextField();
		txtFtime1.setBounds(32, 332, 195, 26);
		txtFtime1.disable();
		getContentPane().add(txtFtime1);

		txtFtime2 = new JTextField();
		txtFtime2.setBounds(292, 332, 195, 26);
		txtFtime2.disable();
		getContentPane().add(txtFtime2);

		btnCarregar = new JButton("Carregar");
		btnCarregar.setBounds(273, 40, 107, 26);
		getContentPane().add(btnCarregar);

		btnAnterior = new JButton("Anterior");
		btnAnterior.setBounds(75, 375, 107, 26);
		getContentPane().add(btnAnterior);

		btnProximo = new JButton("Próximo");
		btnProximo.setBounds(335, 375, 107, 26);
		getContentPane().add(btnProximo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(205, 450, 107, 26);
		getContentPane().add(btnSalvar);

		panel1 = new JPanel();
		panel1.setBounds(32, 130, 195, 180);
		panel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		getContentPane().add(panel1);

		panel2 = new JPanel();
		panel2.setBounds(292, 130, 195, 180);
		panel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		getContentPane().add(panel2);

	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
