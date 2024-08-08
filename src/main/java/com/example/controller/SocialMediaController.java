package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired MessageService messageService;
    @PostMapping("register")
    public void registerAccount(@RequestBody Account account){
        this.accountService.registerAccount(account);
    }
    @PostMapping("login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account user = this.accountService.loginAccount(account);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message newMessage = this.messageService.createMessage(message);
        return new ResponseEntity<>(newMessage, HttpStatus.OK);
    }
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = this.messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        Message message = messageService.getMessageById(messageId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer messageId){
        int messagesDeleted = messageService.deleteById(messageId);
        if (messagesDeleted > 0) return new ResponseEntity<>(String.valueOf(messagesDeleted), HttpStatus.OK);
        else return new ResponseEntity<>("",HttpStatus.OK);
    }
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<String> updateMessageById(@PathVariable Integer messageId, @RequestBody Message messageTextUpdate){
        // System.out.println(messageText);
        int messagesUpdated = messageService.updateMessageById(messageId, messageTextUpdate);
        return new ResponseEntity<>(String.valueOf(messagesUpdated), HttpStatus.OK);
    }
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccount(@PathVariable Integer accountId){
        List<Message> messages = messageService.getAllMessagesByAccount(accountId);
        return new ResponseEntity<>(messages, HttpStatus.OK);

    }

}
