package be.dabla.domain;

import java.util.List;

import javax.ejb.Local;

@Local
public interface EJBLocal<T> {
	public Number count();
	public List<T> findAll(int start, int size);
}