package erp.empresa;

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

final class EmpresaImp implements EmpresaDao {

	@Override
	public void deletarRegistro(Empresa empresa) {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		try {
			entityManager = Jpa.getEntityManagerFactory().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.remove(entityManager.find(Empresa.class, empresa.getId()));
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
	public Collection<Empresa> getRegistro() {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		List<Empresa> empresaList = null;

		try {
			entityManager = Jpa.getEntityManagerFactory().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createQuery("select T from Empresa T order by T.nomeFantasia", Empresa.class);
			empresaList = query.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return empresaList;
	}

	@Override
	public Empresa getRegistro(Empresa empresa) {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		try {
			entityManager = Jpa.getEntityManagerFactory().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			empresa = entityManager.find(Empresa.class, empresa.getId());
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return empresa;
	}

	@Override
	public Collection<Empresa> pesquisarRegistro(Empresa empresa) {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		List<Empresa> empresaList = null;
		try {
			entityManager = Jpa.getEntityManagerFactory().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Empresa> criteriaQuery = criteriaBuilder.createQuery(Empresa.class);
			Root<Empresa> rootEmpresa = criteriaQuery.from(Empresa.class);
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if (empresa.getId() != null) {
				predicateList.add(criteriaBuilder.equal(rootEmpresa.get("id"), empresa.getId()));
			}
			if (empresa.getEnderecoBairro() != null && empresa.getEnderecoBairro().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("enderecoBairro"), "%" + empresa.getEnderecoBairro() + "%"));
			}
			if (empresa.getCapitalSocial() != null && empresa.getCapitalSocial().length() > 0) {
				predicateList.add(
						criteriaBuilder.like(rootEmpresa.get("capitalSocial"), "%" + empresa.getCapitalSocial() + "%"));
			}
			if (empresa.getEnderecoCep() != null && !empresa.getEnderecoCep().equals(Mascara.getEnderecoCep().getPlaceholder())
					&& !empresa.getEnderecoCep().equals(Mascara.getEnderecoCepVazio())) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("enderecoCep"), "%" + empresa.getEnderecoCep() + "%"));
			}
			if (empresa.getEnderecoCidade() != null && empresa.getEnderecoCidade().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("enderecoCidade"), "%" + empresa.getEnderecoCidade() + "%"));
			}
			if (empresa.getCnpj() != null && !empresa.getCnpj().equals(Mascara.getCnpj().getPlaceholder())
					&& !empresa.getCnpj().equals(Mascara.getCnpjVazio())) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("cnpj"), "%" + empresa.getCnpj() + "%"));
			}
			if (empresa.getEnderecoComplemento() != null && empresa.getEnderecoComplemento().length() > 0) {
				predicateList.add(
						criteriaBuilder.like(rootEmpresa.get("enderecoComplemento"), "%" + empresa.getEnderecoComplemento() + "%"));
			}
			if (empresa.getDataFundacao() != null
					&& !empresa.getDataFundacao().equals(Mascara.getData().getPlaceholder())
					&& !empresa.getDataFundacao().equals(Mascara.getDataVazio())) {
				predicateList.add(
						criteriaBuilder.like(rootEmpresa.get("dataFundacao"), "%" + empresa.getDataFundacao() + "%"));
			}
			if (empresa.getEmail() != null && empresa.getEmail().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("email"), "%" + empresa.getEmail() + "%"));
			}
			if (empresa.getEnderecoEstado() != null && empresa.getEnderecoEstado().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("enderecoEstado"), "%" + empresa.getEnderecoEstado() + "%"));
			}
			if (empresa.getFaturamentoMensal() != null) {
				predicateList.add(
						criteriaBuilder.equal(rootEmpresa.get("faturamentoMensal"), empresa.getFaturamentoMensal()));
			}
			if (empresa.getFax() != null && !empresa.getFax().equals(Mascara.getFax().getPlaceholder())
					&& !empresa.getFax().equals(Mascara.getFaxVazio())) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("fax"), "%" + empresa.getFax() + "%"));
			}
			if (empresa.getFone1() != null && !empresa.getFone1().equals(Mascara.getFone().getPlaceholder())
					&& !empresa.getFone1().equals(Mascara.getFoneVazio())) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("fone1"), "%" + empresa.getFone1() + "%"));
			}
			if (empresa.getFone2() != null && !empresa.getFone2().equals(Mascara.getFone().getPlaceholder())
					&& !empresa.getFone2().equals(Mascara.getFoneVazio())) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("fone2"), "%" + empresa.getFone2() + "%"));
			}
			if (empresa.getInscricaoEstadual() != null && empresa.getInscricaoEstadual().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("inscricaoEstadual"),
						"%" + empresa.getInscricaoEstadual() + "%"));
			}
			if (empresa.getInscricaoMunicipal() != null && empresa.getInscricaoMunicipal().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("inscricaoMunicipal"),
						"%" + empresa.getInscricaoMunicipal() + "%"));
			}
			if (empresa.getEnderecoLogradouro() != null && empresa.getEnderecoLogradouro().length() > 0) {
				predicateList
						.add(criteriaBuilder.like(rootEmpresa.get("enderecoLogradouro"), "%" + empresa.getEnderecoLogradouro() + "%"));
			}
			if (empresa.getNomeFantasia() != null && empresa.getNomeFantasia().length() > 0) {
				predicateList.add(
						criteriaBuilder.like(rootEmpresa.get("nomeFantasia"), "%" + empresa.getNomeFantasia() + "%"));
			}
			if (empresa.getNumeroFuncionarios() != null && empresa.getNumeroFuncionarios().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("numeroFuncionarios"),
						"%" + empresa.getNumeroFuncionarios() + "%"));
			}
			if (empresa.getEnderecoPais() != null && empresa.getEnderecoPais().length() > 0) {
				predicateList.add(criteriaBuilder.like(rootEmpresa.get("pais"), "%" + empresa.getEnderecoPais() + "%"));
			}
			if (empresa.getRamoAtividade() != null && empresa.getRamoAtividade().length() > 0) {
				predicateList.add(
						criteriaBuilder.like(rootEmpresa.get("ramoAtividade"), "%" + empresa.getRamoAtividade() + "%"));
			}
			if (empresa.getRazaoSocial() != null && empresa.getRazaoSocial().length() > 0) {
				predicateList.add(
						criteriaBuilder.like(rootEmpresa.get("razaoSocial"), "%" + empresa.getRazaoSocial() + "%"));
			}
			if (empresa.getTipoEmpresa() != null && empresa.getTipoEmpresa().length() > 0) {
				predicateList.add(
						criteriaBuilder.like(rootEmpresa.get("tipoEmpresa"), "%" + empresa.getTipoEmpresa() + "%"));
			}

			criteriaQuery.select(rootEmpresa).where(predicateList.toArray(new Predicate[] {}));
			empresaList = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return empresaList;
	}

	@Override
	public void salvarRegistro(Empresa empresa) {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		try {
			entityManager = Jpa.getEntityManagerFactory().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.merge(empresa);
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