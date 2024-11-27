package ro.tedyst.lab7.converters;


import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ro.tedyst.lab7.model.Activity;

@RequestScoped
@FacesConverter(value = "activityConverter", managed = true)
public class ActivityConverter implements Converter<Activity> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Activity getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("ActivityConverter.getAsObject: " + value);
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            long id = Long.parseLong(value);
            Activity m = em.find(Activity.class, id);
            return m;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Activity a) {
        if (a == null) {
            return "";
        }
        return String.valueOf(a.getId());
    }
}

