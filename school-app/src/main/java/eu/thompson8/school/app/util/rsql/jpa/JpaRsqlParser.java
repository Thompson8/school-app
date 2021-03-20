package eu.thompson8.school.app.util.rsql.jpa;

import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.RSQLParser;
import eu.thompson8.school.app.util.rsql.core.CustomRsqlVisitor;

public class JpaRsqlParser {

	public static <T> Specification<T> parse(String filter) {
		return filter != null ? new RSQLParser().parse(filter).accept(new CustomRsqlVisitor<T>()) : null;
	}

	private JpaRsqlParser() {
	}

}
