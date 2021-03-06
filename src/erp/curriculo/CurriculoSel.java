package erp.curriculo;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import erp.funcionario.Funcionario;
import erp.main.MainControl;

final class CurriculoSel implements ListSelectionListener {

	JTable table;

	CurriculoSel(JTable table) {
		this.table = table;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			int[] selRows = table.getSelectedRows();
			TableModel tm = table.getModel();
			if (selRows.length > 0) {
				Funcionario funcionarioPesquisaRegistro = new Funcionario();
				funcionarioPesquisaRegistro.setId((Long) tm.getValueAt(selRows[0], CurriculoTm.ID));

				if (table.getSelectedRow() != -1) {
					Funcionario funcionario = CurriculoFac.getRegistro(funcionarioPesquisaRegistro);
					CurriculoTm CurriculoTm = (CurriculoTm) table.getModel();
					CurriculoTm.getFuncionario(table.getSelectedRow());
					MainControl.mostrarFrame(MainControl.getCurriculoFc());
					MainControl.getCurriculoFc().getCurriculoCont().setFuncionario(funcionario);
					MainControl.getCurriculoFc().getCurriculoCont().atualizarGui();
					MainControl.getCurriculoFc().setFocusable(true);
					MainControl.getCurriculoFp().setVisible(false);
				}
			}
		}
	}
}
