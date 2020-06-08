package com.rocioarroyo.futfemapp.dto;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {

    private String usrEmail;
    private String usrPassword;

    /**
     * Constructor vacio
     */
    public UsuarioDTO() {
    }

    /**
     * Constructor con los campos de la clase
     * @param usrEmail
     * @param usrPassword
     */
    public UsuarioDTO(String usrEmail, String usrPassword) {
        this.usrEmail = usrEmail;
        this.usrPassword = usrPassword;
    }

    /**
     * getUsrEmail
     * @return usrEmail
     */
    public String getUsrEmail() {
        return usrEmail;
    }

    /**
     * setUsrEmail
     * @param usrEmail
     */
    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    /**
     * getUsrPassword
     * @return usrPassword
     */
    public String getUsrPassword() {
        return usrPassword;
    }

    /**
     * setUsrPassword
     * @param usrPassword
     */
    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }
}
