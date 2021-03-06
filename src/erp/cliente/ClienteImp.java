package erp.cliente;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import arquitetura.Jpa;
import arquitetura.validacao.Mascara;

final class ClienteImp implements ClienteDao {

	@Override
	public void deletarRegistro(Cliente cliente) {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		try {
			entityManager = Jpa.getEntityManagerFactory().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.remove(entityManager.find(Cliente.class, cliente.getId()));
			entityTransaction.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
			entityTransaction.rollback();
			throw exception;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Cliente> getRegistro() {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		List<Cliente> clienteList = null;

		try {
			entityManager = Jpa.getEntityManagerFactory().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createQuery("select T from Cliente T order by T.funcionario", Cliente.class);
			clienteList = query.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return clienteList;
	}

	@Override
	public Cliente getRegistro(Cliente cliente) {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		try {
			entityManager = Jpa.getEntityManagerFactory().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			cliente = entityManager.find(Cliente.class, cliente.getId());
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return cliente;
	}

	@Override
	public Collection<Cliente> pesquisarRegistro(Cliente cliente) {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		List<Cliente> clienteList = null;
		try {
			entityManager = Jpa.getEntityManagerFactory().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
			Root<Cliente> rootCliente = criteriaQuery.from(Cliente.class);
			List<Predicate> predicateList = new ArrayList<Predicate>();

			if (cliente.getId() != null) {
				predicateList.add(criteriaBuilder.equal(rootCliente.get("id"), cliente.getId()));
			}
			if (cliente.getEnderecoBairro() != null && cliente.getEnderecoBairro().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("enderecoBairro"), "%" + cliente.getEnderecoBairro() + "%"));
			}
			if (cliente.getCargo() != null && cliente.getCargo().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("cargo"), "%" + cliente.getCargo() + "%"));
			}
			if (cliente.getClasseEconomica() != null && cliente.getClasseEconomica().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("classeEconomica"),
						"%" + cliente.getClasseEconomica() + "%"));
			}
			if (cliente.getDataCadastro() != null
					&& !cliente.getDataCadastro().equals(Mascara.getData().getPlaceholder())
					&& !cliente.getDataCadastro().equals(Mascara.getDataVazio() != null)) {
				predicateList.add(
						criteriaBuilder.like(rootCliente.get("dataCadastro"), "%" + cliente.getDataCadastro() + "%"));
			}
			if (cliente.getEnderecoCep() != null && !cliente.getEnderecoCep().equals(Mascara.getEnderecoCep().getPlaceholder())
					&& !cliente.getEnderecoCep().equals(Mascara.getEnderecoCepVazio())) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("enderecoCep"), "%" + cliente.getEnderecoCep() + "%"));
			}
			if (cliente.getEnderecoCidade() != null && cliente.getEnderecoCidade().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("enderecoCidade"), "%" + cliente.getEnderecoCidade() + "%"));
			}
			if (cliente.getEnderecoComplemento() != null && cliente.getEnderecoComplemento().length() > 0) {
				predicateList.add(
						criteriaBuilder.like(rootCliente.get("enderecoComplemento"), "%" + cliente.getEnderecoComplemento() + "%"));
			}
			if (cliente.getCpf() != null && !cliente.getCpf().equals(Mascara.getCpf().getPlaceholder())
					&& !cliente.getCpf().equals(Mascara.getCpfVazio())) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("cpf"), "%" + cliente.getCpf() + "%"));
			}
			if (cliente.getEmail() != null && cliente.getEmail().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("email"), "%" + cliente.getEmail() + "%"));
			}
			if (cliente.getEnderecoEstado() != null && cliente.getEnderecoEstado().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("enderecoEstado"), "%" + cliente.getEnderecoEstado() + "%"));
			}
			if (cliente.getEnderecoEstadoCivil() != null && cliente.getEnderecoEstadoCivil().length() > 0) {
				predicateList.add(
						criteriaBuilder.like(rootCliente.get("enderecoEstadoCivil"), "%" + cliente.getEnderecoEstadoCivil() + "%"));
			}
			if (cliente.getFax() != null && !cliente.getFax().equals(Mascara.getFax().getPlaceholder())
					&& !cliente.getFax().equals(Mascara.getFaxVazio())) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("fax"), "%" + cliente.getFax() + "%"));
			}
			if (cliente.getFone1() != null && !cliente.getFone1().equals(Mascara.getFone().getPlaceholder())
					&& !cliente.getFone1().equals(Mascara.getFoneVazio())) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("fone1"), "%" + cliente.getFone1() + "%"));
			}
			if (cliente.getFone2() != null && !cliente.getFone2().equals(Mascara.getFone().getPlaceholder())
					&& !cliente.getFone2().equals(Mascara.getFoneVazio())) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("fone2"), "%" + cliente.getFone2() + "%"));
			}
			if (cliente.getEnderecoLogradouro() != null && cliente.getEnderecoLogradouro().length() > 0) {
				predicateList
						.add(criteriaBuilder.like(rootCliente.get("enderecoLogradouro"), "%" + cliente.getEnderecoLogradouro() + "%"));
			}
			if (cliente.getNome() != null && cliente.getNome().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("nome"), "%" + cliente.getNome() + "%"));
			}
			if (cliente.getEnderecoPais() != null && cliente.getEnderecoPais().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("pais"), "%" + cliente.getEnderecoPais() + "%"));
			}
			if (cliente.getCnpj() != null && !cliente.getCnpj().equals(Mascara.getCnpj().getPlaceholder())
					&& !cliente.getCnpj().equals(Mascara.getCnpjVazio())) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("cnpj"), "%" + cliente.getCnpj() + "%"));
			}
			if (cliente.getRgNumero() != null && cliente.getRgNumero().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("rgNumero"), "%" + cliente.getRgNumero() + "%"));
			}
			if (cliente.getRgOrgaoEmissor() != null && cliente.getRgOrgaoEmissor().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("rgOrgaoEmissor"),
						"%" + cliente.getRgOrgaoEmissor() + "%"));
			}
			if (cliente.getSalario() != null && cliente.getSalario().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("salario"), "%" + cliente.getSalario() + "%"));
			}
			if (cliente.getSexo() != null && cliente.getSexo().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("sexo"), "%" + cliente.getSexo() + "%"));
			}
			if (cliente.getIdade() != null && cliente.getIdade().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("idade"), "%" + cliente.getIdade() + "%"));
			}
			if (cliente.getEmpresa() != null) {
				predicateList.add(criteriaBuilder.equal(rootCliente.get("empresa"), cliente.getEmpresa()));
			}
			if (cliente.getBanco() != null) {
				predicateList.add(criteriaBuilder.equal(rootCliente.get("banco"), cliente.getBanco()));
			}
			if (cliente.getNumeroAgenciaBancaria() != null && cliente.getNumeroAgenciaBancaria().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("numeroAgenciaBancaria"),
						"%" + cliente.getNumeroAgenciaBancaria() + "%"));
			}
			if (cliente.getNumeroContaBancaria() != null && cliente.getNumeroContaBancaria().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("numeroContaBancaria"),
						"%" + cliente.getNumeroContaBancaria() + "%"));
			}
			if (cliente.getNomeReferencia1() != null && cliente.getNomeReferencia1().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("nomeReferencia1"),
						"%" + cliente.getNomeReferencia1() + "%"));
			}
			if (cliente.getNomeReferencia2() != null && cliente.getNomeReferencia2().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("nomeReferencia2"),
						"%" + cliente.getNomeReferencia2() + "%"));
			}
			if (cliente.getNomeReferencia3() != null && cliente.getNomeReferencia3().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("nomeReferencia3"),
						"%" + cliente.getNomeReferencia3() + "%"));
			}
			if (cliente.getFoneReferencia1() != null
					&& !cliente.getFoneReferencia1().equals(Mascara.getFone().getPlaceholder())
					&& !cliente.getFoneReferencia1().equals(Mascara.getFoneVazio())) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("foneReferencia1"),
						"%" + cliente.getFoneReferencia1() + "%"));
			}
			if (cliente.getFoneReferencia2() != null
					&& !cliente.getFoneReferencia2().equals(Mascara.getFone().getPlaceholder())
					&& !cliente.getFoneReferencia2().equals(Mascara.getFoneVazio())) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("foneReferencia2"),
						"%" + cliente.getFoneReferencia2() + "%"));
			}
			if (cliente.getFoneReferencia3() != null
					&& !cliente.getFoneReferencia3().equals(Mascara.getFone().getPlaceholder())
					&& !cliente.getFoneReferencia3().equals(Mascara.getFoneVazio())) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("foneReferencia3"),
						"%" + cliente.getFoneReferencia3() + "%"));
			}
			if (cliente.getRelacionamentoReferencia1() != null && cliente.getRelacionamentoReferencia1().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("relacionamentoReferencia1"),
						"%" + cliente.getRelacionamentoReferencia1() + "%"));
			}
			if (cliente.getRelacionamentoReferencia2() != null && cliente.getRelacionamentoReferencia2().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("relacionamentoReferencia2"),
						"%" + cliente.getRelacionamentoReferencia2() + "%"));
			}
			if (cliente.getRelacionamentoReferencia3() != null && cliente.getRelacionamentoReferencia3().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootCliente.get("relacionamentoReferencia3"),
						"%" + cliente.getRelacionamentoReferencia3() + "%"));
			}

			criteriaQuery.select(rootCliente).where(predicateList.toArray(new Predicate[] {}));
			clienteList = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return clienteList;
	}

	@Override
	public void salvarRegistro(Cliente cliente) {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		try {
			entityManager = Jpa.getEntityManagerFactory().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.merge(cliente);
			entityTransaction.commit();
		} catch (Exception exception) {
			entityTransaction.rollback();
			exception.printStackTrace();
			throw exception;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}
}