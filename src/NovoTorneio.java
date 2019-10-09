
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class NovoTorneio extends MasterDialogCad {

	private JLabel LblJogo, LblCodTorneio, LblNomeTorneio, LblQtdTimes, LblObs;
	private JTextField txtFCodTorneio, txtFNomeTorneio, txtFQtdTimes, txtFObs;
	private JTextArea txtAObs;
	private JComboBox<String> ComboJogo;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnAdd;

	private void create() {

		setSize(550,763);
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

		LblCodTorneio = new JLabel("Código do torneio:");
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
		ComboJogo.setBounds(150, 147, 377, 26);
		getContentPane().add(ComboJogo);

		LblObs = new JLabel("Observação:");
		LblObs.setBounds(59, 140, 200, 100);
		getContentPane().add(LblObs);

		txtFCodTorneio = new JTextField();
		txtFCodTorneio.setBounds(150, 57, 377, 26);
		getContentPane().add(txtFCodTorneio);

		txtFNomeTorneio = new JTextField();
		txtFNomeTorneio.setBounds(150, 87, 377, 26);
		getContentPane().add(txtFNomeTorneio);

		txtFQtdTimes = new JTextField();
		txtFQtdTimes.setBounds(150, 117, 377, 26);
		getContentPane().add(txtFQtdTimes);

		txtAObs = new JTextArea();
		getContentPane().add(txtAObs);
		txtAObs.setLineWrap(true);
		
		btnAdd = new JButton("Adicionar time");
		btnAdd.setBounds(10, 695, 107, 26);
		getContentPane().add(btnAdd);
		
		
		JScrollPane sp = new JScrollPane(txtAObs);
		sp.setBounds(9, 207, 519, 70);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(sp);
		
		String colunas[] = { "Código do Torneio", "Nome do time" };

		model = new DefaultTableModel(colunas, 50);

		table = new JTable(model);
		table.setBorder(BorderFactory.createLineBorder(Color.black));
		table.setEnabled(true);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(9, 287, 519, 400);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getContentPane().add(scrollPane);
		table.getTableHeader().setEnabled(false);
		
		childContainer = getContentPane();

	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}

}
