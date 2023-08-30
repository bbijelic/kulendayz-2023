package com.infobip.kulendayz.auth.entity;

import com.infobip.kulendayz.auth.integration.dto.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@Table(name = "credentials", uniqueConstraints = {
        @UniqueConstraint(name = "credentials_email_uq", columnNames = "email")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsEntity {

    @Id
    @GeneratedValue(generator = "credentials_sequence_generator")
    @SequenceGenerator(name = "credentials_sequence_generator", sequenceName = "credentials_seq", allocationSize = 10)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "uid", nullable = false, updatable = false)
    private UUID uid;

    @Column(name = "email", nullable = false, length = 255, updatable = true)
    private String email;

    @Column(name = "password", nullable = false, length = 255, updatable = true)
    private String password;

    @Column(name = "registered_at", columnDefinition = "timestamp with time zone", nullable = false, updatable = false)
    private OffsetDateTime registeredAt;

    @ElementCollection(targetClass = RoleEnum.class)
    @JoinTable(name = "roles", joinColumns = @JoinColumn(name = "credentials"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<RoleEnum> roles;

    @Transient
    private String firstName;

    @Transient
    private String lastName;
}
