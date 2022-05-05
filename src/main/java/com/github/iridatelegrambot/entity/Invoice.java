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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private UserTelegram user;
    @JsonIgnore
    @Column
    private String comment;
    @JsonIgnore
    @Column(name = "date_add")
    private String date;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
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

    public UserTelegram getUser() {
        return user;
    }

    public void setUser(UserTelegram user) {
        if(user == null){
            return;
        }
        user.addInvoice(this);
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if(order!=null){
            order.addInvoice(this);
        }
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
                ", user=" + user +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                ", order=" + order +
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
                Objects.equals(comment, invoice.comment) &&
                Objects.equals(date, invoice.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, city, comment, date);
    }

    public String toStringForUser(){
        String text = "Накладная на перемещение:\n" +
                "Номер = " + number + "\n" +
                "Город = " + city;

        if(user==null) {
            text += "\nПользователь добавил = Не найдено.\n" +
                    "Время добавления = " + date;
        } else {
            text += "\nПользователь добавил = " + user.getFirstName() + ", " + user.getUserName() + "\n" +
                    "Время добавления = " + date;
        }

        if(order == null){
            text += "\nЗаказ для которого создавалась накладная не найден.";
        } else {
            text += "\nЗаказ для которого создавлась накладная: " + order.getNumber();
        }
        return text;
    }
}
