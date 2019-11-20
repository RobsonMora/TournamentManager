package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import dao.TimeDAO;
import dao.TorneioPartidaDAO;
import model.TimeModel;
import model.TorneioPartidaModel;

@SuppressWarnings("serial")
public class ControlePartidas extends JInternalFrame {

	private JLabel LblTorneio, LblPartida, LblVS, lblTime1, lblTime2;
	private JTextField txtFTorneio, txtFtime1, txtFtime2;
	private JButton btnCarregar, btnAnterior, btnProximo, btnSalvar;
	private JPanel panel1, panel2;

	private ArrayList<TorneioPartidaModel> partidas;
	private TorneioPartidaModel partidaAtual;
	private TorneioPartidaDAO torneioPartidaDAO;
	private TimeDAO timeDAO;

	private int posicao = 0;

	public ControlePartidas(Connection conn) {

		setSize(535, 535);
		setTitle("Controle de Partidas");
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
		timeDAO = new TimeDAO(conn);

		Components();
		setVisible(true);
		clean();
	}

	public void Components() {

		LblTorneio = new JLabel("Torneio");
		LblTorneio.setBounds(12, 0, 200, 100);
		getContentPane().add(LblTorneio);

		LblPartida = new JLabel();
		LblPartida.setBounds(10, 100, 500, 330);
		LblPartida.setBorder(BorderFactory.createTitledBorder("Partida"));
		getContentPane().add(LblPartida);

		LblVS = new JLabel("VS");
		LblVS.setBounds(250, 170, 200, 100);
		getContentPane().add(LblVS);

		txtFTorneio = new JTextField();
		txtFTorneio.setBounds(70, 40, 187, 26);
		txtFTorneio.addFocusListener(new FocusAdapter() {
			public void focusGained(final FocusEvent pE) {

				txtFTorneio.selectAll();
			}
		});

		txtFTorneio.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		getContentPane().add(txtFTorneio);

		lblTime1 = new JLabel("-");
		lblTime1.setBounds(32, 310, 195, 26);
		lblTime1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime1.setHorizontalTextPosition(SwingConstants.CENTER);
		getContentPane().add(lblTime1);

		txtFtime1 = new JTextField();
		txtFtime1.setBounds(32, 332, 195, 26);
		txtFtime1.addFocusListener(new FocusAdapter() {
			public void focusGained(final FocusEvent pE) {

				txtFtime1.selectAll();
			}
		});
		txtFtime1.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				if(txtFtime1.getText().trim().isEmpty()) {
					txtFtime1.setText("0");
				}
				if (partidaAtual != null) {
					partidaAtual.setPontos1(Integer.parseInt(txtFtime1.getText()));
				}
			}

		});
		txtFtime1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtFtime1.setEnabled(false);
		getContentPane().add(txtFtime1);

		lblTime2 = new JLabel("-");
		lblTime2.setBounds(292, 310, 195, 26);
		lblTime2.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime2.setHorizontalTextPosition(SwingConstants.CENTER);
		getContentPane().add(lblTime2);

		txtFtime2 = new JTextField();
		txtFtime2.setBounds(292, 332, 195, 26);
		txtFtime2.addFocusListener(new FocusAdapter() {
			public void focusGained(final FocusEvent pE) {

				txtFtime2.selectAll();
			}
		});
		txtFtime2.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent e) {
				if(txtFtime2.getText().trim().isEmpty()) {
					txtFtime2.setText("0");
				}
				if (partidaAtual != null) {
					partidaAtual.setPontos2(Integer.parseInt(txtFtime2.getText()));
				}
			}

		});
		txtFtime2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtFtime2.setEnabled(false);
		getContentPane().add(txtFtime2);

		btnCarregar = new JButton(new AbstractAction("Carregar") {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					
					if(txtFTorneio.getText().trim().isEmpty()){
						JOptionPane.showMessageDialog(null, "Campo vazio!");
						return;
					}
					
					ResultSet result = torneioPartidaDAO.freeSqlQuery(
							" select MAX(fase) from torneio_partidas where id_torneio = " + txtFTorneio.getText());

					if (result.next()) {

						Integer fase = result.getInt(1);
						if (fase > 0) {
							partidas = torneioPartidaDAO.getAllTorneioPartidas(Integer.parseInt(txtFTorneio.getText()),
									fase);

							if (partidas != null) {

								carregaPosicao(0);

							}
						}
						
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnCarregar.setBounds(273, 40, 107, 26);
		getContentPane().add(btnCarregar);

		btnAnterior = new JButton(new AbstractAction("Anterior") {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (partidas == null) {
					return;
				}

				if (posicao > 0) {
					carregaPosicao(posicao - 1);
				}

			}
		});
		btnAnterior.setBounds(75, 375, 107, 26);
		getContentPane().add(btnAnterior);

		btnProximo = new JButton(new AbstractAction("Próximo") {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (partidas == null) {
					return;
				}

				if (posicao < (partidas.size() - 1)) {
					carregaPosicao(posicao + 1);
				}

			}
		});
		btnProximo.setBounds(335, 375, 107, 26);
		getContentPane().add(btnProximo);

		btnSalvar = new JButton(new AbstractAction("Salvar") {

			@Override
			public void actionPerformed(ActionEvent e) {

				
				if (partidas == null) {
					
						JOptionPane.showMessageDialog(null, "Informe um torneio com partidas válidas!");
						return;
				}
				
				for (TorneioPartidaModel partida : partidas) {

					if(partida.getPontos1() == partida.getPontos2()) {
						JOptionPane.showMessageDialog(null, "Não pode haver empates!");
						return;
					}

				}

				try {
					for (TorneioPartidaModel partida : partidas) {

						torneioPartidaDAO.updateTorneioPartida(partida, timeDAO);

					}
					clean();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnSalvar.setBounds(205, 450, 107, 26);
		getContentPane().add(btnSalvar);

		panel1 = new JPanel();
		panel1.setBounds(32, 130, 195, 180);
		panel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		getContentPane().add(panel1);

		panel2 = new JPanel();
		panel2.setBounds(292, 130, 195, 180);
		panel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		getContentPane().add(panel2);

	}

	private void carregaPosicao(int posicao) {
		this.posicao = posicao;

		carregaPartida();

	}
	
	private void clean() {
		
		txtFtime1.setText("");
		txtFtime2.setText("");
		txtFTorneio.setText("");
		lblTime1.setText("-");
		lblTime2.setText("-");
		if(getGraphics()!=null) {
			paint(getGraphics());
		}
		partidas = null;
		partidaAtual = null;
		txtFTorneio.requestFocus();
		
	}

	private void carregaPartida() {
		partidaAtual = partidas.get(posicao);

		TimeModel time = null;

		txtFtime1.setEnabled(partidaAtual != null);
		txtFtime2.setEnabled(partidaAtual != null);

		try {
			time = timeDAO.getOneTime(partidaAtual.getIdTime1());
			paint(getGraphics());
			if (time != null) {

				panel1.getGraphics().drawImage(ImageIO.read((time.getLogo()==null)? new File(System.getProperty("user.dir") + "\\images\\Logos\\Logo_gradient.png"):time.getLogo()), 0, 0, panel1.getWidth(), panel1.getHeight(), null);
				lblTime1.setText(time.getNome());
				txtFtime1.setText(partidaAtual.getPontos1().toString());

			}

			time = timeDAO.getOneTime(partidaAtual.getIdTime2());

			if (time != null) {

				panel2.getGraphics().drawImage(ImageIO.read((time.getLogo()==null)? new File(System.getProperty("user.dir") + "\\images\\Logos\\Logo_gradient.png"):time.getLogo()), 0, 0, panel2.getWidth(), panel2.getHeight(), null);
				lblTime2.setText(time.getNome());
				txtFtime2.setText(partidaAtual.getPontos2().toString());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
