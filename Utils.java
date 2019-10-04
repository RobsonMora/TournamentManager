package view;

import java.awt.Component;
import java.awt.Container;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.print.attribute.standard.PDLOverrideSupported;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.*;
import com.lowagie.text.*;

public class Utils {

	private BufferedWriter bw = null;
	private File textFile = null;
	private boolean readToSave = false;

	//Pega um container passado pela função getContentPane, traz todos componentes abaixo dele,
	//e desabilita os que estao contidos em alvo e não sa nulos
	public void setFieldsEnabled(Container contents, Boolean enabled) {

		for(Component field : contents.getComponents()) {		

			if (field!=null && isTarget(field.getClass().getName())) {	
				field.setEnabled(enabled);
			}

		}			

	}


	// Verifica se classe esta cadastrada como um alvo para ser desabilitada
	private Boolean isTarget(String componentClass) {

		// Insira as classes dos componentes para serem desabilitados
		String targets[] = {
				"javax.swing.JTextField",
				"javax.swing.JComboBox"							
		};

		for(String target : targets ) {
			if(target.equals(componentClass)) {
				return true;
			}
		}

		return false;

	}

	public boolean createNewReport(String file, String title) {

		readToSave = false;
		
		try {

			File textFile = new File(file)

			if(bw != null || textFile != null) {
				bw = null;
				textFile = null;
			}
			bw = new BufferedWriter(new FileWriter(textFile));	

			bw.write(title);
			bw.close();

			return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;		
		}

	}

	public boolean fillReport(File textFile; String newRecord) {

		try {			
			if(bw = null || textFile = null) {
				return false;			
			} 

			bw.write(newRecord);
			
			return readToSave = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return readToSave = false;
		}		

	}
	
	public Document getFinalReport() {
		/*
		try {
			if(readToSave) {
				
				P
				
				Document pdf = new Document(PageSize.LETTER, 40,40,40,40) 
				
				PdfWriter.ge
				
			}else {
				System.out.println("O arquivo não esta pronto!");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		*/
	}
	
}
