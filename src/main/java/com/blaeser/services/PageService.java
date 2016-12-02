package com.blaeser.services;

import com.blaeser.models.Page;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PageService {

	@PersistenceContext()
	private EntityManager entityManager;

	public List<Page> getAll() {

		return null;
	}
}
