package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import dao.GanhadorDAO;
import dao.TimeDAO;
import dao.TorneioPartidaDAO;
import dao.TorneioTimeDAO;
import model.GanhadorModel;
import model.TimeModel;
import model.TorneioPartidaModel;

@SuppressWarnings("serial")
public class TorneioAndamento  extends JInternalFrame {

	private JButton btnGerarFase, btnCarregarTorneio;
	private JTextField txtTorneio;

	private ArrayList<TimeModel> times;
	private TorneioPartidaDAO torneioPartidaDAO;
	private TorneioTimeDAO torneioTimeDAO;
	private GanhadorDAO ganhadorDAO;
	private TimeDAO timeDAO;

	private Container chaveContainer;


	private ArrayList<TorneioPartidaModel> ultimaFasePartidas;
	private int ultimaFase = 0;


	public TorneioAndamento(Connection conn){

		setSize(1200, 800);
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

		btnCarregarTorneio = new JButton(new AbstractAction("Carregar Torneio") {

			@Override
			public void actionPerformed(ActionEvent e) {

				ultimaFasePartidas = null;
				if(!txtTorneio.getText().isEmpty()) {						
					carregaFases(Integer.parseInt(txtTorneio.getText()));					
				}

			}
		});
		btnCarregarTorneio.setBounds(70,10, 140, 25);
		getContentPane().add(btnCarregarTorneio);

		btnGerarFase = new JButton(new AbstractAction("Gera Prox. Fase") {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(!txtTorneio.getText().isEmpty()) {					
					gerarFase(Integer.parseInt(txtTorneio.getText()));					
				}

			}
		});
		btnGerarFase.setBounds(10, 40, 200, 25);
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
		
	}

	private void cleanPaint() {

		paint(getGraphics());		

	}

	private void createSquare(TimeModel time, String pontos, int x, int y) {

		Graphics g = getGraphics();
		g.setColor(new Color(153,255,153));
		g.fillRect(x, y, 160, 30);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 160, 30);
		BufferedImage image;
		printText((time.getNome() + "               ").substring(0, 15), x + 40, y + 18, null);
		try {
			image = ImageIO.read(new File(System.getProperty("user.dir") + "\\images\\Logos\\icone.png"));
			g.drawImage(image, x+3, y+3, 25, 25, null);
			if(!pontos.equals("Winner")) {
				printText(pontos, x + 140, y + 18, new Font("Lemon/Milk", Font.PLAIN, 10));
			}else {
				image = ImageIO.read(new File(System.getProperty("user.dir") + "\\images\\icons\\award.png"));
				g.drawImage(image, x + 120, y+3, 25, 25, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		

	}

	private void createLines(int x, int y1, int y2) {

		Graphics g = getGraphics();
		int passo = 30;
		x = x + 160;
		if(y2 > 0) {
			g.drawLine(x, y1, x + passo, y1);
			g.drawLine(x, y2, x + passo, y2);
			g.drawLine(x + passo, y1, x + passo , y2);
			g.drawLine(x + passo, ((y2 - y1) / 2) + y1, x + passo + passo, ((y2 - y1) / 2) + y1);
		}else {
			g.drawLine(x, y1, x + passo + passo , y1);			
		}

	}

	private void printText(String text, int x, int y, Font fonte) {
		if(getGraphics() instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D)getGraphics();
			
			if(fonte !=  null) {
			g2.setFont(new Font("Lemon/Milk", Font.PLAIN, 10));
			}
			else {
			g2.setFont(new Font("Lemon/Milk", Font.PLAIN, 10));
			}
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
			
			g2.drawString(text, x, y); 
		}		
	}

	@SuppressWarnings("unchecked")
	private void carregaFases(Integer idTorneio) {

		ArrayList<TorneioPartidaModel> partidas = null, proxPartidas = null;

		cleanPaint();

		int j = 0;
		int x = 0;
		int passoVertical = 0;
		int y1 = 0, y2 = 0;
		boolean aux = true;
		int escala = 0, escalaPassoVertical = 0;

		for(int i = 1; i < 6; i++) {

			try {
				partidas = (proxPartidas == null) ? torneioPartidaDAO.getAllTorneioPartidas(idTorneio, i) : (ArrayList<TorneioPartidaModel>)proxPartidas.clone();					
				if(partidas != null) {
					proxPartidas = torneioPartidaDAO.getAllTorneioPartidas(idTorneio, i + 1);	

					if(partidas.size()>0) {
						ultimaFasePartidas = (ArrayList<TorneioPartidaModel>)partidas.clone();
					}

					ultimaFase = i;
					x = ((ultimaFase - 1) * 220) + 20;
					j = j + 120;
					escalaPassoVertical = Math.max(escalaPassoVertical * 2, 1);
					passoVertical = escalaPassoVertical * 86;
					y1 = 0;
					y2 = 0;
					aux = true;

					for(TorneioPartidaModel partida : partidas) {

						if(aux) {
							y1 = j + 30;
							y2 = 0;
						}else {
							y2 = j + 30;							
						}

						createSquare(timeDAO.getOneTime(partida.getIdTime1()), partida.getPontos1().toString(), x, j);
						createSquare(timeDAO.getOneTime(partida.getIdTime2()), partida.getPontos2().toString(), x, j + 30);

						if(proxPartidas!=null && !aux) {
							createLines(x, y1, y2);
						}

						aux = !aux; 
						j = j + passoVertical;					

					}

					if((partidas.size() == 1) && ((partidas.get(0).getPontos1() + partidas.get(0).getPontos2() > 0))) {

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
						createSquare(timeDAO.getOneTime(ganhador.getIdTime()), "Winner", ((ultimaFase * 220) + 20), j);
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

				ResultSet result = torneioPartidaDAO.freeSqlQuery(" select 1 as valor from torneio_partidas where pontos_1 is null and fase = "+ultimaFasePartidas.get(0).getFase());
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
