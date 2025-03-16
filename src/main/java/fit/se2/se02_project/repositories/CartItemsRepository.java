package fit.se2.se02_project.repositories;

import fit.se2.se02_project.model.Cartitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository extends JpaRepository<Cartitem, Long> {
}
