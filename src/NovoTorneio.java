
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class NovoTorneio extends MasterDialogCad {

	private JLabel LblJogo, CodTorneio, NomeTorneio, QtdTimes, Obs;
	private JComboBox<String> ComboJogo;
	private JButton BtnCriar;


	private void create() {

		setSize(690, 538);
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
		CodTorneio.setBounds(135, 20, 200, 100);
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
