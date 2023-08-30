package com.infobip.kulendayz.infobip.repository;

import com.infobip.kulendayz.infobip.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    Optional<MessageEntity> findByEmail(final String email);

}
