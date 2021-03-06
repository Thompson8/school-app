package eu.thompson8.school.app.util.rsql.core;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;

public class GenericRsqlSpecification<T> implements Specification<T> {

	private static final long serialVersionUID = 1L;

	private final String property;

	private final transient ComparisonOperator operator;

	private final List<String> arguments;

	public GenericRsqlSpecification(String property, ComparisonOperator operator, List<String> arguments) {
		this.property = property;
		this.operator = operator;
		this.arguments = arguments;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Object> args = castArguments(root);
		Object argument = args.get(0);
		RsqlSearchOperation operation = RsqlSearchOperation.getSimpleOperator(operator);

		switch (operation) {
		case EQUAL:
			return processEqual(argument, root, builder);
		case NOT_EQUAL:
			return processNotEqual(argument, root, builder);
		case GREATER_THAN:
			return builder.greaterThan(root.<String>get(property), argument.toString());
		case GREATER_THAN_OR_EQUAL:
			return builder.greaterThanOrEqualTo(root.<String>get(property), argument.toString());
		case LESS_THAN:
			return builder.lessThan(root.<String>get(property), argument.toString());
		case LESS_THAN_OR_EQUAL:
			return builder.lessThanOrEqualTo(root.<String>get(property), argument.toString());
		case IN:
			return root.get(property).in(args);
		case NOT_IN:
			return builder.not(root.get(property).in(args));
		default:
			throw new IllegalArgumentException(String.format("Unimplemented rsql operation %s", operation));
		}
	}

	private Predicate processEqual(Object argument, Root<T> root, CriteriaBuilder builder) {
		if (argument instanceof String) {
			return builder.like(root.get(property), argument.toString().replace('*', '%'));
		} else if (argument == null) {
			return builder.isNull(root.get(property));
		} else {
			return builder.equal(root.get(property), argument);
		}
	}

	private Predicate processNotEqual(Object argument, Root<T> root, CriteriaBuilder builder) {
		if (argument instanceof String) {
			return builder.notLike(root.<String>get(property), argument.toString().replace('*', '%'));
		} else if (argument == null) {
			return builder.isNotNull(root.get(property));
		} else {
			return builder.notEqual(root.get(property), argument);
		}
	}

	private List<Object> castArguments(final Root<T> root) {
		Class<? extends Object> type = root.get(property).getJavaType();
		return arguments.stream().map(arg -> castArgument(arg, type)).collect(Collectors.toList());
	}

	private Object castArgument(String arg, Class<? extends Object> type) {
		if (type.equals(Integer.class)) {
			return Integer.parseInt(arg);
		} else if (type.equals(Long.class)) {
			return Long.parseLong(arg);
		} else {
			return arg;
		}
	}

}