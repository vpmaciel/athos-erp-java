package erp.banco;

import java.util.Collection;

public final class BancoDaoFacade {

	private final BancoDao bancoDao = new BancoDaoImp();

	public void deletarRegistro(Banco banco) {
		bancoDao.deletarRegistro(banco);
	}

	public Banco getRegistro(Banco banco) {
		return bancoDao.getRegistro(banco);
	}

	public Collection<Banco> getRegistro() {
		return bancoDao.getRegistro();
	}

	public Collection<Banco> pesquisarRegistro(Banco banco) {
		return bancoDao.pesquisarRegistro(banco);
	}

	public void salvarBanco(Banco banco) {
		bancoDao.salvarRegistro(banco);
	}
}
