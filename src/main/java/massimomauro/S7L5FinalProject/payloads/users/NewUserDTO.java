package massimomauro.S7L5FinalProject.payloads.users;

import jakarta.validation.constraints.*;
import massimomauro.S7L5FinalProject.enums.Role;

public record NewUserDTO(
        @NotEmpty(message = "Il nome è un campo obbligatorio!")
        @Size(min = 3, max=30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
        String name,
        @NotEmpty(message = "Il cognome è un campo obbligatorio!")
        String surname,
        @NotEmpty(message = "La password è un campo obbligatorio!")
        String password,

        @NotEmpty(message = "L'email è un campo obbligatorio!")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'email inserita non è valida")
        String email) {}
// Il record definisce una classe IMMUTABILE (cioè non ho a disposizione dei setter per cambiare i valori degli oggetti)
