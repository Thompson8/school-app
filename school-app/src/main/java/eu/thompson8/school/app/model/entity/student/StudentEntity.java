package eu.thompson8.school.app.model.entity.student;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import eu.thompson8.school.app.model.entity.AbstractEntity;
import eu.thompson8.school.app.model.entity.course.CourseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "STUDENT")
@Getter
@Setter
public class StudentEntity extends AbstractEntity {

	@NotNull
	@Size(min = 1, max = 64)
	@Column(name = "FIRST_NAME", nullable = false, length = 64)
	private String firstName;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(name = "LAST_NAME", nullable = false, length = 64)
	private String lastName;

	@NotNull
	@Column(name = "BIRTH_DATE", nullable = false)
	private LocalDate birthDate;

	@ManyToMany(mappedBy = "students")
	private Set<CourseEntity> courses;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof StudentEntity)) {
			return false;
		}
		StudentEntity other = (StudentEntity) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "StudentEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate="
				+ birthDate + "]";
	}

}
