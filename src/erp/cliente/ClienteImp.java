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

import arquitetura.JPA;
import arquitetura.validacao.Mascara;

final class ClienteImp implements ClienteDao {

	@Override
	public Cliente consultarRegistro(Cliente cliente) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> rootCliente = criteriaQuery.from(Cliente.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		boolean naoTemCriterio = true;

		if (cliente.getCpf() != null && !cliente.getCpf().equals(Mascara.getCpf().getPlaceholder())
				&& !cliente.getCpf().equals(Mascara.getCpfVazio())) {
			predicates.add(criteriaBuilder.equal(rootCliente.get("cpf"), cliente.getCpf()));
			naoTemCriterio = false;
		}
		if (cliente.getCnpj() != null && !cliente.getCnpj().equals(Mascara.getCnpj().getPlaceholder())
				&& !cliente.getCnpj().equals(Mascara.getCnpjVazio())) {
			predicates.add(criteriaBuilder.equal(rootCliente.get("cnpj"), cliente.getCnpj()));
			naoTemCriterio = false;
		}

		if (naoTemCriterio) {
			return new Cliente();
		}

		criteriaQuery.select(rootCliente).where(predicates.toArray(new Predicate[] {}));

		List<Cliente> list = entityManager.createQuery(criteriaQuery).getResultList();
		entityTransaction.commit();
		entityManager.close();
		return list.size() > 0 ? list.get(0) : new Cliente();
	}

	@Override
	public void deletarRegistro(Cliente cliente) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(entityManager.find(Cliente.class, cliente.getId()));
		entityTransaction.commit();
		entityManager.close();
	}

	@Override
	public Collection<Cliente> getRegistro() {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Query query = entityManager.createQuery("select T from Cliente T order by T.nome", Cliente.class);
		@SuppressWarnings("unchecked")
		List<Cliente> list = query.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return list;
	}

	@Override
	public Cliente getRegistro(Cliente cliente) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		return entityManager.find(Cliente.class, cliente.getId());
	}

	@Override
	public Collection<Cliente> pesquisarRegistro(Cliente cliente) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> rootCliente = criteriaQuery.from(Cliente.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (cliente.getId() != null) {
			predicates.add(criteriaBuilder.equal(rootCliente.get("id"), cliente.getId()));
		}
		if (cliente.getBairro() != null && cliente.getBairro().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("bairro"), "%" + cliente.getBairro() + "%"));
		}
		if (cliente.getCargo() != null && cliente.getCargo().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("cargo"), "%" + cliente.getCargo() + "%"));
		}
		if (cliente.getClasseEconomica() != null && cliente.getClasseEconomica().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootCliente.get("classeEconomica"), "%" + cliente.getClasseEconomica() + "%"));
		}
		if (cliente.getDataCadastro() != null && !cliente.getDataCadastro().equals(Mascara.getData().getPlaceholder())
				&& !cliente.getDataCadastro().equals(Mascara.getDataVazio() != null)) {
			predicates
					.add(criteriaBuilder.like(rootCliente.get("dataCadastro"), "%" + cliente.getDataCadastro() + "%"));
		}
		if (cliente.getCep() != null && !cliente.getCep().equals(Mascara.getCep().getPlaceholder())
				&& !cliente.getCep().equals(Mascara.getCepVazio())) {
			predicates.add(criteriaBuilder.like(rootCliente.get("cep"), "%" + cliente.getCep() + "%"));
		}
		if (cliente.getCidade() != null && cliente.getCidade().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("cidade"), "%" + cliente.getCidade() + "%"));
		}
		if (cliente.getComplemento() != null && cliente.getComplemento().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("complemento"), "%" + cliente.getComplemento() + "%"));
		}
		if (cliente.getCpf() != null && !cliente.getCpf().equals(Mascara.getCpf().getPlaceholder())
				&& !cliente.getCpf().equals(Mascara.getCpfVazio())) {
			predicates.add(criteriaBuilder.like(rootCliente.get("cpf"), "%" + cliente.getCpf() + "%"));
		}
		if (cliente.getEmail() != null && cliente.getEmail().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("email"), "%" + cliente.getEmail() + "%"));
		}
		if (cliente.getEstado() != null && cliente.getEstado().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("estado"), "%" + cliente.getEstado() + "%"));
		}
		if (cliente.getEstadoCivil() != null && cliente.getEstadoCivil().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("estadoCivil"), "%" + cliente.getEstadoCivil() + "%"));
		}
		if (cliente.getFax() != null && !cliente.getFax().equals(Mascara.getFax().getPlaceholder())
				&& !cliente.getFax().equals(Mascara.getFaxVazio())) {
			predicates.add(criteriaBuilder.like(rootCliente.get("fax"), "%" + cliente.getFax() + "%"));
		}
		if (cliente.getFone1() != null && !cliente.getFone1().equals(Mascara.getFone().getPlaceholder())
				&& !cliente.getFone1().equals(Mascara.getFoneVazio())) {
			predicates.add(criteriaBuilder.like(rootCliente.get("fone1"), "%" + cliente.getFone1() + "%"));
		}
		if (cliente.getFone2() != null && !cliente.getFone2().equals(Mascara.getFone().getPlaceholder())
				&& !cliente.getFone2().equals(Mascara.getFoneVazio())) {
			predicates.add(criteriaBuilder.like(rootCliente.get("fone2"), "%" + cliente.getFone2() + "%"));
		}
		if (cliente.getLogradouro() != null && cliente.getLogradouro().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("logradouro"), "%" + cliente.getLogradouro() + "%"));
		}
		if (cliente.getNome() != null && cliente.getNome().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("nome"), "%" + cliente.getNome() + "%"));
		}
		if (cliente.getPais() != null && cliente.getPais().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("pais"), "%" + cliente.getPais() + "%"));
		}
		if (cliente.getCnpj() != null && !cliente.getCnpj().equals(Mascara.getCnpj().getPlaceholder())
				&& !cliente.getCnpj().equals(Mascara.getCnpjVazio())) {
			predicates.add(criteriaBuilder.like(rootCliente.get("cnpj"), "%" + cliente.getCnpj() + "%"));
		}
		if (cliente.getRgNumero() != null && cliente.getRgNumero().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("rgNumero"), "%" + cliente.getRgNumero() + "%"));
		}
		if (cliente.getRgOrgaoEmissor() != null && cliente.getRgOrgaoEmissor().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootCliente.get("rgOrgaoEmissor"), "%" + cliente.getRgOrgaoEmissor() + "%"));
		}
		if (cliente.getSalario() != null && cliente.getSalario().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("salario"), "%" + cliente.getSalario() + "%"));
		}
		if (cliente.getSexo() != null && cliente.getSexo().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("sexo"), "%" + cliente.getSexo() + "%"));
		}
		if (cliente.getIdade() != null && cliente.getIdade().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("idade"), "%" + cliente.getIdade() + "%"));
		}
		if (cliente.getEmpresa() != null) {
			predicates.add(criteriaBuilder.equal(rootCliente.get("empresa"), cliente.getEmpresa()));
		}
		if (cliente.getBanco() != null) {
			predicates.add(criteriaBuilder.equal(rootCliente.get("banco"), cliente.getBanco()));
		}
		if (cliente.getNumeroAgenciaBancaria() != null && cliente.getNumeroAgenciaBancaria().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("numeroAgenciaBancaria"),
					"%" + cliente.getNumeroAgenciaBancaria() + "%"));
		}
		if (cliente.getNumeroContaBancaria() != null && cliente.getNumeroContaBancaria().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("numeroContaBancaria"),
					"%" + cliente.getNumeroContaBancaria() + "%"));
		}
		if (cliente.getNomeReferencia1() != null && cliente.getNomeReferencia1().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootCliente.get("nomeReferencia1"), "%" + cliente.getNomeReferencia1() + "%"));
		}
		if (cliente.getNomeReferencia2() != null && cliente.getNomeReferencia2().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootCliente.get("nomeReferencia2"), "%" + cliente.getNomeReferencia2() + "%"));
		}
		if (cliente.getNomeReferencia3() != null && cliente.getNomeReferencia3().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootCliente.get("nomeReferencia3"), "%" + cliente.getNomeReferencia3() + "%"));
		}
		if (cliente.getFoneReferencia1() != null
				&& !cliente.getFoneReferencia1().equals(Mascara.getFone().getPlaceholder())
				&& !cliente.getFoneReferencia1().equals(Mascara.getFoneVazio())) {
			predicates.add(
					criteriaBuilder.like(rootCliente.get("foneReferencia1"), "%" + cliente.getFoneReferencia1() + "%"));
		}
		if (cliente.getFoneReferencia2() != null
				&& !cliente.getFoneReferencia2().equals(Mascara.getFone().getPlaceholder())
				&& !cliente.getFoneReferencia2().equals(Mascara.getFoneVazio())) {
			predicates.add(
					criteriaBuilder.like(rootCliente.get("foneReferencia2"), "%" + cliente.getFoneReferencia2() + "%"));
		}
		if (cliente.getFoneReferencia3() != null
				&& !cliente.getFoneReferencia3().equals(Mascara.getFone().getPlaceholder())
				&& !cliente.getFoneReferencia3().equals(Mascara.getFoneVazio())) {
			predicates.add(
					criteriaBuilder.like(rootCliente.get("foneReferencia3"), "%" + cliente.getFoneReferencia3() + "%"));
		}
		if (cliente.getRelacionamentoReferencia1() != null && cliente.getRelacionamentoReferencia1().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("relacionamentoReferencia1"),
					"%" + cliente.getRelacionamentoReferencia1() + "%"));
		}
		if (cliente.getRelacionamentoReferencia2() != null && cliente.getRelacionamentoReferencia2().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("relacionamentoReferencia2"),
					"%" + cliente.getRelacionamentoReferencia2() + "%"));
		}
		if (cliente.getRelacionamentoReferencia3() != null && cliente.getRelacionamentoReferencia3().length() > 0) {
			predicates.add(criteriaBuilder.like(rootCliente.get("relacionamentoReferencia3"),
					"%" + cliente.getRelacionamentoReferencia3() + "%"));
		}

		criteriaQuery.select(rootCliente).where(predicates.toArray(new Predicate[] {}));

		List<Cliente> list = entityManager.createQuery(criteriaQuery).getResultList();
		entityTransaction.commit();
		entityManager.close();
		return list;
	}

	@Override
	public void salvarRegistro(Cliente cliente) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(cliente);
		entityTransaction.commit();
		entityManager.close();
	}
}
