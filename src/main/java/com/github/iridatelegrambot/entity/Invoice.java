package com.github.iridatelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @Column(name = "id_invoice")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @Column(name = "number_invoice")
    private String number;
    @Column
    private String city;
    @JsonIgnore
    @Column(name = "id_user")
    private Long idUser;
    @JsonIgnore
    @Column
    private String comment;
    @JsonIgnore
    @Column(name = "date_add")
    private String date;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_order")
    private Order order;

    public Invoice() {
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

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", idUser=" + idUser +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Invoice invoice = (Invoice) object;
        return id == invoice.id &&
                Objects.equals(number, invoice.number) &&
                Objects.equals(city, invoice.city) &&
                Objects.equals(idUser, invoice.idUser) &&
                Objects.equals(comment, invoice.comment) &&
                Objects.equals(date, invoice.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, city, idUser, comment, date);
    }
}
