package fit.se2.se02_project.repositories;

import fit.se2.se02_project.dto.CountProductDTO;
import fit.se2.se02_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT NEW fit.se2.se02_project.dto.CountProductDTO(CAST(SUM(od.quantity) AS INTEGER), p.productName, p.id) " +
            "FROM Order o " +
            "JOIN o.orderdetails od " +
            "JOIN od.product p " +
            "WHERE o.orderstatus.id <> 4 AND FUNCTION('YEAR', o.orderDate) = :year AND FUNCTION('MONTH', o.orderDate) = :month " +
            "GROUP BY p.id, p.productName " +
            "ORDER BY SUM(od.quantity) DESC")
    List<CountProductDTO> getProductsummary(@Param("year") int year, @Param("month") int month);
}
