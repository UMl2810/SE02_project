package fit.se2.se02_project.repositories;

import fit.se2.se02_project.model.Orderstatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<Orderstatus, Long> {
}
