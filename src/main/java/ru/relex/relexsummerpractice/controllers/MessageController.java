package ru.relex.relexsummerpractice.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.relex.relexsummerpractice.models.Message;
import ru.relex.relexsummerpractice.services.MessageService;

@Controller
@RequestMapping("message")
public class MessageController {

    MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping()
    public String showAll(@ModelAttribute("message") Message message, Model model) {
        model.addAttribute("messages", messageService.findAll());
        return "all";
    }

    @GetMapping("/{header}")
    public String showOne(@PathVariable("header") String header, Model model) {
        model.addAttribute("message", messageService.findByHeader(header));
        return "show";
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        messageService.export(response);
    }

    @GetMapping("/create")
    public String createPage(@ModelAttribute("newMessage") Message message) {
        return "create";
    }


    @PostMapping()
    public String create(@ModelAttribute("newMessage") @Valid Message message, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "redirect:/message/create";
        }
        messageService.createMessage(message);
        return "redirect:/message";
    }

    @GetMapping("/delete")
    public String delete() {
        messageService.deleteAll();
        return "redirect:/message";
    }
}
