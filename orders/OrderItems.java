package orders;

//import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderItems {

    public static void main(String[] args) {
        OrderItems orderItems = new OrderItems();
        Map<String,List<String>> ordersMap = orderItems.setOderMap();

        System.out.println("Final Output :: "+orderItems.orderItemSet(ordersMap));

    }

    public <T> Map<String,OrderItemData> orderItemSet(
            Map<T, ? extends Collection<String>> inputMap)
    {
        Map<String,OrderItemData> orderItemsMap = new HashMap<>();
        for (Map.Entry<T, ? extends Collection<String>> entry : inputMap.entrySet())
        {
            // resetting the map based on the ordered item map
            orderItemsMap  = computeOrderItems((String)entry.getKey(),(List<String>)entry.getValue(),orderItemsMap);
        }
        // Sorting the items based on usage count and fetch the top two order items
        orderItemsMap = orderItemsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((o1,o2)->o2.getUsageCount() - o1.getUsageCount())).limit(2)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, HashMap::new));

        return orderItemsMap;
    }

    private Map<String,OrderItemData> computeOrderItems(String orders,List<String> orderItems,
                                                        Map<String,OrderItemData> orderItemsMap) {

        // Order item map is empty go to if block
        if(null == orderItemsMap) {
            for(String orderItem : orderItems) {
                orderItemsMap.put(orderItem,new OrderItemData(1,Arrays.asList(orders)));
            }
        }else {
            // Order item map is not empty go to else block
            for(String orderItem :orderItems) {
                if(orderItemsMap.containsKey(orderItem)) {
                    OrderItemData updateOrderDat = orderItemsMap.get(orderItem);
                    updateOrderDat.setUsageCount(updateOrderDat.getUsageCount()+1);
                    updateOrderDat.setOrders(Stream.concat(new ArrayList<>(Arrays.asList(orders)).stream(), updateOrderDat.getOrders().stream()).parallel()
                            .collect(Collectors.toList()));

                    orderItemsMap.put(orderItem,updateOrderDat);
                } else {
                    orderItemsMap.put(orderItem,new OrderItemData(1,Arrays.asList(orders)));
                }
            }
        }

        return orderItemsMap;
    }

    public Map<String,List<String>> setOderMap() {
        // Setting the order map object
        Map<String,List<String>> orderMap = new HashMap<>();
        orderMap.put("order 1", Arrays.asList("item 1","item 2"));
        orderMap.put("order 2",Arrays.asList("item 3"));
        orderMap.put("order 3",Arrays.asList("item 2"));
        orderMap.put("order 4",Arrays.asList("item 1","item 2"));
        return orderMap;
    }

}
