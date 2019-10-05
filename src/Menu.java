import java.awt.*;

import java.awt.event.*;
import java.text.ParseException;

import javax.swing.*;

import dao.BaseDAO;
import dao.ConexaoDAO;

@SuppressWarnings("serial")
public class Menu extends JFrame {

	// Painéis e items do menu
	public JDesktopPane desktopPane;
	private JMenuBar menuBar;
	private JMenu faturas, faturamento, matricular, sistema, torneios, processos, utilitarios, ajuda;
	private JMenuItem alunos, gerarF, realizaP, consultarF, planos, usuarios, sair, novo_torneio, torneio_andamento,
			utility, help, faturasemaberto, faturaspagas, backupRestore, testeConexao;
	// Classes/frames
	private Usuarios fUsuario;
	private Sair fSair;
	private CadastroAlunos fCadastroAlunos;
	private TorneioAndamento fTorneioAndamento;
	private Planos fPlanos;
	private MatricularAlunos fMatricularAlunos;
	private Utilitarios fUtilitarios;
	private Ajuda fAjuda;
	private ConsultarFaturas fConsultarFaturas;
	private PagamentosFaturas fPagamentosFaturas;
	private NovoTorneio fNovoTorneio;
	private BuscarAluno fBuscarAluno;
	private BackupRestore fBackupRestore;
	private String perfil, usuario;

	private int janelaAberta = 0;

	public Menu(String Perfil, String Usuario) {
		usuario = Usuario;
		perfil = Perfil;

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

		desktopPane = new JDesktopPane();
		
		testeConexao = new JMenuItem("Testar Conexão");
		testeConexao.addActionListener(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ConexaoDAO();
					JOptionPane.showMessageDialog(null, "Conectado com sucesso!");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Conexão falhou! verifique o servidor ou usuario");
				}
				
			}
		});

		usuarios = new JMenuItem("Usuários");
		usuarios.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 1;

				try {
					fUsuario = new Usuarios();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				desktopPane.add(fUsuario);
				fUsuario.setVisible(true);
				fUsuario.setPosicao();
			}
		});
		
		
		
		sair = new JMenuItem("Sair");		
		sair.addActionListener(new ActionListener() {

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

			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 3;

				fNovoTorneio = new NovoTorneio();
				desktopPane.add(fNovoTorneio);
				fNovoTorneio.setVisible(true);
				fNovoTorneio.setPosicao();

			}
		});

		torneio_andamento = new JMenuItem("Torneios em andamento");
		torneio_andamento.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				fecharJanelaAberta();
				janelaAberta = 4;

				fTorneioAndamento = new TorneioAndamento();
				desktopPane.add(fTorneioAndamento);
				fTorneioAndamento.setVisible(true);
				fTorneioAndamento.setPosicao();

			}
		});

		/*
		 * planos = new JMenuItem("Planos"); planos.addActionListener(new
		 * ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 5;
		 * 
		 * fPlanos = new Planos(); desktopPane.add(fPlanos); fPlanos.setVisible(true);
		 * fPlanos.setPosicao(); } });
		 * 
		 * alunos = new JMenuItem("Alunos"); alunos.addActionListener(new
		 * ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 6;
		 * 
		 * fMatricularAlunos = new MatricularAlunos();
		 * desktopPane.add(fMatricularAlunos); fMatricularAlunos.setVisible(true);
		 * fMatricularAlunos.setPosicao();
		 * 
		 * } });
		 * 
		 * faturamento = new JMenu("Faturamento"); faturamento.addActionListener(new
		 * ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 7;
		 * 
		 * fFaturamento = new Faturamento(); fFaturamento.setVisible(true);
		 * desktopPane.add(fFaturamento); } });
		 * 
		 * faturas = new JMenu("Faturas"); faturas.addActionListener(new
		 * ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 9;
		 * 
		 * fFaturas = new Faturas(); fFaturas.setVisible(true);
		 * desktopPane.add(fFaturas);
		 * 
		 * } }); utility = new JMenuItem("Utilitários"); utility.addActionListener(new
		 * ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 10;
		 * 
		 * fUtilitarios = new Utilitarios(); desktopPane.add(fUtilitarios);
		 * fUtilitarios.setVisible(true); fUtilitarios.setPosicao();
		 * 
		 * 
		 * } });
		 * 
		 * help = new JMenuItem("Ajuda"); help.addActionListener(new ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 11;
		 * 
		 * fAjuda = new Ajuda(); desktopPane.add(fAjuda); fAjuda.setVisible(true);
		 * fAjuda.setPosicao();
		 * 
		 * } });
		 * 
		 * matricular = new JMenu("Matricular"); matricular.addActionListener(new
		 * ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 12;
		 * 
		 * fMatricularAlunos = new MatricularAlunos();
		 * fMatricularAlunos.setVisible(true); desktopPane.add(fMatricularAlunos); } });
		 * 
		 * gerarF = new JMenuItem("Gerar Fatura"); gerarF.addActionListener(new
		 * ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 13;
		 * 
		 * fGerarFaturas = new GerarFaturas(); desktopPane.add(fGerarFaturas);
		 * fGerarFaturas.setVisible(true); fGerarFaturas.setPosicao(); } });
		 * 
		 * consultarF = new JMenuItem("Consultar Fatura");
		 * consultarF.addActionListener(new ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 14;
		 * 
		 * fConsultarFaturas = new ConsultarFaturas();
		 * desktopPane.add(fConsultarFaturas); fConsultarFaturas.setVisible(true);
		 * fConsultarFaturas.setPosicao(); } });
		 * 
		 * realizaP = new JMenuItem("Realiza Pagamento"); realizaP.addActionListener(new
		 * ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 15;
		 * 
		 * fPagamentosFaturas = new PagamentosFaturas();
		 * desktopPane.add(fPagamentosFaturas); fPagamentosFaturas.setVisible(true);
		 * fPagamentosFaturas.setPosicao(); } });
		 * 
		 * faturasemaberto = new JMenuItem("Faturas Em Aberto");
		 * faturasemaberto.addActionListener(new ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 17;
		 * 
		 * fFaturasAberto = new FaturasAberto(); fFaturasAberto.setVisible(true);
		 * desktopPane.add(fFaturasAberto); } });
		 * 
		 * faturaspagas = new JMenuItem("Faturas Pagas");
		 * faturaspagas.addActionListener(new ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 18;
		 * 
		 * fFaturasPagas = new FaturasPagas(); fFaturasPagas.setVisible(true);
		 * desktopPane.add(fFaturasPagas); } });
		 * 
		 * backupRestore = new JMenuItem("Backup"); backupRestore.addActionListener(new
		 * ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent evt) {
		 * 
		 * fecharJanelaAberta(); janelaAberta = 19;
		 * 
		 * fBackupRestore = new BackupRestore(); desktopPane.add(fBackupRestore);
		 * fBackupRestore.setVisible(true); fBackupRestore.setPosicao();
		 * 
		 * } });
		 */

		setContentPane(desktopPane);

		menuBar = new JMenuBar();
		sistema = new JMenu("Sistema");

		if (perfil.equals("Completo"))
			sistema.add(usuarios);
		sistema.add(sair);
		sistema.add(testeConexao);
		menuBar.add(sistema);

		torneios = new JMenu("Torneios");

		torneios.add(novo_torneio);
		torneios.add(torneio_andamento);

		menuBar.add(torneios);

		processos = new JMenu("Processos");

		/*
		 * matricular.add(alunos);
		 * 
		 * 
		 * faturamento.add(gerarF); faturamento.add(consultarF);
		 * faturamento.add(realizaP);
		 * 
		 * if(perfil.equals("Matricular") || perfil.equals("Completo"))
		 * processos.add(matricular);
		 * 
		 * if(perfil.equals("Financeiro") || perfil.equals("Completo"))
		 * processos.add(faturamento); menuBar.add(processos);
		 * 
		 * utilitarios = new JMenu("Utilitários"); utilitarios.add(backupRestore);
		 * utilitarios.add(utility);
		 * 
		 * menuBar.add(utilitarios);
		 * 
		 * ajuda = new JMenu("Ajuda"); ajuda.add(help);
		 * 
		 * menuBar.add(ajuda);
		 */

		setJMenuBar(menuBar);
		setTitle("Liu Kang Academy System");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
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

	/*
	 * public Container CreateContentPane() {
	 * 
	 * JPanel contentPane = new JPanel(new BorderLayout()); desktopPane = new
	 * JDesktopPane() { Image im = (new
	 * ImageIcon("Images/background/liukang.jpg")).getImage();
	 * 
	 * public void paintComponent(Graphics g) { g.drawImage(im, 0, 0, getWidth(),
	 * getHeight(), this);
	 * 
	 * } };
	 * 
	 * contentPane.setOpaque(true); contentPane.add(desktopPane); return
	 * contentPane; }
	 */

	/*
	 * public void FramePrincipal() {
	 * 
	 * fControleAlunos = new ControleAlunos(this); desktopPane.add(fControleAlunos);
	 * fControleAlunos.setVisible(true); fControleAlunos.setPosicao();
	 * 
	 * }
	 */

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

		case 5:
			fPlanos.dispose();
			break;

		case 6:
			fMatricularAlunos.dispose();
			break;

		case 7:
			//fFaturamento.dispose();
			break;

		case 9:
//			fFaturas.dispose();
			break;
		case 10:
			fUtilitarios.dispose();
			break;
		case 11:
			fAjuda.dispose();
			break;
		case 12:
			fMatricularAlunos.dispose();
			break;
		case 13:
//			fGerarFaturas.dispose();
			break;
		case 14:
			fConsultarFaturas.dispose();
			break;
		case 15:
			fPagamentosFaturas.dispose();
			break;
		case 16:
			fNovoTorneio.dispose();
			break;
		case 17:
//			fFaturasAberto.dispose();
			break;
		case 18:
//			fFaturasPagas.dispose();
			break;
		case 20:
			fBuscarAluno.dispose();
			break;

		default:
			break;
		}
	}

	public static void main(String args[]) {

		new Menu("Completo", "admin");
	}
}