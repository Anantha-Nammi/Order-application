package orders;

import java.util.List;

public class OrderItemData {

    private Integer usageCount;
    private List<String> orders;

    public OrderItemData() {}

    public OrderItemData(Integer usageCount,List<String> orders) {
        this.usageCount = usageCount;
        this.orders = orders;
    }
    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    @Override
    public String toString() {
        return "OrderItemData{" +
                "usageCount=" + usageCount +
                ", orders=" + orders +
                '}';
    }
}
