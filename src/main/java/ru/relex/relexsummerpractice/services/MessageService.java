package ru.relex.relexsummerpractice.services;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.relex.relexsummerpractice.models.Message;
import ru.relex.relexsummerpractice.repositories.MessageRepository;

import java.io.IOException;
import java.util.List;

@Service
@Log4j2
public class MessageService {
    private final MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> findAll() {
        return messageRepository.selectAllOrderByIdDesc();
    }

    public Message findByHeader(String header) {
        return messageRepository.findByHeader(header);
    }

    public void deleteAll() {
        messageRepository.deleteAll();
    }

    public void export(HttpServletResponse response) {
        log.info("Export method started");
        long start = System.currentTimeMillis();

        String fileName = "messages.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        response.setCharacterEncoding("WINDOWS-1251");

        try {
            StatefulBeanToCsv<Message> writer = new StatefulBeanToCsvBuilder<Message>(response.getWriter())
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withOrderedResults(false).build();

            writer.write(messageRepository.findAll());
        } catch (IOException ioException) {
            log.warn(ioException.getMessage());
        } catch (CsvRequiredFieldEmptyException csvRequiredFieldEmptyException) {
            log.warn(csvRequiredFieldEmptyException.getMessage());
        } catch (CsvDataTypeMismatchException csvDataTypeMismatchException) {
            log.warn(csvDataTypeMismatchException.getMessage());
        }

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        log.info(String.format("Export method completed in %d ms", elapsed));
    }
}
