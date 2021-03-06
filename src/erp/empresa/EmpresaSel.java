package erp.empresa;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import erp.main.MainControl;

final class EmpresaSel implements ListSelectionListener {

	JTable table;

	EmpresaSel(JTable table) {
		this.table = table;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			int[] selRows = table.getSelectedRows();
			TableModel tm = table.getModel();
			if (selRows.length > 0) {
				Empresa empresaPesquisaRegistro = new Empresa();
				empresaPesquisaRegistro.setId((Long) tm.getValueAt(selRows[0], EmpresaTm.ID));

				if (table.getSelectedRow() != -1) {
					Empresa empresa = EmpresaFac.getRegistro(empresaPesquisaRegistro);
					EmpresaTm empresaTm = (EmpresaTm) table.getModel();
					empresaTm.getEmpresa(table.getSelectedRow());
					MainControl.mostrarFrame(MainControl.getEmpresaFc());
					MainControl.getEmpresaFc().getEmpresaCont().setEmpresa(empresa);
					MainControl.getEmpresaFc().getEmpresaCont().atualizarGui();
					MainControl.getEmpresaFc().setFocusable(true);
					MainControl.getEmpresaFp().setVisible(false);
				}
			}
		}
	}
}
