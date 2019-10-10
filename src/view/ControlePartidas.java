package view;

import java.awt.Color;
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

public class ControlePartidas extends JFrame {

	private JLabel LblTorneio, LblPartida, LblVS;
	private JTextField txtFTorneio, txtFPartida;
	private JButton btnCarregar, btnAnterior, btnProximo, btnSalvar;
	private JPanel panel1, panel2;

	public ControlePartidas() {

		setSize(535, 480);
		setTitle("Controle de Partidas");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		/* setClosable(true); */
		Componnents();
		setVisible(true);
	}

	public void Componnents() {

		LblTorneio = new JLabel("Torneio");
		LblTorneio.setBounds(12, 20, 200, 100);
		getContentPane().add(LblTorneio);

		LblPartida = new JLabel();
		LblPartida.setBounds(10, 100, 500, 269);
		LblPartida.setBorder(BorderFactory.createTitledBorder("Partida"));

		getContentPane().add(LblPartida);

		LblVS = new JLabel("VS");
		LblVS.setBounds(250, 170, 200, 100);
		getContentPane().add(LblVS);

		txtFTorneio = new JTextField();
		txtFTorneio.setBounds(70, 57, 187, 26);
		getContentPane().add(txtFTorneio);

		btnCarregar = new JButton("Carregar");
		btnCarregar.setBounds(273, 57, 107, 26);
		getContentPane().add(btnCarregar);

		btnAnterior = new JButton("Anterior");
		btnAnterior.setBounds(75, 320, 107, 26);
		getContentPane().add(btnAnterior);

		btnProximo = new JButton("Próximo");
		btnProximo.setBounds(335, 320, 107, 26);
		getContentPane().add(btnProximo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(205, 390, 107, 26);
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

	public static void main(String args[]) {

		new ControlePartidas();
	}
}
