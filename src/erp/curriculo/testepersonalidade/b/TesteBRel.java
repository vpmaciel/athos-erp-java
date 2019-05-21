package erp.curriculo.testepersonalidade.b;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import arquitetura.relatorio.Relatorio;

public class TesteBRel {

	private String arquivo = "testeB.pdf";
	private Document document = new Document();
	private Relatorio relatorio = new Relatorio();
	private String titulo = "CARACTERÍSTICAS";
	private PdfWriter writer = null;

	public TesteBRel(List<TesteB> testeBs) {

		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
			relatorio.criarRelatorio(writer, document, titulo);

			for (TesteB testeB : testeBs) {
				document.newPage();
				document.add(new Paragraph("FUNCIONÁRIO: " + testeB.getFuncionario()));
				document.add(new Paragraph("ADEQUADO: " + testeB.getAdequado()));
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