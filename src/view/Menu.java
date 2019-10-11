package view;

import java.awt.*;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;

import database.ConnectionFactory;

@SuppressWarnings("serial")
public class Menu extends JFrame {

	// Painéis e items do menu
	public JDesktopPane desktopPane;
	private JMenuBar menuBar;
	private JMenu sistema, torneios, cadastros;
	private JMenuItem sair, novo_torneio, torneio_andamento, controle_partidas, times, cadastro_jogos, categoria,
			testeConexao;
	// Classes/frames
	private Usuarios fUsuario;
	private Sair fSair;
	private TorneioAndamento fTorneioAndamento;
	private CadastroTorneios fNovoTorneio;
	private ControlePartidas fControlePartidas;
	private Times fTimes;
	private CadastroJogos fCadastroJogos;
	private Categoria fCategoria;
	private Connection conn;

	private int janelaAberta = 0;

	protected Connection connection() {
		conn = ConnectionFactory.getConnection("master", "admin", "admin");
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

		testeConexao = new JMenuItem("Testar Conexão");
		testeConexao.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					connection();
					JOptionPane.showMessageDialog(null, "Conectado com sucesso!");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Conexão falhou! verifique o servidor ou usuario");
				}

			}
		});

		sair = new JMenuItem("Sair");
		sair.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 1;

				fSair = new Sair();
				fSair.setVisible(true);
				desktopPane.add(fSair);
			}
		});

		novo_torneio = new JMenuItem("Cadastro de Torneio");
		novo_torneio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 2;

				try {
					fNovoTorneio = new CadastroTorneios(conn);
				} catch (Exception e) {
					System.out.println(" erro: " + e.getMessage());
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
				janelaAberta = 3;

				fTorneioAndamento = new TorneioAndamento(conn);
				desktopPane.add(fTorneioAndamento);
				fTorneioAndamento.setVisible(true);
				fTorneioAndamento.setPosicao();

			}
		});

		controle_partidas = new JMenuItem("Controle de Partidas");
		controle_partidas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 4;

				fControlePartidas = new ControlePartidas(conn);
				desktopPane.add(fControlePartidas);
				fControlePartidas.setVisible(true);
				fControlePartidas.setPosicao();

			}
		});

		times = new JMenuItem("Times");
		times.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 5;

				fTimes = new Times(conn);
				desktopPane.add(fTimes);
				fTimes.setVisible(true);
				fTimes.setPosicao();

			}
		});

		cadastro_jogos = new JMenuItem("Cadastro de Jogos");
		cadastro_jogos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 6;

				fCadastroJogos = new CadastroJogos(conn);
				desktopPane.add(fCadastroJogos);
				fCadastroJogos.setVisible(true);
				fCadastroJogos.setPosicao();

			}
		});

		categoria = new JMenuItem("Categorias");
		categoria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 7;

				fCategoria = new Categoria(conn);
				desktopPane.add(fCategoria);
				fCategoria.setVisible(true);
				fCategoria.setPosicao();

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
		torneios.add(controle_partidas);

		menuBar.add(torneios);

		cadastros = new JMenu("Cadastros");

		cadastros.add(times);
		cadastros.add(cadastro_jogos);
		cadastros.add(categoria);

		menuBar.add(cadastros);

		new JMenu("Processos");

		setJMenuBar(menuBar);
		setTitle("Tournament Manager");
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
			fSair.dispose();
			break;
		
		case 2:
			fNovoTorneio.dispose();
			break;

		case 3:
			fTorneioAndamento.dispose();
			break;

		case 4:
			fControlePartidas.dispose();
			break;
		
		case 5:
			fTimes.dispose();
			break;
		
		case 6:
			fCadastroJogos.dispose();
			break;
		
		case 7:
			fCategoria.dispose();
			break;
			
		default:

			break;
		}
	}

	public static void main(String args[]) {

		new Menu("Completo", "admin");
	}
}