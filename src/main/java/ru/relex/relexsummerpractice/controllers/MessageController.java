package ru.relex.relexsummerpractice.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
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
    public ModelAndView showAll(@ModelAttribute("message") Message message, Model model) {
        model.addAttribute("messages", messageService.findAll());
        ModelAndView all = new ModelAndView("all");
        return all;
    }

    @GetMapping("/{header}")
    public ModelAndView showOne(@PathVariable("header") String header, Model model) {
        model.addAttribute("message", messageService.findByHeader(header));
        ModelAndView show = new ModelAndView("show");
        return show;
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        messageService.export(response);
    }

    @GetMapping("/create")
    public ModelAndView createPage(@ModelAttribute("newMessage") Message message) {
        ModelAndView create = new ModelAndView("create");
        return create;
    }


    @PostMapping()
    public RedirectView create(@ModelAttribute("newMessage") @Valid Message message, BindingResult bindingResult) {
        RedirectView redirect = new RedirectView();
        if(bindingResult.hasErrors()) {
            redirect.setUrl("message/create");
            return redirect;
        }
        messageService.createMessage(message);
        redirect.setUrl("message");
        return redirect;
    }

    @GetMapping("/delete")
    public RedirectView delete() {
        messageService.deleteAll();
        RedirectView redirect = new RedirectView("/message");
        return redirect;
    }
}
