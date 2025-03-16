package fit.se2.se02_project.repositories;

import fit.se2.se02_project.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
