package ro.tedyst.lab7.converters;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import ro.tedyst.lab7.model.SubmissionType;

@RequestScoped
@FacesConverter(value = "submissionTypeConverter", managed = true)
public class SubmissionTypeConverter implements Converter<SubmissionType> {
    @Override
    public SubmissionType getAsObject(FacesContext context, UIComponent component, String value) {
        return SubmissionType.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, SubmissionType s) {
        return s.name();
    }
}


