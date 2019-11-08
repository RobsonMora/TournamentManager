package view;

import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;

import cadastro.CadastroCategorias;
import cadastro.CadastroJogos;
import cadastro.CadastroTimes;
import cadastro.CadastroTorneios;
import database.ConnectionFactory;

@SuppressWarnings("serial")
public class Menu extends JFrame {

	// Painéis e items do menu
	public JDesktopPane desktopPane;
	public JLayeredPane layeredPane;

	private JMenuBar menuBar;
	private JMenu sistema, cadastros;
	private JMenuItem sair, novo_torneio, times, jogos, categoria, testeConexao;
	// Classes/frames
	private Sair fSair;
	private TorneioAndamento fTorneioAndamento;
	private CadastroTorneios fNovoTorneio;
	private ControlePartidas fControlePartidas;
	private CadastroTimes fTimes;
	private CadastroJogos fJogos;
	private CadastroCategorias fCategoria;
	private Connection conn;
	private JButton btnBracket, btnControlePartida, btnRanking;
	private Ranking fRanking;
	
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

	public Menu() {

		try {

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.put("nimbusBase", new Color(255, 145, 77));
			UIManager.put("nimbusBlueGrey", new Color(255, 145, 77));
					
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block			e1.printStackTrace();
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

		novo_torneio = new JMenuItem("Torneio");
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

		times = new JMenuItem("Times");
		times.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 5;

				fTimes = new CadastroTimes(conn);
				desktopPane.add(fTimes);
				fTimes.setVisible(true);
				fTimes.setPosicao();

			}
		});

		jogos = new JMenuItem("Jogos");
		jogos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 6;

				fJogos = new CadastroJogos(conn);
				desktopPane.add(fJogos);
				fJogos.setVisible(true);
				fJogos.setPosicao();

			}
		});

		categoria = new JMenuItem("Categorias");
		categoria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 7;

				fCategoria = new CadastroCategorias(conn);
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

		/*
		 * torneios = new JMenu("Torneios");
		 * 
		 * 
		 * torneios.add(torneio_andamento); torneios.add(controle_partidas);
		 * 
		 * menuBar.add(torneios);
		 */

		cadastros = new JMenu("Cadastros");

		cadastros.add(novo_torneio);
		cadastros.add(times);
		cadastros.add(jogos);
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
		setUndecorated(true);
		customizeMenuBar();
		setContentPane(CreateContentPane());
		ImageIcon imagemTituloJanela = new ImageIcon("images\\\\Logos\\\\icone.png");
		setIconImage(imagemTituloJanela.getImage());
		
		
		btnBracket = new JButton(null, new ImageIcon(System.getProperty("user.dir") + "\\images\\icons\\bracketf.png"));
		btnBracket.addActionListener(new ActionListener() {

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
		btnBracket.setBounds(10, 10, 170, 170);
		btnBracket.setBackground(Color.BLACK);
		btnBracket.setEnabled(true);
		btnBracket.setVisible(true);
		desktopPane.add(btnBracket);
		/* btnBracket.setVisible(true); */

		btnControlePartida = new JButton(null,
				new ImageIcon(System.getProperty("user.dir") + "\\images\\icons\\004.png"));
		btnControlePartida.addActionListener(new ActionListener() {

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
		btnControlePartida.setBounds(200, 10, 170, 170);
		btnControlePartida.setBackground(Color.BLACK);
		btnControlePartida.setEnabled(true);
		btnControlePartida.setVisible(true);
		desktopPane.add(btnControlePartida);
		
		btnRanking = new JButton(null,
				new ImageIcon(System.getProperty("user.dir") + "\\images\\icons\\008.png"));
		btnRanking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 4;

				fRanking = new Ranking();
				desktopPane.add(fRanking);
				fRanking.setVisible(true);
				fRanking.setPosicao();

			}
		});
		btnRanking.setBounds(390, 10, 170, 170);
		btnRanking.setBackground(Color.BLACK);
		btnRanking.setEnabled(true);
		btnRanking.setVisible(true);
		desktopPane.add(btnRanking);
		
		
		

		setVisible(true);
	}

	public Container CreateContentPane() {

		JPanel contentPane = new JPanel(new BorderLayout());
		try {
			desktopPane = new JDesktopPane() {
				Image im = ImageIO.read(new File(System.getProperty("user.dir") + "\\images\\Logos\\logohd.png"));

				public void paintComponent(Graphics g) {
					g.drawImage(im, 0, 0, getWidth(), getHeight(), this);

				}
			};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		contentPane.setOpaque(true);
		contentPane.add(desktopPane);
		return contentPane;
	}

	private void customizeMenuBar() {

		menuBar.setUI(new BasicMenuBarUI() {

			@Override
			public void paint(Graphics g, JComponent c) {
				g.setColor(new Color(255, 145, 77));
				g.fillRect(0, 0, c.getWidth(), c.getHeight());
			}

		});

		MenuElement[] menus = menuBar.getSubElements();

		for (MenuElement menuElement : menus) {

			JMenu menu = (JMenu) menuElement.getComponent();
			changeComponentColors(menu);
			menu.setOpaque(true);

			MenuElement[] menuElements = menu.getSubElements();

			for (MenuElement popupMenuElement : menuElements) {

				JPopupMenu popupMenu = (JPopupMenu) popupMenuElement.getComponent();
				popupMenu.setBorder(null);

				MenuElement[] menuItens = popupMenuElement.getSubElements();

				for (MenuElement menuItemElement : menuItens) {

					JMenuItem menuItem = (JMenuItem) menuItemElement.getComponent();
					changeComponentColors(menuItem);
					menuItem.setOpaque(true);

				}
			}
		}
	}

	private void changeComponentColors(Component comp) {
		comp.setBackground(new Color(255, 145, 77));
		comp.setForeground(Color.black);
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
			fJogos.dispose();
			break;

		case 7:
			fCategoria.dispose();
			break;

		default:

			break;
		}
	}

	public static void main(String args[]) {

		new Menu();
	}
}