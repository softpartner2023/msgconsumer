package com.cacib.msgconsumer.repository;

import com.cacib.msgconsumer.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
