package ro.tedyst.lab3.model;

import jakarta.enterprise.inject.Model;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Integer startHour;
    private Integer endHour;

    public int getId() {
        return id;
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

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }
}
