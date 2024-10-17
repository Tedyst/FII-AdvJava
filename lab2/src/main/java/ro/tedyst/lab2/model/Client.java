package ro.tedyst.lab2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToMany(mappedBy = "projects")
    private Set<Product> requestedProducts = new HashSet<>();

    private Integer startHour;
    private Integer endHour;

    public int getId() {
        return id;
    }

    public Set<Product> getRequestedProducts() {
        return requestedProducts;
    }

    public String getName() {
        return name;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRequestedProduct(Product product) {
        requestedProducts.add(product);
    }

    public void removeRequestedProduct(Product product) {
        requestedProducts.remove(product);
    }

    public void setHourInterval(Integer startHour, Integer endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }
}
