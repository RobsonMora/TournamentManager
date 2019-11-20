package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import Buscas.BuscarTorneio;
import dao.GanhadorDAO;
import dao.JogoDAO;
import dao.TimeDAO;
import dao.TorneioDAO;
import dao.TorneioPartidaDAO;
import dao.TorneioTimeDAO;
import model.GanhadorModel;
import model.TimeModel;
import model.TorneioModel;
import model.TorneioPartidaModel;

@SuppressWarnings("serial")
public class TorneioAndamento  extends JInternalFrame {

	private JButton btnGerarFase, btnCarregarTorneio, btnBuscar;
	private JTextField txtTorneio, txtLoadTorneio, txtLoadJogo;
	private JTextArea txtAObs;
	private JLabel lblPartida, lblShowTorneio, lblJogo, lblObs;
	private ArrayList<TimeModel> times;
	private TorneioPartidaDAO torneioPartidaDAO;
	private TorneioTimeDAO torneioTimeDAO;
	private GanhadorDAO ganhadorDAO;
	private TimeDAO timeDAO;
	private TorneioDAO torneioDAO;
	private BuscarTorneio busca;

	private Container chaveContainer;


	private ArrayList<TorneioPartidaModel> ultimaFasePartidas;
	private int ultimaFase = 0;


	public TorneioAndamento(Connection conn){

		setSize(1300, 700);
		setTitle("Torneios em Andamento");
		setLayout(null); 
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		for(Component c : getComponents()){
			if (c instanceof BasicInternalFrameTitlePane){
				for (MouseMotionListener m : c.getMouseMotionListeners()){
					c.removeMouseMotionListener(m);
				}
				break;
			}
		}
		BasicInternalFrameUI ui = (BasicInternalFrameUI) getUI();
		Container north = (Container)ui.getNorthPane();
		north.remove(0);
		north.validate();
		north.repaint();

		torneioDAO = new TorneioDAO(conn);
		torneioPartidaDAO = new TorneioPartidaDAO(conn);
		torneioTimeDAO = new TorneioTimeDAO(conn);
		ganhadorDAO = new GanhadorDAO(conn);
		timeDAO = new TimeDAO(conn);

		chaveContainer = new Container();
		getContentPane().add(chaveContainer);

		componentes();
		setVisible(true);		

	}

	private void componentes() {

		lblShowTorneio = new JLabel("Torneio:");
		lblShowTorneio.setBounds(21, 100, 100, 26);
		getContentPane().add(lblShowTorneio);

		lblJogo = new JLabel("Jogo:");
		lblJogo.setBounds(21, 160, 100, 26);
		getContentPane().add(lblJogo);

		lblObs = new JLabel("Observação:");
		lblObs.setBounds(21, 220, 100, 26);
		getContentPane().add(lblObs);

		lblPartida = new JLabel();
		lblPartida.setBounds(7, 80, 230, 400);
		lblPartida.setBorder(BorderFactory.createTitledBorder("Informações"));
		getContentPane().add(lblPartida);

		btnCarregarTorneio = new JButton(new AbstractAction("Carregar Torneio") {

			@Override
			public void actionPerformed(ActionEvent e) {

				ultimaFasePartidas = null;
				if(!txtTorneio.getText().isEmpty()) {

					carregarTorneio(Integer.parseInt(txtTorneio.getText()));

					carregaFases(Integer.parseInt(txtTorneio.getText()));
				}

			}
		});
		btnCarregarTorneio.setBounds(70,10, 164, 26);
		getContentPane().add(btnCarregarTorneio);
		
		
		btnBuscar = new JButton(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				busca = new BuscarTorneio(torneioDAO.getConn());
				busca.addWindowListener(new WindowAdapter() {
					
					@Override
					public void windowClosed(WindowEvent e) {
					}
					
				});
				
			}
		});
		btnBuscar.setBounds(0, 0, 0, 0);

		btnGerarFase = new JButton(new AbstractAction("Gera Prox. Fase") {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(!txtTorneio.getText().isEmpty()) {					
					gerarFase(Integer.parseInt(txtTorneio.getText()));					
				}

			}
		});
		btnGerarFase.setBounds(10, 45, 224, 26);
		getContentPane().add(btnGerarFase);

		txtTorneio = new JTextField();
		txtTorneio.setBounds(10, 10, 50, 25);
		txtTorneio.addFocusListener(new FocusAdapter() {
			public void focusGained(final FocusEvent pE) {

				txtTorneio.selectAll();
			}
		});		
		txtTorneio.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		getContentPane().add(txtTorneio);

		txtLoadTorneio = new JTextField();
		txtLoadTorneio.setBounds(21, 130, 200, 25);
		txtLoadTorneio.setEditable(false);
		txtLoadTorneio.setFocusable(false);
		getContentPane().add(txtLoadTorneio);

		txtLoadJogo = new JTextField();
		txtLoadJogo.setBounds(21, 190, 200, 25);
		txtLoadJogo.setEditable(false);
		txtLoadJogo.setFocusable(false);
		getContentPane().add(txtLoadJogo);

		txtAObs = new JTextArea();
		txtAObs.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {

				if(!txtAObs.getText().trim().isEmpty()) {
				}

			}

		});

		JScrollPane sp = new JScrollPane(txtAObs);
		sp.setEnabled(true);
		sp.setBounds(21, 250, 200, 212);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		getContentPane().add(sp);


	}

	private void cleanPaint() {

		paint(getGraphics());		

	}

	private void createSquare(TimeModel time, String pontos, int x, int y, boolean winner) {

		if(getGraphics() instanceof Graphics2D) {
			Graphics2D g = (Graphics2D)getGraphics();
			if(winner) {
				g.setColor(new Color(125,255, 77));				
			}else {
				g.setColor(new Color(255,145, 77));
			}
			g.fillRect(x, y, 160, 30);
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(2));
			g.drawRect(x, y, 160, 30);
			BufferedImage image;
			printText((time.getNome() + "               ").substring(0, 12), x + 40, y + 18);
			try {
				if(time.getLogo() == null) {
					image = ImageIO.read(new File(System.getProperty("user.dir") + "\\images\\Logos\\icone.png"));
				}else {
					image = ImageIO.read(time.getLogo());
				}
				g.drawImage(image, x+3, y+3, 25, 25, null);
				if(!pontos.equals("Winner")) {
					printText(pontos, x + 140, y + 18);
				}else {
					image = ImageIO.read(new File(System.getProperty("user.dir") + "\\images\\icons\\award.png"));
					g.drawImage(image, x + 130, y+3, 25, 25, null);
				}
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
	}

	private void createLines(int x, int y1, int y2) {

		if(getGraphics() instanceof Graphics2D) {
			Graphics2D g = (Graphics2D)getGraphics();
			int passo = 30, meio = (y2 - y1) / 2;
			x = x + 160;
			g.setStroke(new BasicStroke(3));
			if(y2 > 0) {			
				g.drawLine(x, y1, x + passo, y1);
				g.drawLine(x, y2, x + passo, y2);				
				g.drawLine(x + passo, meio + y1, x + passo + passo, meio + y1);
				g.drawLine(x + passo, y1, x + passo , y2);
			}else {
				g.drawLine(x, y1, x + passo + passo , y1);			
			}
		} 

	}

	private void carregarTorneio(Integer idTorneio) {

		try {
			TorneioModel torneio = torneioDAO.getOneTorneio(idTorneio);

			if(torneio != null) {
				txtLoadTorneio.setText(torneio.getNome());
				txtLoadJogo.setText(new JogoDAO(torneioDAO.getConn()).getOneJogo(torneio.getIdJogo()).getNome());
				txtAObs.setText(torneio.getObservacao());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	private void printText(String text, int x, int y) {
		if(getGraphics() instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D)getGraphics();
			g2.setFont(new Font("Lemon/Milk", Font.PLAIN, 12));
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	

			g2.drawString(text, x, y); 
		}		
	}

	@SuppressWarnings("unchecked")
	private void carregaFases(Integer idTorneio) {

		ArrayList<TorneioPartidaModel> partidas = null, proxPartidas = null;

		cleanPaint();

		int j = 0, y1 = 0, y2 = 0, x = 0, base = 0, passoVertical = 0, posVencedor = 0;
		boolean aux = true, partidaFinalizada = false;
		int escala = 0, escalaPassoVertical = 0;


		for(int i = 1; i < 6; i++) {

			try {
				partidas = (proxPartidas == null) ? torneioPartidaDAO.getAllTorneioPartidas(idTorneio, i) : (ArrayList<TorneioPartidaModel>)proxPartidas.clone();					
				if(partidas != null) {

					proxPartidas = torneioPartidaDAO.getAllTorneioPartidas(idTorneio, i + 1);	

					if(partidas.size()>0) {
						ultimaFasePartidas = (ArrayList<TorneioPartidaModel>)partidas.clone();
					}

					if(base == 0) {
						int borda = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane().getPreferredSize().height;
						base = borda + ((((this.getHeight() + borda) / 2) - ((partidas.size()/2) * 60)) - ((partidas.size()>1) ? ((partidas.size()/2) * 26) + 13 : 60));
					}
					ultimaFase = i;
					x = ((ultimaFase - 1) * 220) + 250;
					j = j + base;
					escalaPassoVertical = Math.max(escalaPassoVertical * 2, 1);
					passoVertical = escalaPassoVertical * 86;
					y1 = 0;
					y2 = 0;
					aux = true;
					partidaFinalizada = false;

					for(TorneioPartidaModel partida : partidas) {

						if(aux) {
							y1 = j + 30;
							y2 = 0;
						}else {
							y2 = j + 30;							
						}
						partidaFinalizada = isFinished(partida);						
						posVencedor = getPosVencedor(partida);

						createSquare(timeDAO.getOneTime(partida.getIdTime1()), (partidaFinalizada ? partida.getPontos1().toString() : " - "), x, j, posVencedor == 1);
						createSquare(timeDAO.getOneTime(partida.getIdTime2()), (partidaFinalizada ? partida.getPontos2().toString() : " - "), x, j + 30, posVencedor == 2);

						if(proxPartidas!=null && !aux) {
							createLines(x, y1, y2);
						}

						aux = !aux; 
						j = j + passoVertical;					

					}

					if((partidas.size() == 1) && partidaFinalizada) {

						createLines(x, y1, y2);

						j = (j - passoVertical) + 15;
						GanhadorModel ganhador = ganhadorDAO.getOneGanhador(idTorneio, 0);
						if(ganhador == null) {		
							ganhadorDAO.createGanhador(new GanhadorModel().setIdTime(getIdVencedor(partidas.get(0))).setIdTorneio(idTorneio));
							ganhador = ganhadorDAO.getOneGanhador(idTorneio, getIdVencedor(partidas.get(0)));							
						}else {
							if(ganhador.getIdTime() != getIdVencedor(partidas.get(0))) {
								ganhadorDAO.updateGanhador(ganhador.setIdTime(getIdVencedor(partidas.get(0))));
							}
						}
						createSquare(timeDAO.getOneTime(ganhador.getIdTime()), "Winner", ((ultimaFase * 220) + 250), j, true);
					}
					if(i > 1) {
						escala = (escala * 2) + 1;
					}
					j = ((73 * (escala + 1)) + (13 * escala)) - 30;

				}else {
					break;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	

	private void iniciaPartidas(Integer idTorneio) {
		try {

			times = torneioTimeDAO.getAllTimes(idTorneio);

			if(times!=null) {
				for(int i = 0; i <= (times.size() - 1); i = i + 2) {
					torneioPartidaDAO.createTorneioPartida(new TorneioPartidaModel()
							.setIdTorneio(idTorneio)							
							.setIdTime1(times.get(i).getId())
							.setIdTime2(times.get(i+1).getId())
							.setFase(1)
							);					
				}				
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	private boolean isFinished(TorneioPartidaModel partida) {

		return 	(partida.getPontos1() + partida.getPontos2()) > 0;
	}

	private int getPosVencedor(TorneioPartidaModel partida) {

		if(isFinished(partida)) {
			if(getIdVencedor(partida) == partida.getIdTime1()) {
				return 1;
			}else {
				return 2;
			}
		}

		return 0;
	}

	private Integer getIdVencedor(TorneioPartidaModel partida) {

		if(partida.getPontos1() > partida.getPontos2()) {
			return partida.getIdTime1();
		}else {
			return partida.getIdTime2();
		}

	}

	private void gerarFase(Integer idTorneio) {

		try {

			if(ultimaFasePartidas == null) {

				iniciaPartidas(idTorneio);

			}else {			

				ResultSet result = torneioPartidaDAO.freeSqlQuery(" select 1 as valor from torneio_partidas where id_torneio = "+idTorneio.toString()+" and pontos_1 is null and fase = "+ultimaFasePartidas.get(0).getFase());
				if(result.next()) {
					if(result.getInt(1) == 1) {
						return;
					}
				}

				TimeModel time1 = null, time2, timeAux;
				boolean aux = true;

				if(ultimaFasePartidas.size() > 1) {
					for(TorneioPartidaModel partida: ultimaFasePartidas) {

						timeAux = timeDAO.getOneTime(getIdVencedor(partida));
						if(aux) {
							time1 = new TimeModel(timeAux);
						}else {
							time2 = new TimeModel(timeAux);	

							if(time1!=null) {
								torneioPartidaDAO.createTorneioPartida(new TorneioPartidaModel()
										.setIdTorneio(idTorneio)							
										.setIdTime1(time1.getId())
										.setIdTime2(time2.getId())
										.setFase(partida.getFase()+1)
										);	
							}
						}
						timeAux = null;					
						aux = !aux;
					}
				}



			}

			carregaFases(idTorneio);

		} catch (SQLException e) {
			e.printStackTrace();
		} 		

	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}

}
