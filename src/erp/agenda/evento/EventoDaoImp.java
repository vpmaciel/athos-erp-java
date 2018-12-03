package erp.agenda.evento;

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

final class EventoDaoImp implements EventoDao {

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
		stringBuilder.append(" order by C.data, C.horaInicio");
		PesquisaRegistro = stringBuilder.toString();
		return PesquisaRegistro;
	}

	@Override
	public void deletarRegistro(Evento evento) {
		EntityManager em = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(em.find(Evento.class, evento.getId()));
		tx.commit();
		em.close();
	}

	@Override
	public Evento getRegistro(Evento evento) {
		EntityManager em = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		return em.find(Evento.class, evento.getId());
	}

	@Override
	public Collection<Evento> getRegistro() {
		EntityManager em = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("from erp.agenda.evento.Evento C order by C.data, C.horaInicio");
		@SuppressWarnings("unchecked")
		List<Evento> list = query.getResultList();
		tx.commit();
		em.close();
		return list;
	}

	@Override
	public Collection<Evento> pesquisarRegistro(Evento evento) {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Evento> criteriaQuery = criteriaBuilder.createQuery(Evento.class);
		Root<Evento> rootEvento = criteriaQuery.from(Evento.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (evento.getId() != null) {
			predicates.add(criteriaBuilder.equal(rootEvento.get("id"), evento.getId()));
		}
		if (evento.getData() != null && !evento.getData().equals("__/__/____")) {
			predicates.add(criteriaBuilder.like(rootEvento.get("data"), "%" + evento.getData() + "%"));
		}
		if (evento.getDescricao() != null && !evento.getDescricao().equals("")) {
			predicates.add(criteriaBuilder.like(rootEvento.get("descricao"), "%" + evento.getDescricao() + "%"));
		}
		if (evento.getHoraInicio() != null && !evento.getHoraInicio().equals("__:__")) {
			predicates.add(criteriaBuilder.like(rootEvento.get("horaInicio"), "%" + evento.getHoraInicio() + "%"));
		}
		if (evento.getHoraTermino() != null && !evento.getHoraTermino().equals("__:__")) {
			predicates.add(criteriaBuilder.like(rootEvento.get("horaTermino"), "%" + evento.getHoraTermino() + "%"));
		}
		if (evento.getTipoEvento() != null && evento.getTipoEvento().getId() != null) {
			predicates.add(criteriaBuilder.equal(rootEvento.get("tipoEvento"), evento.getTipoEvento()));
			System.out.println(evento.getTipoEvento().getId());
		}
		
		criteriaQuery.select(rootEvento).where(predicates.toArray(new Predicate[] {}));

		List<Evento> list = entityManager.createQuery(criteriaQuery).getResultList();
		tx.commit();
		entityManager.close();
		return list;
	}

	@Override
	public void salvarRegistro(Evento evento) {
		EntityManager em = JPA.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(evento);
		tx.commit();
		em.close();
	}
}
