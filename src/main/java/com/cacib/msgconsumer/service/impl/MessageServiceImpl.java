package com.cacib.msgconsumer.service.impl;

import com.cacib.msgconsumer.dto.MessageRequestDTO;
import com.cacib.msgconsumer.dto.MessageResponseDTO;
import com.cacib.msgconsumer.dto.PartnerResponseDTO;
import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.exception.ResourceNotFoundException;
import com.cacib.msgconsumer.mapper.MessageMapper;
import com.cacib.msgconsumer.repository.MessageRepository;
import com.cacib.msgconsumer.repository.PartnerRepository;
import com.cacib.msgconsumer.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final PartnerRepository partnerRepository;
    private final MessageMapper messageMapper;

    @Override
    public List<MessageResponseDTO> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toMessageResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponseDTO getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id : " + id));
        return messageMapper.toMessageResponseDTO(message);
    }

    @Override
    public MessageResponseDTO saveMessage(MessageRequestDTO messageRequestDTO) {
        Partner partner = partnerRepository.findByAlias(messageRequestDTO.getPartnerAlias())
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

        Message message = messageMapper.toEntity(messageRequestDTO);
        message.setReceptionDate(LocalDateTime.now());
        message.setPartner(partner);

        Message messageSaved = messageRepository.save(message);

        return messageMapper.toMessageResponseDTO(messageSaved);
    }
}
