package fit.se2.se02_project.dto;

import java.math.BigDecimal;

public class ProfitDTO {
    private String month;
    private BigDecimal total;

    public ProfitDTO() {
    }

    public ProfitDTO(String month, BigDecimal total) {
        this.month = month;
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
