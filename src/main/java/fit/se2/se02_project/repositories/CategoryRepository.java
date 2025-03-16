package fit.se2.se02_project.repositories;

import fit.se2.se02_project.dto.CountCategoryDTO;
import fit.se2.se02_project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT NEW fit.se2.se02_project.dto.CountCategoryDTO(c.categoryName, c.id, CAST(SUM(od.quantity) AS INTEGER)) " +
            "FROM Order o " +
            "JOIN o.orderdetails od " +
            "JOIN od.product p " +
            "JOIN p.category c " +
            "WHERE FUNCTION('YEAR', o.orderDate) = :year AND FUNCTION('MONTH', o.orderDate) = :month AND o.orderstatus.id <> 4 " +
            "GROUP BY c.id, c.categoryName " +
            "ORDER BY SUM(od.quantity) DESC")
    List<CountCategoryDTO> getSummary(@Param("year") int year, @Param("month") int month);

}
