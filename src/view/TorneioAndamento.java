package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import dao.TimeDAO;
import dao.TorneioPartidaDAO;
import dao.TorneioTimeDAO;
import model.TimeModel;
import model.TorneioPartidaModel;

@SuppressWarnings("serial")
public class TorneioAndamento  extends JInternalFrame {

	private JButton btnGerarFase, btnSalvarFase;
	private JTextField txtTorneio;

	private ArrayList<TorneioPartidaModel> partidas;
	private ArrayList<TimeModel> times;
	private TorneioPartidaDAO torneioPartidaDAO;
	private TorneioTimeDAO torneioTimeDAO;
	private TimeDAO timeDAO;

	private Graphics painter;
	private Container chaveContainer;


	private int ultimaFase = 0;


	public TorneioAndamento(Connection conn){

		setSize(1200, 800);
		setTitle("Torneios em Andamento");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);

		torneioPartidaDAO = new TorneioPartidaDAO(conn);
		torneioTimeDAO = new TorneioTimeDAO(conn);
		timeDAO = new TimeDAO(conn);
		
			
		chaveContainer = new Container();
		getContentPane().add(chaveContainer);

		componentes();
		setVisible(true);		

	}

	private void componentes() {

		btnGerarFase = new JButton(new AbstractAction("Gera Prox. Fase") {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(!txtTorneio.getText().isEmpty()) {					
					gerarFase(Integer.parseInt(txtTorneio.getText()));					
				}

			}
		});
		btnGerarFase.setBounds(10,10, 200, 25);
		getContentPane().add(btnGerarFase);

		txtTorneio = new JTextField();
		txtTorneio.setBounds(220, 10, 50, 25);
		getContentPane().add(txtTorneio);

		btnSalvarFase = new JButton(new AbstractAction("Salvar Fase") {

			@Override
			public void actionPerformed(ActionEvent e) {

				getGraphics().clearRect(10, 70, 1100, 800);

			}
		});
		btnSalvarFase.setBounds(280,10, 200, 25);
		getContentPane().add(btnSalvarFase);


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
	
	


	private void carregaFases(Integer idTorneio) {

		partidas = null;


		int j = 80;
		int x = 0;

		for(int i = 1; i < 6; i++) {

			try {
				partidas = torneioPartidaDAO.getAllTorneioPartidas(idTorneio, i);
				if(partidas != null) {
					ultimaFase = i;
					x = ((ultimaFase - 1) * 300) + 100;
					j = 80;

					for(TorneioPartidaModel partida : partidas) {

						createSquare(timeDAO.getOneTime(partida.getIdTime1()), partida.getPontos1().toString(), x, j);
						createSquare(timeDAO.getOneTime(partida.getIdTime2()), partida.getPontos2().toString(), x, j + 30);

						j = j + 86;					

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
				partidas = new ArrayList<TorneioPartidaModel>();
				for(int i = 0; i < times.size(); i = i + 2) {
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

	private void gerarFase(Integer idTorneio) {

		try {
			ResultSet result = torneioPartidaDAO.freeSqlQuery(" select (Max(coalesce(fase,0)) + 1) as fase from torneio_partidas where id_torneio = "+idTorneio.toString());
			if(result.next()) {
				if(result.getInt(1) == 0) {
					iniciaPartidas(idTorneio);			
				}else {
					carregaFases(idTorneio);
				}
			}else {
				result = torneioPartidaDAO.freeSqlQuery(" select ");
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
