package ru.relex.relexsummerpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.relex.relexsummerpractice.models.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findByHeader(String header);
    @Query(value = "select * from messages order by id desc", nativeQuery = true)
    List<Message> selectAllOrderByIdDesc();
}
