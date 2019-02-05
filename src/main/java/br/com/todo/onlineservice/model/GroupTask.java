package br.com.todo.onlineservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "GROUP_TASK")
public class GroupTask implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(max = 100)
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	public GroupTask(@NotNull @Size(max = 100) String name) {
		super();
		this.name = name;
	}

}
