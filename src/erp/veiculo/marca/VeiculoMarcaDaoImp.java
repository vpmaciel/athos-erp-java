package erp.veiculo.marca;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import erp.aop.JPA;

final class VeiculoMarcaDaoImp implements VeiculoMarcaDao {

	@Override
	public String construirQuery(StringBuilder stringBuilder) {
		String PesquisaRegistro = stringBuilder.toString();
		if (PesquisaRegistro.endsWith("and")) {
			PesquisaRegistro = stringBuilder.substring(0, stringBuilder.length() - 4);
			stringBuilder = new StringBuilder(PesquisaRegistro);
		}
		if (PesquisaRegistro.endsWith("where")) {
			PesquisaRegistro = stringBuilder.substring(0, stringBuilder.length() - 5);
			stringBuilder = new StringBuilder(PesquisaRegistro);
		}
		stringBuilder.append(" order by C.marca");
		PesquisaRegistro = stringBuilder.toString();
		return PesquisaRegistro;
	}

	@Override
	public void deletarRegistro(VeiculoMarca veiculoMarca) {
		EntityManager em = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(em.find(VeiculoMarca.class, veiculoMarca.getId()));
		tx.commit();
		em.close();
	}

	@Override
	public VeiculoMarca getRegistro(VeiculoMarca veiculoMarca) {
		EntityManager em = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		return em.find(VeiculoMarca.class, veiculoMarca.getId());
	}

	@Override
	public Collection<VeiculoMarca> getRegistro() {
		EntityManager em = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("from erp.veiculo.marca.VeiculoMarca C order by C.modelo");
		@SuppressWarnings("unchecked")
		List<VeiculoMarca> list = query.getResultList();
		tx.commit();
		em.close();
		return list;
	}

	@Override
	public Collection<VeiculoMarca> pesquisarRegistro(VeiculoMarca veiculoMarca) {
		StringBuilder qsb = new StringBuilder();
		qsb.setLength(0);
		qsb = new StringBuilder();
		qsb.append("select C from erp.veiculo.marca.VeiculoMarca C where");
		HashMap<String, Object> parametros = new HashMap<String, Object>();

		if (veiculoMarca.getId() != null) {
			qsb.append(" C.id = :id and");
			parametros.put("id", veiculoMarca.getId());
		}
		if (veiculoMarca.getMarca() != null && !veiculoMarca.getMarca().trim().equals("")) {
			qsb.append(" C.marca like :marca and");
			parametros.put("marca", "%" + veiculoMarca.getMarca() + "%");
		}

		EntityManager em = JPA.getEntityManagerFactory().createEntityManager();
		Query query = em.createQuery(construirQuery(qsb));
		Set<Map.Entry<String, Object>> set = parametros.entrySet();

		for (Map.Entry<String, Object> me : set) {
			query.setParameter(me.getKey(), me.getValue());
		}
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		@SuppressWarnings("unchecked")
		List<VeiculoMarca> list = query.getResultList();
		tx.commit();
		em.close();
		return list;
	}

	@Override
	public void salvarRegistro(VeiculoMarca veiculoMarca) {
		EntityManager em = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(veiculoMarca);
		tx.commit();
		em.close();
	}
}
