package cn.zectec.contraceptive.management.system.repository.util;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.BooleanOperator;

public class DynamicSpecifications<T> {

	public static <T> Specification<T> bySearchFilter(SearchFilter filter) {
		ArrayList<SearchFilter> list = new ArrayList<SearchFilter>();
		list.add(filter);
		return bySearchFilters(list);
	}

	public static <T> Specification<T> bySearchFilters(final Collection<SearchFilter> filters) {
		return new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,CriteriaBuilder builder) {
				if (((filters != null) && !(filters.isEmpty()))) {
					Predicate p = null;
					for (SearchFilter filter : filters) {
						// 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
						String[] names = filter.fieldName.split("\\.");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}
						switch (filter.operator) {
						case EQ:
							p = joinPredicate(builder, p,builder.equal(expression, filter.value),filter.booleanOperator);
							break;
						case LIKE:
							p = joinPredicate(
									builder,
									p,
									builder.like(expression, "%" + filter.value + "%"), filter.booleanOperator);
							break;
						case GT:
							p = joinPredicate(builder, p, builder.greaterThan(
									expression, (Comparable) filter.value),
									filter.booleanOperator);
							break;
						case LT:
							p = joinPredicate(builder, p, builder.lessThan(
									expression, (Comparable) filter.value),
									filter.booleanOperator);
							break;
						case GTE:
							p = joinPredicate(builder, p,
									builder.greaterThanOrEqualTo(expression,
											(Comparable) filter.value),
									filter.booleanOperator);
							break;
						case LTE:
							p = joinPredicate(builder, p,
									builder.lessThanOrEqualTo(expression,
											(Comparable) filter.value),
									filter.booleanOperator);
							break;
						case NEQ:
							p = joinPredicate(builder, p, builder.notEqual(
									expression, (Comparable)filter.value),
									filter.booleanOperator);
							break;
						}
					}
					return p;

				}

				return builder.conjunction();
			}
		};
	}

	private static Predicate joinPredicate(CriteriaBuilder builder,Predicate first, Predicate second, BooleanOperator booleanOperator) {
		Predicate p = null;
		if (first == null) {
			p = second;
		} else {
			switch (booleanOperator) {
			case AND:
				p = builder.and(first, second);
				break;
			case OR:
				p = builder.or(first, second);
				break;
			}
		}
		return p;
	}
}