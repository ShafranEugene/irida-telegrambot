package com.github.iridatelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private UserTelegram user;

    @JsonIgnore
    @Column(name = "status")
    private boolean statusActive;

    @JsonIgnore
    @Column(name = "date_created")
    private String date;

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<Invoice> invoiceList = new ArrayList<>();


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

    public UserTelegram getUser() {
        return user;
    }

    public void setUser(UserTelegram user) {
        if(user == null){
            return;
        }
        this.user = user;
        user.addOrder(this);
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

    public void addInvoice(Invoice invoice){
        if(invoiceList.contains(invoice)){
            return;
        }
        invoiceList.add(invoice);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Order order = (Order) object;
        return id == order.id &&
                statusActive == order.statusActive &&
                Objects.equals(number, order.number) &&
                Objects.equals(city, order.city) &&
                Objects.equals(user, order.user) &&
                Objects.equals(date, order.date) &&
                Objects.equals(invoiceList, order.invoiceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, city, user, statusActive, date, invoiceList);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", user=" + user +
                ", statusActive=" + statusActive +
                ", date='" + date + '\'' +
                ", invoiceList=" + invoiceList +
                '}';
    }

    public String toStringForUsers(){
        String text = "Заказ на перемещение:\n" +
                "Номер = " + number + "\n" +
                "Город = " + city;

        if(user==null) {
            text += "\nПользователь добавил = Пользователь не известен.\n" +
                    "Время добавления = " + date + "\n" +
                    "Накладные на перемещение, подвязынные к заказу:";
        } else {
            text += "\nПользователь добавил = " + user.getFirstName() + ", " + user.getUserName() + "\n" +
                    "Время добавления = " + date + "\n" +
                    "Накладные на перемещение, подвязынные к заказу:";
        }

        if(invoiceList.size() == 0){
            text += "Накладных нет.";
        } else {
            for (Invoice invoice : invoiceList){
                text += "\n\t- " + invoice.getNumber() + ", из " + invoice.getCity();
            }
        }
        return text;
    }
}
