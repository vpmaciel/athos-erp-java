package erp.contador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import erp.aop.relatorio.Relatorio;

public class ContadorRelatorio {
	
	private PdfWriter writer = null;
	private Document document = new Document();
	private String arquivo = "contador.pdf";
	private String titulo = "contadores";
	private Relatorio relatorio = new Relatorio();
	
	public ContadorRelatorio(List<Contador> contadores) {
		
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
			relatorio.criarRelatorio(writer, document, titulo);

			for (Contador contador : contadores) {
				document.newPage();
				document.add(new Paragraph("NOME: " + contador.getNome()));
				document.add(new Paragraph("CNPJ: " + contador.getCnpj()));
				document.add(new Paragraph("CPF: " + contador.getCpf()));
				document.add(new Paragraph("CRC: " + contador.getCrc()));
				document.add(new Paragraph("TELEFONE: " + contador.getFone1()));
				document.add(new Paragraph("TELEFONE: " + contador.getFone2()));
				document.add(new Paragraph("FAX: " + contador.getFax()));
				document.add(new Paragraph("E-MAIL: " + contador.getEmail()));
				document.add(new Paragraph("SITE: " + contador.getSite()));
			}
		} catch (DocumentException | FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
		relatorio.getRodape(writer, document);
		document.close();
		relatorio.retornarRelatorio(arquivo, false);
	}	
	
	public File retornarRelatorio(boolean abrirRelatorio) {
		return relatorio.retornarRelatorio(arquivo, abrirRelatorio);
	}
}
