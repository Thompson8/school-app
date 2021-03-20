package eu.thompson8.school.app.model.entity.course;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import eu.thompson8.school.app.model.entity.AbstractEntity;
import eu.thompson8.school.app.model.entity.student.StudentEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "COURSE")
@Getter
@Setter
public class CourseEntity extends AbstractEntity {

	@NotNull
	@Size(min = 1, max = 128)
	@Column(name = "CODE", nullable = false, length = 128)
	private String code;
	
	@NotNull
	@Size(min = 1, max = 128)
	@Column(name = "NAME", nullable = false, length = 128)
	private String name;

	@Lob
	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToMany
	@JoinTable(name = "ENROLLED", joinColumns = @JoinColumn(name = "STUDENT"), inverseJoinColumns = @JoinColumn(name = "COURSE"))
	private Set<StudentEntity> students;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CourseEntity)) {
			return false;
		}
		CourseEntity other = (CourseEntity) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "CourseEntity [id=" + id + ", name=" + name + "]";
	}

}
