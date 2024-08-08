package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.CustomException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText().equals("") || message.getMessageText().length() > 255
                || !accountRepository.existsById(message.getPostedBy()))
            throw new CustomException("Message could not be created", HttpStatus.BAD_REQUEST);
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return this.messageRepository.findAll();
    }

    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public Integer deleteById(int messageId) {
        return messageRepository.deleteById(messageId);
    }

    @Transactional
    public int updateMessageById(Integer messageId, Message messageUpdate) {
        if (!messageRepository.existsById(messageId))
            {throw new CustomException("Invalid Message ID", HttpStatus.BAD_REQUEST);}
    
        if (messageUpdate.getMessageText().length() > 255) {
            throw new CustomException("Invalid Message Text", HttpStatus.BAD_REQUEST);
        }
        if (messageUpdate.getMessageText().equals("")) throw new CustomException("Invalid Message Text", HttpStatus.BAD_REQUEST);


        return messageRepository.updateMessageById(messageId, messageUpdate.getMessageText());
    }

    public List<Message> getAllMessagesByAccount(Integer accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }
}
