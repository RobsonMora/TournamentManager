package view;


import java.awt.*;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;

import database.ConnectionFactory;

@SuppressWarnings("serial")
public class Menu extends JFrame {

	// Pain�is e items do menu
	public JDesktopPane desktopPane;
	private JMenuBar menuBar;
	private JMenu sistema, torneios;
	private JMenuItem sair, novo_torneio, torneio_andamento, testeConexao;
	// Classes/frames
	private Usuarios fUsuario;
	private Sair fSair;
	private TorneioAndamento fTorneioAndamento;
	private NovoTorneio fNovoTorneio;
	private Connection conn;

	private int janelaAberta = 0;
	
	protected Connection connection() {
		conn = ConnectionFactory.getConnection
				(
					"master", 
					"admin", 
					"admin"
				);
		try {
			conn.setAutoCommit(true);
			System.out.println("Conectado com sucesso!");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Connection getConn() {
		return conn;
	}

	public Menu(String Perfil, String Usuario) {

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		connection();
		
		desktopPane = new JDesktopPane();

		testeConexao = new JMenuItem("Testar Conex�o");
		testeConexao.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					connection();
					JOptionPane.showMessageDialog(null, "Conectado com sucesso!");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Conex�o falhou! verifique o servidor ou usuario");
				}

			}
		});


		sair = new JMenuItem("Sair");		
		sair.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 2;

				fSair = new Sair();
				fSair.setVisible(true);
				desktopPane.add(fSair);
			}
		});

		novo_torneio = new JMenuItem("Novo Torneio");
		novo_torneio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 3;

				try {
					fNovoTorneio = new NovoTorneio(conn);				
				} catch (Exception e) {
					System.out.println(" erro: "+e.getMessage());
				}
				desktopPane.add(fNovoTorneio);
				fNovoTorneio.setVisible(true);
				fNovoTorneio.setPosicao();

			}
		});

		torneio_andamento = new JMenuItem("Torneios em andamento");
		torneio_andamento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 4;

				fTorneioAndamento = new TorneioAndamento(conn);
				desktopPane.add(fTorneioAndamento);
				fTorneioAndamento.setVisible(true);
				fTorneioAndamento.setPosicao();

			}
		});

		setContentPane(desktopPane);

		menuBar = new JMenuBar();
		sistema = new JMenu("Sistema");

		sistema.add(sair);
		sistema.add(testeConexao);
		menuBar.add(sistema);

		torneios = new JMenu("Torneios");

		torneios.add(novo_torneio);
		torneios.add(torneio_andamento);

		menuBar.add(torneios);

		new JMenu("Processos");


		setJMenuBar(menuBar);
		setTitle("Liu Kang Academy System");
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setBackground(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		/* setContentPane(CreateContentPane()); */
		/* FramePrincipal(); */
		ImageIcon imagemTituloJanela = new ImageIcon("Images/48x48/icon.jpg");
		setIconImage(imagemTituloJanela.getImage());
		setVisible(true);
		/* FramePrincipal(); */
	}


	public void mostrarJanela(boolean o) {

		setVisible(o);
	}

	private void fecharJanelaAberta() {
		switch (janelaAberta) {
		case 1:
			fUsuario.dispose();
			break;
		case 2:
			fSair.dispose();
			break;

		case 3:
			fNovoTorneio.dispose();
			break;

		case 4:
			fTorneioAndamento.dispose();
			break;
		case 16:
			fNovoTorneio.dispose();
			break;

		default:
			break;
		}
	}

	public static void main(String args[]) {

		new Menu("Completo", "admin");
	}
}