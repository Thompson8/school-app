package eu.thompson8.school.app.util.sorting;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortingParser {

	public static Sort parse(List<String> sorts) {
		if (sorts == null || sorts.isEmpty()) {
			return Sort.unsorted();
		} else {
			Sort result = null;
			for (String toParse : sorts) {
				String[] parts = toParse.split(":");
				Sort sort = Sort.by(Direction.fromString(parts[1]), parts[0]);
				result = result != null ? result.and(sort) : sort;
			}

			return result;
		}
	}

	private SortingParser() {
	}

}
