package ru.relex.relexsummerpractice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "header")
    @NotEmpty(message = "Заголовок не должен быть пустым")
    private String header;
    @Column(name = "message")
    @NotEmpty(message = "Текст сообщения не должен быть пустым")
    private String msg;
}
