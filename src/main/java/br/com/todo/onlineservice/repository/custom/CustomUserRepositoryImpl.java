package br.com.todo.onlineservice.repository.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.todo.onlineservice.model.User;

public class CustomUserRepositoryImpl implements CustomUserRepository {

	private EntityManager em;

	public CustomUserRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public Page<User> search(String filter, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);

		Root<User> orderFrom = cq.from(User.class);
		orderFrom.fetch("group");

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNoneBlank(filter)) {
			predicates.add(cb.like(orderFrom.get("userName"), "%" + filter + "%"));
		}

		CriteriaQuery<User> select = cq.where(predicates.toArray(new Predicate[predicates.size()]));
		Long count = countResult(filter);
		if (count == 0) {
			return new PageImpl<>(Collections.emptyList(), pageable, count);
		}

		TypedQuery<User> typedQuery = em.createQuery(select);
		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());

		return new PageImpl<>(typedQuery.getResultList(), pageable, count);
	}

	private Long countResult(String filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<User> orderFrom = cq.from(User.class);
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNoneBlank(filter)) {
			predicates.add(cb.like(orderFrom.get("username"), "%" + filter + "%"));
		}

		CriteriaQuery<Long> countSelect = cq.select(cb.count(orderFrom.get("id")));
		countSelect.where(predicates.toArray(new Predicate[predicates.size()]));

		return em.createQuery(countSelect).getSingleResult();
	}

	@Override
	public User getActiveUser(String userName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);

		Root<User> orderFrom = cq.from(User.class);
		orderFrom.fetch("group");

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNoneBlank(userName)) {
			predicates.add(cb.like(orderFrom.get("title"), "%" + userName + "%"));
		}
		predicates.add(cb.and(orderFrom.get("enabled").in(1)));

		CriteriaQuery<User> select = cq.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<User> typedQuery = em.createQuery(select);

		return typedQuery.getResultList().get(0);
	}

}
