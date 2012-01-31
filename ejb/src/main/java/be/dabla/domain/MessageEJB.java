package be.dabla.domain;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import be.dabla.model.Message;

@Stateless(name="MessageEJB",mappedName="MessageEJB")
@Remote(MessageEJBRemote.class)
public class MessageEJB implements MessageEJBLocal {
	@PersistenceContext(unitName = "tomee-maven-demo-pu")
	protected EntityManager em;
	
	@Override
	public Number count() {
		return (Number)em.createQuery("SELECT COUNT(m.id) FROM Message m").getSingleResult();
	}
	
	@Override
	public List<Message> findAll(int start, int size) {
		return em.createNamedQuery("Message.findAll").setFirstResult(start).setMaxResults(size).getResultList();
	}
	
	@Override
	public Message findById(final Long id) {
		return em.find(Message.class, id);
	}
	
	@Override
	public Message create(final Message entity) {
		if (entity != null) {
			em.persist(entity);
		}
		
		return entity;
	}
	
	@Override
	public void delete(final Message entity) {
		if (entity != null) {
			em.remove(em.merge(entity));
		}
	}

	@Override
	public Message update(final Message entity) {
		if (entity != null) {
			return em.merge(entity);
		}
		
		return entity;
	}
}