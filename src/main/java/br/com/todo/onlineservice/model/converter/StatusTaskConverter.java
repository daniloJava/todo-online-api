package br.com.todo.onlineservice.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.todo.onlineservice.enumeration.StatusTaskEnum;

@Converter
public class StatusTaskConverter implements AttributeConverter<StatusTaskEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(StatusTaskEnum value) {
		return value != null ? value.getId() : null;
	}

	@Override
	public StatusTaskEnum convertToEntityAttribute(Integer value) {
		return StatusTaskEnum.valueOfId(value);
	}

}
