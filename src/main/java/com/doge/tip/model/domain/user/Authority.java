package com.doge.tip.model.domain.user;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "authorities", schema = "public")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "pg-uuid"
    )
    @GenericGenerator(
            name = "pg-uuid",
            strategy = "uuid2"
    )
    @Column(name = "authority_id", unique = true, nullable = false, updatable = false, insertable = false)
    private UUID id;

    @NotNull(message = "The authority name must NOT be left empty (null)! Please, input a non-null value!")
    @NotBlank(message = "The authority name must NOT be left blank (without an alpha-numeric sign)!")
    @Size(max = 100, message = "The highest amount of characters in authority name allowed is 100 (a hundred)!")
    @Column(name = "authority_name", nullable = false, length = 100, unique = true)
    private String name;

    @Size(max = 255, message = "The highest amount of characters in authority description allowed is 255!")
    @Column(name = "authority_description", length = 100)
    private String description;

}
