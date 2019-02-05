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

import br.com.todo.onlineservice.model.GroupTask;

public class CustomGroupTaskRepositoryImpl implements CustomGroupTaskRepository {

	private EntityManager em;

	public CustomGroupTaskRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public Page<GroupTask> search(final String filter, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<GroupTask> cq = cb.createQuery(GroupTask.class);

		Root<GroupTask> orderFrom = cq.from(GroupTask.class);

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNoneBlank(filter)) {
			predicates.add(cb.like(orderFrom.get("name"), "%" + filter + "%"));
		}

		CriteriaQuery<GroupTask> select = cq.where(predicates.toArray(new Predicate[predicates.size()]));

		Long count = countResult(filter);
		if (count == 0) {
			return new PageImpl<>(Collections.emptyList(), pageable, count);
		}

		TypedQuery<GroupTask> typedQuery = em.createQuery(select);
		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());

		return new PageImpl<>(typedQuery.getResultList(), pageable, count);
	}

	private Long countResult(String filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<GroupTask> orderFrom = cq.from(GroupTask.class);
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNoneBlank(filter)) {
			predicates.add(cb.like(orderFrom.get("name"), "%" + filter + "%"));
		}

		CriteriaQuery<Long> countSelect = cq.select(cb.count(orderFrom.get("id")));
		countSelect.where(predicates.toArray(new Predicate[predicates.size()]));

		return em.createQuery(countSelect).getSingleResult();
	}

}
