package com.infobip.kulendayz.infobip.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "message",
uniqueConstraints = {
        @UniqueConstraint(name = "uq_message_email", columnNames = "email")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue(generator = "message_sequence_generator")
    @SequenceGenerator(name = "message_sequence_generator", sequenceName = "message_seq", allocationSize = 10)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "subject", nullable = false, updatable = false)
    private String subject;

    @Column(name = "content", nullable = false, updatable = false)
    private String content;

    @Column(name = "email", nullable = false, updatable = false)
    private String email;

    @Column(name = "sent_at", nullable = false, updatable = false)
    private OffsetDateTime sentAt;

    @Column(name = "bulk_id", nullable = false, updatable = false)
    private String bulkId;

    @Column(name = "message_id", nullable = false, updatable = false)
    private String messageId;

}
