package com.fleemer.webmvc.model;

import com.fleemer.webmvc.model.enums.AccountType;
import com.fleemer.webmvc.model.enums.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "accounts", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @Enumerated
    @Column(nullable = false)
    private AccountType type;

    @Enumerated
    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private String name;

    @Column(precision = 20, scale = 10, nullable = false)
    private BigDecimal sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Person person;
}
