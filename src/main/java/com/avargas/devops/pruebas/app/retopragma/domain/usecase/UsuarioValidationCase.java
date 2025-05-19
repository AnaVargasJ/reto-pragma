package com.avargas.devops.pruebas.app.retopragma.domain.usecase;


import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

public class UsuarioValidationCase {

    public void validateUserFields(UsuarioModel usuarioModel) {
        if (isNullOrEmpty(usuarioModel.getNombre()) ) {
            throw new UsuariosDomainException("El campo nombre es obligatorio");
        }

        if(isNullOrEmpty(usuarioModel.getApellido())){
            throw new UsuariosDomainException("El campo nombre es obligatorio");
        }

        if (isNullOrEmpty(usuarioModel.getNumeroDocumento()) ){
            throw new UsuariosDomainException("El campo numero de documento es obligatorio");
        }

        if (!isNumeric(usuarioModel.getNumeroDocumento())) {
            throw new UsuariosDomainException("El documento de identidad debe ser numérico");
        }

        if (isNullOrEmpty(usuarioModel.getCelular()) ){
            throw new UsuariosDomainException("El campo celular es obligatorio");
        }

        if (isNullOrEmpty(usuarioModel.getCorreo())) {
            throw new UsuariosDomainException("El correo es obligatorio");
        }

        if (!isValidEmail(usuarioModel.getCorreo())) {
            throw new UsuariosDomainException("Correo no tiene una estructura válida");
        }

        if (!isValidPhone(usuarioModel.getCelular())) {
            throw new UsuariosDomainException("Celular no tiene un formato válido");
        }


        if (usuarioModel.getFechaNacimiento() == null) {
            throw new UsuariosDomainException("La fecha de nacimiento es obligatoria");
        }

        if (!isAdult(usuarioModel.getFechaNacimiento())) {
            throw new UsuariosDomainException("El usuario debe ser mayor de edad");
        }
    }

    public void validaLoginFiels(String correo, String clave) {
        if (isNullOrEmpty(correo))
            throw new UsuariosDomainException("El correo es obligatorio");

        if (!isValidEmail(correo))
            throw new UsuariosDomainException("Correo no tiene una estructura válida");

        if (isNullOrEmpty(clave))
            throw new UsuariosDomainException("El campo clave es obligatorio");
    }



    private Boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private Boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }

    private Boolean isValidPhone(String phone) {
        String regex = "^\\+?[0-9]{1,13}$";
        return Pattern.matches(regex, phone);
    }

    private Boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private Boolean isAdult(Date fechaNacimiento) {
        LocalDate birthDate = fechaNacimiento.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate today = LocalDate.now();
        return birthDate.plusYears(18).isBefore(today) || birthDate.plusYears(18).isEqual(today);
    }

}