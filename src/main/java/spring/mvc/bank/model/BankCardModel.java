package spring.mvc.bank.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class BankCardModel {
    private long id;
    @NotNull(message = "Must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate validity;
    @NotBlank(message = "Card Number must not be blank")
    @NotNull(message = "Card Type must not be null")
    private String cardType;
    @NotBlank(message = "Card Number must not be blank")
    @NotNull(message = "Card Number must not be null")
    private String cardNumber;
    @NotNull(message = "Balance must not be null")
    @Min(value = 0, message = "Balance must be positive")
    private Double balance;
}
