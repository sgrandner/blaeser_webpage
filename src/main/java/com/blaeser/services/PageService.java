package com.blaeser.services;

import com.blaeser.models.Page;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//@Stateless
public class PageService {

//	@PersistenceContext()
//	private EntityManager entityManager;

//	public List<Page> getAll() {
//
//		Query query = entityManager.createQuery(
//				"SELECT p.id, p.name" +
//				"FROM pages AS p" +
//				"WHERE p.id > 1");
//
////		query.setParameter('1', "1");
//
//		List<?> tempResultList = query.getResultList();
//		Iterator<?> iterator = tempResultList.iterator();
//
//		List<Page> resultList = new ArrayList<>();
//
//		while (iterator.hasNext()) {
//
//			Object[] row = (Object[]) iterator.next();
//
//			Page tempPage = new Page();
//			tempPage.setId((Integer) row[0]);
//			tempPage.setName((String) row[1]);
//
//			resultList.add(tempPage);
//		}
//
//		return resultList;
//	}
}
