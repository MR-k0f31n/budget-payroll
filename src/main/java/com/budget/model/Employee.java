package com.budget.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author MR.k0F31n
 */

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NotNull
    private Float rate;
    private LocalDate birthDay;
    @NotNull
    private LocalDate dateEmployment;
    @NotNull
    private Boolean isFired;
}
