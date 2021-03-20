package eu.thompson8.school.app.util.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.thompson8.school.app.model.entity.AbstractEntity;

public class MapperUtil {

	public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
		if (list == null || list.isEmpty()) {
			return Collections.emptyList();
		} else {
			return list.stream().map(mapper).collect(Collectors.toList());
		}
	}

	public static <T, R> List<R> mapSetToList(Set<T> set, Function<T, R> mapper) {
		if (set == null || set.isEmpty()) {
			return Collections.emptyList();
		} else {
			return set.stream().map(mapper).collect(Collectors.toList());
		}
	}

	public static <T extends AbstractEntity, P> void mapEntityList(List<P> fromIds, Set<T> toEntitySet,
			JpaRepository<T, P> repository) {
		if (fromIds == null || fromIds.isEmpty()) {
			toEntitySet.clear();
		} else {
			if (toEntitySet.isEmpty()) {
				toEntitySet.addAll(repository.findAllById(fromIds));
			} else {
				toEntitySet.removeIf(e -> !fromIds.contains(e.getId()));
				List<Long> toIds = toEntitySet.stream().map(AbstractEntity::getId).collect(Collectors.toList());
				fromIds.removeIf(toIds::contains);

				toEntitySet.addAll(repository.findAllById(fromIds));
			}
		}
	}

	private MapperUtil() {
	}

}
