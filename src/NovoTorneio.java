
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class NovoTorneio extends MasterDialogCad {

	private JLabel LblJogo, CodTorneio, NomeTorneio, QtdTimes, Obs;
	private JTextField txtCodTorneio, txtNomeTorneio, txtQtdTimes, txtObs;
	private JComboBox<String> ComboJogo;
	private JButton BtnCriar;


	private void create() {

		setSize(375, 285);
		setTitle("Novo Torneio");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);		
		
	}

	public NovoTorneio() {

		create();

	}


	@Override
	protected void subComponents() {


		CodTorneio = new JLabel("Código do torneio:");		
		CodTorneio.setBounds(30, 20, 200, 100);
		getContentPane().add(CodTorneio);

		NomeTorneio = new JLabel("Nome do Torneio:");		
		NomeTorneio.setBounds(32, 50, 200, 100);
		getContentPane().add(NomeTorneio);

		QtdTimes = new JLabel("Quantidade de Times:");		
		QtdTimes.setBounds(10, 80, 200, 100);
		getContentPane().add(QtdTimes);

		LblJogo = new JLabel("Tipo de jogo:");		
		LblJogo.setBounds(58, 110, 200, 100);
		getContentPane().add(LblJogo);

		ComboJogo = new JComboBox<String>();
		ComboJogo.addItem("--Selecione--");
		ComboJogo.setBounds(150, 147, 150, 26);
		getContentPane().add(ComboJogo);

		Obs = new JLabel("Observação:");
		Obs.setBounds(59, 140, 200, 100);
		getContentPane().add(Obs);

		BtnCriar = new JButton("Criar Torneio");
		BtnCriar.setBounds(150, 210, 120, 36);
		getContentPane().add(BtnCriar);
		
		txtCodTorneio = new JTextField();
		txtCodTorneio.setBounds(150, 57, 150, 26);
		getContentPane().add(txtCodTorneio);
		
		txtNomeTorneio = new JTextField();
		txtNomeTorneio.setBounds(150, 87, 150, 26);
		getContentPane().add(txtNomeTorneio);
		
		txtQtdTimes = new JTextField();
		txtQtdTimes.setBounds(150, 117, 150, 26);
		getContentPane().add(txtQtdTimes);
		
		txtObs = new JTextField();
		txtObs.setBounds(150, 177, 150, 26);
		getContentPane().add(txtObs);
		
		childContainer = getContentPane();


	}


	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}

}
