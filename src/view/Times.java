package view;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.JogoDAO;
import dao.TimeDAO;
import dao.TorneioDAO;
import dao.TorneioTimeDAO;
import model.JogoModel;
import model.TimeModel;

@SuppressWarnings("serial")
public class Times extends MasterDialogCad{
	
	private JLabel LblCodigoID, LblTime;
	private JTextField txtFCodigoID, txtFTime;
	private TimeDAO timeDao;
	private BuscarTime busca;
	private TimeModel time, timeChange;
			
	private void create() {

		setSize(550,154);
		setTitle("Times");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setVisible(true);

	}
	
	public Times(Connection conn) {
		
		super(conn);
		timeDao = new TimeDAO(conn);
		create();
	}
	
	protected void actionSearch() {
		busca = new BuscarTime(conn);
		try {
			busca.addWindowListener(eventWindowSearchClosed);
		} catch (Exception e2) {
			busca = null;
		}
	}

	@Override
	protected boolean afterSearch() {
		if (busca.timeReturn != null) {
			time = busca.timeReturn;
			return true;
		}
		return false;
	}

	@Override
	protected boolean actionDelete() {
		if ((time != null) && (!isInserting)) {
			try {
				timeDao.deleteTime(time.getId());
				return true;
			} catch (SQLException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	protected boolean actionSave() {
		try {
			getFields();
			if (isInserting) {
				timeDao.createTime(timeChange);

			} else {
				timeDao.updateTime(timeChange);
			}
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	protected boolean actionAdd() {
		if (!isInserting) {
			try {
				time = new TimeModel();
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	protected boolean actionCancel() {
		try {
			if (isInserting) {
				time = null;
			}
			timeChange = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	protected void fillFields() {

		txtFCodigoID.setText(time.getId().toString());
		txtFTime.setText(time.getNome());


		timeChange = new TimeModel(time);
	}
	
	private void getFields() {
		timeChange.setNome(txtFTime.getText());
	}
	
	protected void subComponents() {
		
		LblCodigoID = new JLabel("Código do torneio:");
		LblCodigoID.setBounds(11, 20, 200, 100);
		getContentPane().add(LblCodigoID);
		
		LblTime = new JLabel("Nome do time:");
		LblTime.setBounds(33, 50, 200, 100);
		getContentPane().add(LblTime);	
		
		txtFCodigoID = new JTextField();
		txtFCodigoID.setBounds(130, 57, 397, 26);
		txtFCodigoID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					txtFTime.requestFocus();
				}

			}
		});
		getContentPane().add(txtFCodigoID);

		txtFTime = new JTextField();
		txtFTime.setBounds(130, 87, 397, 26);
		txtFTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					btnSave.doClick();
				}

			}
		});
		getContentPane().add(txtFTime);
		childContainer = getContentPane();
	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
