package br.com.todo.onlineservice.enumeration;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum StatusTaskEnum {

	CREATE(1, "Incluir"), DOING(2, "Doing"), FINALIZED(3, "Finalized");

	private static final Map<Integer, StatusTaskEnum> LOOKUP = new HashMap<>();

	static {
		for (StatusTaskEnum e : StatusTaskEnum.values()) {
			LOOKUP.put(e.getId(), e);
		}
	}

	private final int id;
	private final String descricao;

	StatusTaskEnum(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static StatusTaskEnum valueOfId(Integer id) {
		return id != null ? LOOKUP.get(id) : null;
	}

}
