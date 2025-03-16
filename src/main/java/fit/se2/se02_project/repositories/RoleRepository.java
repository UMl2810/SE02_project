package fit.se2.se02_project.repositories;

import fit.se2.se02_project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
