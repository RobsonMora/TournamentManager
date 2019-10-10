package view;

import java.awt.Component;
import java.awt.Container;
import java.text.Collator;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public class Utils {

	// Insira as classes dos componentes para serem desabilitados
	// Note que para Classes como a JComboBox, deve ser implementado uma forma
	// especifica para limpar o componente na função cleanFields(), ja os componentes
	// que extendem JTextComponent como JtextArea e JTextField não é necessario implementar.
	private Class<?> targetClasses[] = {
			JTextField.class,
			JFormattedTextField.class,
			JComboBox.class,
			JTextArea.class,
			JScrollPane.class,
			JTable.class,
			JButton.class
	};


	public void cleanSubComponents(Container container) {
		cleanComponents(container.getComponents());
	}

	public void cleanComponents(Component[] components) {
		for(Component component : components) {		

			if (component!=null) {
				if (isInstanceOfSomeTarget(component)) {
					if (JComboBox.class.isInstance(component)) {
						((JComboBox<?>) component).setSelectedIndex(0);;	
					}else if (JScrollPane.class.isInstance(component)) {
						cleanComponents(getSubComponents(component));
					}else if (JTable.class.isInstance(component)){
						((DefaultTableModel) ((JTable) component).getModel()).setRowCount(0);
					} else if (JButton.class.isInstance(component)) {
						//Ignore;
					} else {
						((JTextComponent) component).setText("");
					}
				}
			}

		}
	}

	//Pega um container passado pela função getContentPane, traz todos componentes abaixo dele,
	//e desabilita os que estao contidos em alvo e não sa nulos
	public void setSubComponentsEnabled(Container container, Boolean enabled) {

		setComponentsEnabled(container.getComponents(), enabled);			

	}

	public void setComponentsEnabled(Component[] components, Boolean enabled) {
		for(Component component : components) {		

			if (component!=null && isInstanceOfSomeTarget(component)/*Target(field.getClass().getName())*/) {	
				if(JScrollPane.class.isInstance(component)) {
					//Tratamento especifico para SrollPane, é passado todos componentes dentro do Scroll de forma recursiva
					setComponentsEnabled( getSubComponents(component), enabled);			
				}else {
					if(component.getName()==null) {
						component.setEnabled(enabled);
					}
				}				
			}

		}
	}

	private Component[] getSubComponents(Component component) {
		return ((JViewport) ((JScrollPane) component).getComponent(0)).getComponents();
	}


	// Descontinuado - Use isInstanceOfSomeTarget(Component component) 
	// Verifica se classe esta cadastrada como um alvo para ser desabilitada
	/*private Boolean isTarget(String componentClass) {

		for(String target : targets ) {
			if(target.equals(componentClass)) {
				return true;
			}
		}

		return false;

	}*/


	private boolean isInstanceOfSomeTarget(Component component) {

		for(Class<?> target : targetClasses) {
			if(target.isInstance(component)) {
				return true;								
			}
		}
		return false;
	}

	public boolean containsIgnoreCase(String str, String searchStr)     {
		if(str == null || searchStr == null) return false;

		final int length = searchStr.length();
		if (length == 0)
			return true;

		if (length > str.length())
			return false;

		for (int i = str.length() - length; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, length))
				return true;
		}
		return false;
	}

	public boolean compareStrings(String strA, String StrB) {

		Collator coll = Collator.getInstance (new Locale ("pt", "BR"));
		coll.setStrength(Collator.PRIMARY);

		return coll.compare(strA, StrB) == 0;

	}

	public boolean tableContains(JTable table, int column, String str) {
		DefaultTableModel model = ((DefaultTableModel) table.getModel());

		for(int i = 0; i < model.getRowCount(); i++) {

			if (model.getValueAt(i, column).toString().equals(str)) {
				return true;
			}

		}

		return false;		
	}



}
