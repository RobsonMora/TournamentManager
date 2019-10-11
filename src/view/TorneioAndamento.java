package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import dao.TorneioPartidaDAO;
import dao.TorneioTimeDAO;
import model.TimeModel;
import model.TorneioPartidaModel;
import model.TorneioTimeModel;

@SuppressWarnings("serial")
public class TorneioAndamento  extends JInternalFrame {

	private JButton btnGerarFase, btnSalvarFase;
	private JTextField txtTorneio;
	
	private ArrayList<TorneioPartidaModel> partidas;
	private ArrayList<TimeModel> times;
	private TorneioPartidaDAO torneioPartidaDAO;
	private TorneioTimeDAO torneioTimeDAO;
	
	
	public TorneioAndamento(Connection conn){

		setSize(1200, 800);
		setTitle("Torneios em Andamento");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		
		torneioPartidaDAO = new TorneioPartidaDAO(conn);
		torneioTimeDAO = new TorneioTimeDAO(conn);
		
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
				
				desenhar();
				
			}
		});
		btnSalvarFase.setBounds(280,10, 200, 25);
		getContentPane().add(btnSalvarFase);
		
		
	}
	
	private void createSquare(Image logo, int x, int y) {
		
		
		
	}
	
	private void desenhar() {
		getGraphics().drawLine(20, 30, 50, 30);
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
															);					
				}				
			}
			
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
