package com.test.quind.domain.commons.Enums;

public enum EnumValidateTransactionDTO {

 
	NULL_TRANSACTION_TYPE(1,"Tipo transaccion: ","es obligatorio"),
	NULL_AMOUNT(1,"Saldo: ","es obligatorio"),
	NULL_SUCCESSFUL(1,"exitoso: ","es obligatorio"),
	NULL_SOURCE_ACOUNT(1,"cuenta origen: ","es obligatorio"),
	NULL_DESTINATION_ACCOUNT(1, "cuenta destino: ", "es obligatorio");
	
    private final int code;
    private final String field;
    private final String description;


    EnumValidateTransactionDTO(int code, String field, String description) {
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

