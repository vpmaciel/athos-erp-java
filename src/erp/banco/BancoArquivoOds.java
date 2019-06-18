package erp.banco;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import arquitetura.Sis;
import arquitetura.data.Data;

public class BancoArquivoOds {

	Object[][] dados;
	String[] colunas = new String[] { "BANCO", "CÓDIGO" };
	TableModel tableModel;
	File file;
	private final String arquivo = Sis.getCaminhoDiretorioPlanilhas() + "[banco]-" + Data.getDataHoraArquivo() + ".ods";

	public BancoArquivoOds(List<Banco> listBanco) {

		try {
			dados = new Object[listBanco.size()][2];

			int linha = 0;

			for (Banco banco : listBanco) {
				dados[linha][0] = banco.getNome();
				dados[linha][1] = banco.getCodigo();
				linha++;
			}

			tableModel = new DefaultTableModel(dados, colunas);
			file = new File(arquivo);
			SpreadSheet.createEmpty(tableModel).saveAs(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File retornarArquivo(boolean abrirArquivo) {
		if (abrirArquivo) {
			try {
				Sis.abrirDiretorio(Sis.getCaminhoDiretorioPlanilhas());
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}