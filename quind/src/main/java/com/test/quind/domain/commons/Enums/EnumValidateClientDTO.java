package com.test.quind.domain.commons.Enums;

public enum EnumValidateClientDTO {

 
	NULL_ID_TYPE(1,"Tipo identificacion: ","es obligatorio"),
	NULL_ID_NUMBER(1,"Numero identificacion: ","es obligatorio"),
	NULL_FIRTSNAME(1,"Nombre: ","es obligatorio"),
	NULL_LASTNAME(1,"Apellido: ","es obligatorio"),
	NULL_UNDERAGE(1, "Fecha de nacimiento: ", "es obligatorio"),
	NULL_EMAIL(1, "Email: ", "es obligatorio"),
	
	
	UNDERAGE(1, "Fecha de nacimiento: ", "El cliente debe ser mayor de edad"),
    INVALID_EMAIL(1, "Email: ", "El correo electrónico no tiene un formato válido"),
    INVALID_DATEBIRTH(1, "Fecha de nacimiento: ", " no tiene un formato válido"),
    SHORT_NAME_LASTNAME(1, "FirstName - LastName: ", "El nombre y el apellido deben tener al menos 2 caracteres y solo texto"),
	
	DUPLICATEKEYS(1, "TIPO Y NUMERO DE IDENTIFICACION: ", "El numero de identificacion para el tipo de documento ya existe");
	
    private final int code;
    private final String field;
    private final String description;


    EnumValidateClientDTO(int code, String field, String description) {
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

