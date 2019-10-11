package view;
import java.awt.Dimension;
import java.sql.Connection;

import dao.JogoDAO;
import dao.TimeDAO;
import dao.TorneioDAO;
import dao.TorneioTimeDAO;

@SuppressWarnings("serial")
public class Times extends MasterDialogCad{
	
	private TimeDAO timeDao;
		
	private void create() {

		setSize(550,684);
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
	protected void subComponents() {
	
		
		
	
	}

	public void setPosicao() {

		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);

	}
}
