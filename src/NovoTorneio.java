import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import dao.ConexaoDAO;

public class NovoTorneio extends MasterDialogCad  {
		
	private JLabel LblJogo, CodTorneio, NomeTorneio, QtdTimes, Obs;
	private JComboBox ComboJogo;
	private JButton BtnCriar;
	
	public NovoTorneio() {
		
		setSize(300, 400);
		setTitle("Novo Torneio");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);
		
	}
	
	
	protected void subComponents() {
		
		
		CodTorneio = new JLabel("Código do torneio:");		
		CodTorneio.setBounds(10, 20, 200, 100);
		getContentPane().add(CodTorneio);
		
		NomeTorneio = new JLabel("Nome do Torneio:");		
		NomeTorneio.setBounds(10, 50, 200, 100);
		getContentPane().add(NomeTorneio);

		QtdTimes = new JLabel("Quantidade de Times:");		
		QtdTimes.setBounds(10, 80, 200, 100);
		getContentPane().add(QtdTimes);
		
		LblJogo = new JLabel("Tipo de jogo:");		
		LblJogo.setBounds(10, 110, 200, 100);
		getContentPane().add(LblJogo);
		
		ComboJogo = new JComboBox<String>();
		ComboJogo.addItem("--Selecione--");
		ComboJogo.setBounds(8, 180, 130, 26);
		getContentPane().add(ComboJogo);
		
		Obs = new JLabel("Observação");
		Obs.setBounds(10, 175, 200, 100);
		getContentPane().add(Obs);
				
		BtnCriar = new JButton("Criar Torneio");
		BtnCriar.setBounds(10, 320, 150, 26);
		getContentPane().add(BtnCriar);
		
		childContainer = getContentPane();
	
		
	}
		
	
	public void setPosicao() {
		
		Dimension d = this.getDesktopPane().getSize();
	    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
	
	}

}
