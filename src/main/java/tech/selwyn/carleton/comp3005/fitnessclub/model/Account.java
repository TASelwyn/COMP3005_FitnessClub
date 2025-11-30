package tech.selwyn.carleton.comp3005.fitnessclub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String role;

    public Map<String, Object> toSummary() {
        return Map.of(
                "accountId", id,
                "fullName", firstName + " " + lastName,
                "email", email
        );
    }
}
