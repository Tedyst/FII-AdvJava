package ro.tedyst.lab3.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class LocaleBean implements Serializable {
    private String locale = "en";

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
