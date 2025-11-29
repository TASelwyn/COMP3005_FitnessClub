package tech.selwyn.carleton.comp3005.fitnessclub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

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
    @Column(name = "account_id")


    private Long accountId;

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

    // List of Goals for this account
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals =  new ArrayList<>();

    public Map<String, Object> toSummary() {
        return Map.of(
                "accountId", accountId,
                "fullName", firstName + " " + lastName,
                "email", email//,
                //"goal", currentGoal != null ? currentGoal.toSummary() : null,
                //"lastMetric", lastMetric != null ? lastMetric.toSummary() : null
        );
    }
}
