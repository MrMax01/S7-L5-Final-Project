package massimomauro.S7L5FinalProject.payloads.events;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventDTO (
        @NotEmpty(message = "Il titolo è un campo obbligatorio!")
        @Size(min = 3, max=30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
        String title,
        @NotEmpty(message = "Il luogo è un campo obbligatorio!")
        String location,
        @NotEmpty(message = "La descrizione è un campo obbligatorio!")
        @Size(min = 3, max=30, message="descrizione troppo corta o troppo lunga")
        String description,
        @NotEmpty(message = "La data è un campo obbligatorio!")
        LocalDate eventStartDate,
        @NotEmpty(message = "numero totale biglietti è un campo obbligatorio!")
        int totalTickets

        ) {}


