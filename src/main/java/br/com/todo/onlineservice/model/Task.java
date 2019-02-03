package br.com.todo.onlineservice.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.com.todo.onlineservice.enumeration.StatusTaskEnum;
import br.com.todo.onlineservice.model.converter.StatusTaskConverter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TASK")
public class Task implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(max = 100)
	@Column(name = "TITLE", length = 250, nullable = false)
	private String title;

	@NotNull
	@Size(max = 250)
	@Column(name = "DESCRIPTION", length = 250, nullable = false)
	private String description;

	@Column(name = "DATE_CREATE", nullable = false)
	private LocalDateTime dateCreate;

	@Column(name = "DATE_UPDATE", nullable = false)
	private LocalDateTime dateUpdate;

	@Convert(converter = StatusTaskConverter.class)
	@Column(name = "STATUS", length = 1, nullable = false)
	private StatusTaskEnum status;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ID_GROUP", foreignKey = @ForeignKey(name = "FK_tASK"), nullable = true)
	private GroupTask group;

}
