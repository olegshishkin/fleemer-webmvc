package com.fleemer.webmvc.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fleemer.webmvc.model.enums.AccountType;
import com.fleemer.webmvc.model.enums.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Enumerated
    @Column(nullable = false)
    private AccountType type;

    @NotNull
    @Enumerated
    @Column(nullable = false)
    private Currency currency;

    @NotNull
    @Digits(integer = 20, fraction = 10)
    @Column(precision = 20, scale = 10, nullable = false)
    private BigDecimal balance;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Person person;

    @JsonGetter("type")
    public String toJson() {
        String text = type.name().toLowerCase().replace('_', ' ');
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
