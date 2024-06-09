package app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Report {
	
	private InputStream jrxmlFileInput;
	private Map<String, Object> parameters;
	private JasperDesign jasperDesign;
	private JasperReport jasperReport;
	private static JasperPrint jasperPrint;
	
	public Report() {
		this.jrxmlFileInput = null;
		this.parameters = new HashMap<String, Object>();
		this.jasperDesign = null;
		this.jasperReport = null;
		Report.jasperPrint = null;
	}
	
	public InputStream getJrxmlFileInput() {
		return jrxmlFileInput;
	}

	public void setJrxmlFileInput(InputStream fileInput) {
		this.jrxmlFileInput = fileInput;
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
	public JasperDesign getJasperDesign() {
		return jasperDesign;
	}

	public void setJasperDesign(JasperDesign jasperDesign) {
		this.jasperDesign = jasperDesign;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		Report.jasperPrint = jasperPrint;
	}

	public JasperReport getJasperReport() {
		return jasperReport;
	}

	public void setJasperReport(JasperReport jasperReport) {
		this.jasperReport = jasperReport;
	}
	
	public void addParam(String nameParam, Object object) {
        parameters.put(nameParam, object);
	}
	
	public void readFileReport(String pathFileJrxml) throws FileNotFoundException {
		jrxmlFileInput = new FileInputStream(new File(pathFileJrxml));
	}
	
	public void generateReport() throws JRException {
		jasperDesign = JRXmlLoader.load(jrxmlFileInput);
		jasperReport = JasperCompileManager.compileReport(jasperDesign);
		jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
	}
	
	public void showReport() {
		JasperViewer.viewReport(jasperPrint, false);
	}
	
	static public void saveReportToPdf(String pathDestiny) throws JRException, FileNotFoundException {
        OutputStream outputStream = null;
		outputStream = new FileOutputStream(new File(pathDestiny));
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}
	
	public void clear() {
		this.parameters = null;
		this.jasperDesign = null;
		this.jasperReport = null;
		Report.jasperPrint = null;
	}
}