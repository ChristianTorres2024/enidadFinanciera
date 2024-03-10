package com.test.quind.domain.commons.Enums;

public enum EnumValidatePoductDTO {

	NULL_ACCOUNT_TYPE(1, "Tipo CUENTA: ", "es obligatorio"), NULL_STATUS(1, "Status: ", "es obligatorio"),
	NULL_BALANCE(1, "Balance: ", "es obligatorio"), NULL_GMFEXEMPT(1, "gmf exempt: ", "es obligatorio"),
	NULL_CLIENTdto(1, "clientId: ", "es obligatorio"),

	INVALID_ACCOUNT_TYPE(1, "Tipo de cuenta inválido: ", "solo se permite 'cuenta corriente' o 'cuenta de ahorros'"),	
	NEGATIVE_BALANCE(1, "Saldo negativo: ", "el saldo de la cuenta de ahorro no puede ser menor a $0"),
	INVALID_ACCOUNT_NUMBER(1, "Número de cuenta inválido: ",
			"el número de cuenta debe tener 10 dígitos numéricos y empezar con '33' para cuentas corrientes o '53' para cuentas de ahorros"),;

	private final int code;
	private final String field;
	private final String description;

	EnumValidatePoductDTO(int code, String field, String description) {
		this.code = code;
		this.field = field;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getField() {
		return field;
	}

	public String getDescription() {
		return description;
	}
}
