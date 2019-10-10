package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import dao.TorneioPartidaDAO;
import model.TorneioPartidaModel;

@SuppressWarnings("serial")
public class TorneioAndamento  extends JInternalFrame {

	private JButton btnGerarFase, btnSalvarFase;
	private JTextField txtTorneio;
	
	private ArrayList<TorneioPartidaModel> partidas;
	private TorneioPartidaDAO torneioPartidaDAO;
	
	public TorneioAndamento(Connection conn){

		setSize(1440, 900);
		setTitle("Torneios em Andamento");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		
		torneioPartidaDAO = new TorneioPartidaDAO(conn);
		
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
	
	private void desenhar() {
		getGraphics().drawLine(20, 30, 50, 30);
	}
	
	private void gerarFase(int idTorneio) {
	
		try {
			partidas = torneioPartidaDAO.getAllTorneioPartidas(idTorneio);
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
		
	}
	
	public void setPosicao() {
		
		Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
	
	}
	
}
