package erp.cliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import arquitetura.data.Data;
import arquitetura.relatorio.Relatorio;

public class ClienteRel {

	private String arquivo = Data.getHora() + "-cliente.pdf";
	private Document document = new Document();
	private Relatorio relatorio = new Relatorio();
	private String titulo = "CLIENTES";
	private PdfWriter writer = null;

	public ClienteRel(List<Cliente> clientes) {

		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
			relatorio.criarRelatorio(writer, document, titulo);

			for (Cliente cliente : clientes) {
				document.newPage();
				document.add(new Paragraph("NOME: " + cliente.getNome()));
				document.add(new Paragraph("SEXO: " + cliente.getSexo()));
				document.add(new Paragraph("RG NÚMERO: " + cliente.getRgNumero()));
				document.add(new Paragraph("RG ÓRGÃO EMISSSOR: " + cliente.getRgOrgaoEmissor()));
				document.add(new Paragraph("CNPJ: " + cliente.getCnpj()));
				document.add(new Paragraph("CPF: " + cliente.getCpf()));
				document.add(new Paragraph("DATA CADASTRO: " + cliente.getDataCadastro()));
				document.add(new Paragraph("CARGO: " + cliente.getCargo()));
				document.add(new Paragraph("SALÁRIO: " + cliente.getSalario()));
				document.add(new Paragraph("CATEGORIA: " + cliente.getClasseEconomica()));
				document.add(new Paragraph("EMPRESA: " + cliente.getEmpresa()));
				document.add(new Paragraph("ESTADO CIVIL: " + cliente.getEnderecoEstadoCivil()));
				document.add(new Paragraph("FAX: " + cliente.getFax()));
				document.add(new Paragraph("TELEFONE: " + cliente.getFone1()));
				document.add(new Paragraph("TELEFONE: " + cliente.getFone2()));
				document.add(new Paragraph("E-MAIL: " + cliente.getEmail()));
				document.add(new Paragraph("PAÍS: " + cliente.getEnderecoPais()));
				document.add(new Paragraph("ESTADO: " + cliente.getEnderecoEstado()));
				document.add(new Paragraph("CIDADE: " + cliente.getEnderecoCidade()));
				document.add(new Paragraph("BAIRRO: " + cliente.getEnderecoBairro()));
				document.add(new Paragraph("LOGRADOURO: " + cliente.getEnderecoLogradouro()));
				document.add(new Paragraph("COMPLEMENTO: " + cliente.getEnderecoComplemento()));
				document.add(new Paragraph("CEP: " + cliente.getEnderecoCep()));
			}
		} catch (DocumentException | FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
		relatorio.getRodape(writer, document);
		document.close();
		relatorio.retornarRelatorio(arquivo, false);
	}

	public File retornarRelatorio(boolean abrirArquivo) {
		return relatorio.retornarRelatorio(arquivo, abrirArquivo);
	}
}
