package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Optional<Account> findByAccountId(Long accountId);

    @Query("""
        SELECT a FROM Account a
        WHERE a.role = 'ROLE_MEMBER'
          AND (
              LOWER(a.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
              OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
              OR LOWER(CONCAT(a.firstName, ' ', a.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))
          )
        """)
    List<Account> searchMembersByName(@Param("name") String name);
}
