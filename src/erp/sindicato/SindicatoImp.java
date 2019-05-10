package erp.sindicato;

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

final class SindicatoImp implements SindicatoDao {

	@Override
	public Sindicato consultarRegistro(Sindicato sindicato) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sindicato> criteriaQuery = criteriaBuilder.createQuery(Sindicato.class);
		Root<Sindicato> rootSindicato = criteriaQuery.from(Sindicato.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		boolean naoTemCriterio = true;

		if (sindicato.getCnpj() != null && !sindicato.getCnpj().equals(Mascara.getCnpj().getPlaceholder())
				&& !sindicato.getCnpj().equals(Mascara.getCnpjVazio())) {
			predicates.add(criteriaBuilder.equal(rootSindicato.get("cnpj"), sindicato.getCnpj()));
			naoTemCriterio = false;
		}

		if (naoTemCriterio) {
			return new Sindicato();
		}

		criteriaQuery.select(rootSindicato).where(predicates.toArray(new Predicate[] {}));

		List<Sindicato> list = entityManager.createQuery(criteriaQuery).getResultList();
		entityTransaction.commit();
		entityManager.close();

		return list.size() > 0 ? list.get(0) : new Sindicato();
	}

	@Override
	public void deletarRegistro(Sindicato sindicato) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(entityManager.find(Sindicato.class, sindicato.getId()));
		entityTransaction.commit();
		entityManager.close();
	}

	@Override
	public Collection<Sindicato> getRegistro() {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Query query = entityManager.createQuery("select T from Sindicato T order by T.nomeFantasia", Sindicato.class);
		@SuppressWarnings("unchecked")
		List<Sindicato> list = query.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return list;
	}

	@Override
	public Sindicato getRegistro(Sindicato sindicato) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		return entityManager.find(Sindicato.class, sindicato.getId());
	}

	@Override
	public Collection<Sindicato> pesquisarRegistro(Sindicato sindicato) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sindicato> criteriaQuery = criteriaBuilder.createQuery(Sindicato.class);
		Root<Sindicato> rootSindicato = criteriaQuery.from(Sindicato.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (sindicato.getId() != null) {
			predicates.add(criteriaBuilder.equal(rootSindicato.get("id"), sindicato.getId()));
		}
		if (sindicato.getBairro() != null && sindicato.getBairro().length() > 0) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("bairro"), "%" + sindicato.getBairro() + "%"));
		}
		if (sindicato.getCapitalSocial() != null && sindicato.getCapitalSocial().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootSindicato.get("capitalSocial"), "%" + sindicato.getCapitalSocial() + "%"));
		}
		if (sindicato.getCep() != null && !sindicato.getCep().equals(Mascara.getCep().getPlaceholder())
				&& !sindicato.getCep().equals(Mascara.getCepVazio())) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("cep"), "%" + sindicato.getCep() + "%"));
		}
		if (sindicato.getCidade() != null && sindicato.getCidade().length() > 0) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("cidade"), "%" + sindicato.getCidade() + "%"));
		}
		if (sindicato.getCnpj() != null && !sindicato.getCnpj().equals(Mascara.getCnpj().getPlaceholder())
				&& !sindicato.getCnpj().equals(Mascara.getCnpjVazio())) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("cnpj"), "%" + sindicato.getCnpj() + "%"));
		}
		if (sindicato.getComplemento() != null && sindicato.getComplemento().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootSindicato.get("complemento"), "%" + sindicato.getComplemento() + "%"));
		}
		if (sindicato.getDataFundacao() != null
				&& !sindicato.getDataFundacao().equals(Mascara.getData().getPlaceholder())
				&& !sindicato.getDataFundacao().equals(Mascara.getDataVazio())) {
			predicates.add(
					criteriaBuilder.like(rootSindicato.get("dataFundacao"), "%" + sindicato.getDataFundacao() + "%"));
		}
		if (sindicato.getEmail() != null && sindicato.getEmail().length() > 0) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("email"), "%" + sindicato.getEmail() + "%"));
		}
		if (sindicato.getEstado() != null && sindicato.getEstado().length() > 0) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("estado"), "%" + sindicato.getEstado() + "%"));
		}
		if (sindicato.getFaturamentoMensal() != null && sindicato.getFaturamentoMensal().length() > 0) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("faturamentoMensal"),
					"%" + sindicato.getFaturamentoMensal() + "%"));
		}
		if (sindicato.getFax() != null && !sindicato.getFax().equals(Mascara.getFax().getPlaceholder())
				&& !sindicato.getFax().equals(Mascara.getFaxVazio())) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("fax"), "%" + sindicato.getFax() + "%"));
		}
		if (sindicato.getFone1() != null && !sindicato.getFone1().equals(Mascara.getFone().getPlaceholder())
				&& !sindicato.getFone1().equals(Mascara.getFoneVazio())) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("fone1"), "%" + sindicato.getFone1() + "%"));
		}
		if (sindicato.getFone2() != null && !sindicato.getFone2().equals(Mascara.getFone().getPlaceholder())
				&& !sindicato.getFone2().equals(Mascara.getFoneVazio())) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("fone2"), "%" + sindicato.getFone2() + "%"));
		}
		if (sindicato.getInscricaoEstadual() != null && sindicato.getBairro().length() > 0) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("inscricaoEstadual"),
					"%" + sindicato.getInscricaoEstadual() + "%"));
		}
		if (sindicato.getInscricaoMunicipal() != null && sindicato.getInscricaoMunicipal().length() > 0) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("inscricaoMunicipal"),
					"%" + sindicato.getInscricaoMunicipal() + "%"));
		}
		if (sindicato.getLogradouro() != null && sindicato.getLogradouro().length() > 0) {
			predicates
					.add(criteriaBuilder.like(rootSindicato.get("logradouro"), "%" + sindicato.getLogradouro() + "%"));
		}
		if (sindicato.getNomeFantasia() != null && sindicato.getNomeFantasia().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootSindicato.get("nomeFantasia"), "%" + sindicato.getNomeFantasia() + "%"));
		}
		if (sindicato.getNumeroFuncionarios() != null && sindicato.getNumeroFuncionarios().length() > 0) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("numeroFuncionarios"),
					"%" + sindicato.getNumeroFuncionarios() + "%"));
		}
		if (sindicato.getPais() != null && sindicato.getPais().length() > 0) {
			predicates.add(criteriaBuilder.like(rootSindicato.get("pais"), "%" + sindicato.getPais() + "%"));
		}
		if (sindicato.getRamoAtividade() != null && sindicato.getRamoAtividade().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootSindicato.get("ramoAtividade"), "%" + sindicato.getRamoAtividade() + "%"));
		}
		if (sindicato.getRazaoSocial() != null && sindicato.getRazaoSocial().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootSindicato.get("razaoSocial"), "%" + sindicato.getRazaoSocial() + "%"));
		}
		if (sindicato.getTipoSindicato() != null && sindicato.getTipoSindicato().length() > 0) {
			predicates.add(
					criteriaBuilder.like(rootSindicato.get("tipoSindicato"), "%" + sindicato.getTipoSindicato() + "%"));
		}

		criteriaQuery.select(rootSindicato).where(predicates.toArray(new Predicate[] {}));

		List<Sindicato> list = entityManager.createQuery(criteriaQuery).getResultList();
		entityTransaction.commit();
		entityManager.close();
		return list;
	}

	@Override
	public void salvarRegistro(Sindicato sindicato) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(sindicato);
		entityTransaction.commit();
		entityManager.close();
	}
}
