package com.rocioarroyo.futfemapp.dto;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {

    private String usrEmail;
    private String usrPassword;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String usrEmail, String usrPassword) {
        this.usrEmail = usrEmail;
        this.usrPassword = usrPassword;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }
}
