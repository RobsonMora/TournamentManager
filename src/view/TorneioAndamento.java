package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import dao.GanhadorDAO;
import dao.TimeDAO;
import dao.TorneioPartidaDAO;
import dao.TorneioTimeDAO;
import model.TimeModel;
import model.TorneioPartidaModel;

@SuppressWarnings("serial")
public class TorneioAndamento  extends JInternalFrame {

	private JButton btnGerarFase, btnSalvarFase, btnCarregarTorneio;
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

		setSize(1400, 800);
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

		btnSalvarFase = new JButton(new AbstractAction("Salvar Fase") {

			@Override
			public void actionPerformed(ActionEvent e) {


			}
		});
		btnSalvarFase.setBounds(220, 40, 200, 25);
		getContentPane().add(btnSalvarFase);


	}

	private void cleanPaint() {

		paint(getGraphics());		

	}

	private void createSquare(TimeModel time, String pontos, int x, int y) {

		Graphics g = getGraphics();
		g.setColor(Color.GREEN);
		g.fillRect(x, y, 200, 30);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 200, 30);
		//TODO colocar logo
		printText((time.getNome() + "               ").substring(0, 15), x + 40, y + 18);		
		printText(pontos, x + 180, y + 18);		

	}

	private void printText(String text, int x, int y) {
		if(getGraphics() instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D)getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);	        
			g2.drawString(text, x, y);; 
		}		
	}




	@SuppressWarnings("unchecked")
	private void carregaFases(Integer idTorneio) {

		ArrayList<TorneioPartidaModel> partidas = null;
		
		cleanPaint();


		int j = 120;
		int x = 0;
		boolean fim = false;

		for(int i = 1; i < 6; i++) {

			try {
				partidas = torneioPartidaDAO.getAllTorneioPartidas(idTorneio, i);				
				if(partidas != null) {
					
					if(partidas.size()>0) {
						ultimaFasePartidas = (ArrayList<TorneioPartidaModel>)partidas.clone();
						if(partidas.size() == 1) {
							fim = true;
						}
					}
					
					ultimaFase = i;
					x = ((ultimaFase - 1) * 300) + 20;
					j = 120;

					
					for(TorneioPartidaModel partida : partidas) {

						createSquare(timeDAO.getOneTime(partida.getIdTime1()), partida.getPontos1().toString(), x, j);
						createSquare(timeDAO.getOneTime(partida.getIdTime2()), partida.getPontos2().toString(), x, j + 30);

						j = j + 86;					

					}
					
					if(fim) {
												
					}
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

			carregaFases(idTorneio);

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
				}else {
					
				}

			}


		} catch (SQLException e) {
			e.printStackTrace();
		} 		

	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}

}
