package ro.tedyst.lab7.converters;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ro.tedyst.lab7.model.MyUser;

@RequestScoped
@FacesConverter(value = "userConverter", managed = true)
public class MyUserConverter implements Converter<MyUser> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public MyUser getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("MyUserConverter.getAsObject: " + value);
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            long id = Long.parseLong(value);
            MyUser m = em.find(MyUser.class, id);
            return m;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, MyUser user) {
        if (user == null) {
            return "";
        }
        return String.valueOf(user.getId());
    }
}

