package fit.se2.se02_project.repositories;

import fit.se2.se02_project.model.Productstatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStatusRepository extends JpaRepository<Productstatus, Long> {
}
