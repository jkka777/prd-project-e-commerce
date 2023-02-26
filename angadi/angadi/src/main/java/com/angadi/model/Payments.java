package com.angadi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer paymentsId;

    private PaymentType paymentType;

    private LocalDate paymentDate;

    private PaymentStatus paymentStatus;

    @JsonIgnore
    @OneToOne(mappedBy = "payments")
    private Orders orders;

}
