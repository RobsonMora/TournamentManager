
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class NovoTorneio extends MasterDialogCad {

	private JLabel LblJogo, LblCodTorneio, LblNomeTorneio, LblQtdTimes, LblObs;
	private JTextField txtFCodTorneio, txtFNomeTorneio, txtFQtdTimes, txtFObs;
	private JTextArea txtAObs;
	private JComboBox<String> ComboJogo;
	private JButton BtnCriar;

	private void create() {

		setSize(373, 359);
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

		LblCodTorneio = new JLabel("C�digo do torneio:");
		LblCodTorneio.setBounds(30, 20, 200, 100);
		getContentPane().add(LblCodTorneio);

		LblNomeTorneio = new JLabel("Nome do Torneio:");
		LblNomeTorneio.setBounds(32, 50, 200, 100);
		getContentPane().add(LblNomeTorneio);

		LblQtdTimes = new JLabel("Quantidade de Times:");
		LblQtdTimes.setBounds(10, 80, 200, 100);
		getContentPane().add(LblQtdTimes);

		LblJogo = new JLabel("Tipo de jogo:");
		LblJogo.setBounds(58, 110, 200, 100);
		getContentPane().add(LblJogo);

		ComboJogo = new JComboBox<String>();
		ComboJogo.addItem("--Selecione--");
		ComboJogo.setBounds(150, 147, 201, 26);
		getContentPane().add(ComboJogo);

		LblObs = new JLabel("Observa��o:");
		LblObs.setBounds(59, 140, 200, 100);
		getContentPane().add(LblObs);

		BtnCriar = new JButton("Criar Torneio");
		BtnCriar.setBounds(10, 283, 120, 36);
		getContentPane().add(BtnCriar);

		txtFCodTorneio = new JTextField();
		txtFCodTorneio.setBounds(150, 57, 201, 26);
		getContentPane().add(txtFCodTorneio);

		txtFNomeTorneio = new JTextField();
		txtFNomeTorneio.setBounds(150, 87, 201, 26);
		getContentPane().add(txtFNomeTorneio);

		txtFQtdTimes = new JTextField();
		txtFQtdTimes.setBounds(150, 117, 201, 26);
		getContentPane().add(txtFQtdTimes);

		txtAObs = new JTextArea();
		getContentPane().add(txtAObs);
		txtAObs.setLineWrap(true);

		JScrollPane sp = new JScrollPane(txtAObs);
		sp.setBounds(9, 207, 342, 70);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(sp);

		childContainer = getContentPane();

	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}

}
