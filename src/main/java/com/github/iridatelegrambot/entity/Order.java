package com.github.iridatelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @Column(name = "number_order")
    private String number;

    @Column
    private String city;

    @JsonIgnore
    @Column(name = "id_user")
    private Long idUser;

    @JsonIgnore
    @Column(name = "status")
    private boolean statusActive;

    @JsonIgnore
    @Column(name = "date_created")
    private String date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<Invoice> invoiceList;


    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long id_user) {
        this.idUser = id_user;
    }

    public boolean isStatusActive() {
        return statusActive;
    }

    public void setStatusActive(boolean statusActive) {
        this.statusActive = statusActive;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Order order = (Order) object;
        return id == order.id &&
                statusActive == order.statusActive &&
                number.equals(order.number) &&
                Objects.equals(city, order.city) &&
                idUser.equals(order.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, city, idUser, statusActive);
    }

    @Override
    public String toString() {
        return "Order{" +
                "number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", id_user=" + idUser +
                ", status=" + statusActive +
                '}';
    }
}
