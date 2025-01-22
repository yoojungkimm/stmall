package stmall.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import stmall.OrderApplication;
import stmall.domain.OrderModified;
import stmall.domain.OrderPlaced;

@Entity
@Table(name = "OrderMgmt_table")
@Data
//<<< DDD / Aggregate Root
public class OrderMgmt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private String productName;

    private Long productId;

    private Integer qty;

    private String status;

    @PostPersist
    public void onPostPersist() {
        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        OrderModified orderModified = new OrderModified(this);
        orderModified.publishAfterCommit();
    }

    public static OrderMgmtRepository repository() {
        OrderMgmtRepository orderMgmtRepository = OrderApplication.applicationContext.getBean(
            OrderMgmtRepository.class
        );
        return orderMgmtRepository;
    }
}
//>>> DDD / Aggregate Root
