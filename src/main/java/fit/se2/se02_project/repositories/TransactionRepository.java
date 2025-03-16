package fit.se2.se02_project.repositories;

import fit.se2.se02_project.dto.ProfitDTO;
import fit.se2.se02_project.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT NEW fit.se2.se02_project.dto.ProfitDTO(FUNCTION('MONTHNAME', o.orderDate), COALESCE(SUM(t.total), 0)) " +
            "FROM Order o " +
            "LEFT JOIN Transaction t ON o.id = t.order.id " +
            "GROUP BY FUNCTION('MONTH', o.orderDate) " +
            "ORDER BY FUNCTION('MONTH', o.orderDate)")
    List<ProfitDTO> getSummary();

}
