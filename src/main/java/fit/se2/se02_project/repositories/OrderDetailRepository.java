package fit.se2.se02_project.repositories;

import fit.se2.se02_project.model.Orderdetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<Orderdetail, Long> {
}
