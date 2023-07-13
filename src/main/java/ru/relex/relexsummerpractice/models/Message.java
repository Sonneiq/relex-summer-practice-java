package ru.relex.relexsummerpractice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "header")
    @NotEmpty(message = "Заголовок не должен быть пустым")
    private String header;
    @Column(name = "message")
    @NotEmpty(message = "Текст сообщения не должен быть пустым")
    private String msg;

    public Message() {

    }

    public Message(int id, String msg, String header) {
        this.id = id;
        this.msg = msg;
        this.header = header;
    }

    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public String getHeader() {
        return header;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
