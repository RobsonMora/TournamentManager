
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class MasterDialogCad extends JInternalFrame {
	// Botoes
	protected JToolBar toolbar;
	protected JButton btnAdd;
	protected JButton btnSearch;	
	protected JButton btnDelete;
	protected JButton btnSave;	
	protected JButton btnCancel;
	protected Container childContainer;
	protected Utils utils;
	
	protected Connection conn;

	protected boolean isInserting;
	protected AbstractAction actDel = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));			
				if (actionDelete()) {
					setFieldsEnabled(false);
					clean();
				}
			}finally {
				setCursor(Cursor.getDefaultCursor());
			}
		}
	};

	protected AbstractAction actAdd = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(actionAdd()) {
				isInserting = true;
				clean();
				fillFields();
				setFieldsEnabled(true);				
			}
		}
	};

	protected AbstractAction actSearch = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			actionSearch();
		}
	};

	protected WindowAdapter eventWindowSearchClosed = new WindowAdapter() {

		@Override
		public void windowClosed(WindowEvent e) {

			if(afterSearch()) {
				clean();
				fillFields();
				isInserting = false;
				setFieldsEnabled(true);
			}

		}		

	};

	protected AbstractAction actSave = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				if(actionSave()) {
					if(isInserting) {
						btnCancel.doClick();
						clean();
					}
				}
			} finally {
				setCursor(Cursor.getDefaultCursor());
			}

		}
	};

	protected AbstractAction actCancel = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(actionCancel()) {
				if(!isInserting) {
					fillFields();
				}else {
					isInserting = false;
					setFieldsEnabled(false);
					clean();
				}

			}
		}
	};

	public MasterDialogCad(Connection conn) {

		this.conn = conn;
		
		utils = new Utils();
		Componnents();
		subComponents();
		setFieldsEnabled(false);
super
	}

	protected void setFieldsEnabled(boolean enabled) {
		utils.setSubComponentsEnabled(childContainer, enabled);
	}

	protected void subComponents() {

	}

	protected boolean actionDelete() {
		return false;
	}

	protected boolean actionAdd() {
		return true;
	}

	protected void actionSearch() {
	}

	protected boolean actionSave() {
		return false;
	}

	protected boolean actionCancel() {
		return false;
	}

	protected boolean afterSearch() {
		return false;		
	}

	protected void fillFields() {

	}

	protected void clean() {
		utils.cleanSubComponents(childContainer);
	}

	private void Componnents() {

		toolbar = new JToolBar();
		toolbar.setAlignmentX(LEFT_ALIGNMENT);
		toolbar.setAlignmentY(TOP_ALIGNMENT);

		//Botao Search
		btnSearch = new JButton("Buscar", new ImageIcon(System.getProperty("user.dir") + "\\images\\icons\\buscar.png"));
		btnSearch.addActionListener(actSearch);
		btnSearch.setHorizontalAlignment(SwingConstants.LEFT);
		btnSearch.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnSearch.setBounds(10, 10, 120, 35);
		//getContentPane().add(btnSearch);	


		// Botao Add
		btnAdd = new JButton("Adicionar", new ImageIcon(System.getProperty("user.dir") + "\\images\\icons\\adicionar.png"));
		btnAdd.addActionListener(actAdd);
		btnAdd.setHorizontalAlignment(SwingConstants.LEFT);
		btnAdd.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnAdd.setBounds(130, 10, 120, 35);
		//getContentPane().add(btnAdd);

		// Botao Delete
		btnDelete = new JButton("Remover", new ImageIcon(System.getProperty("user.dir") + "\\images\\icons\\remover.png"));
		btnDelete.addActionListener(actDel);
		btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
		btnDelete.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnDelete.setBounds(250, 10, 120, 35);
		//getContentPane().add(btnDelete);

		// Botao Save
		btnSave = new JButton("Salvar", new ImageIcon(System.getProperty("user.dir") + "\\images\\icons\\salvar.png"));
		btnSave.addActionListener(actSave);
		btnSave.setHorizontalAlignment(SwingConstants.LEFT);
		btnSave.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnSave.setBounds(370, 10, 120, 35);
		//getContentPane().add(btnSave);

		// Botao Cancel
		btnCancel = new JButton("Cancelar", new ImageIcon(System.getProperty("user.dir") + "\\images\\icons\\cancelar.png"));
		btnCancel.addActionListener(actCancel);		
		btnCancel.setHorizontalAlignment(SwingConstants.LEFT);
		btnCancel.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnCancel.setBounds(490, 10, 120, 35);
		//getContentPane().add(btnCancel);
		toolbar.add(btnSearch);
		toolbar.addSeparator();;
		toolbar.add(btnAdd);
		toolbar.addSeparator();
		toolbar.add(btnDelete);
		toolbar.addSeparator();
		toolbar.add(btnSave);
		toolbar.addSeparator();
		toolbar.add(btnCancel);

		toolbar.setBounds(10, 10, 515, 40);		
		getContentPane().add(toolbar);	


	}

}