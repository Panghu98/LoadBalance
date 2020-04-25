package round_robin;

import random.ServerIps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: panghu
 * @Description:
 * @Date: Created in 15:33 2020/4/25
 * @Modified By:
 */
public class WeightRoundRobinV2 {

    private static Map<String, Weight> weightMap = new HashMap<>();
    public static String getServer() {


        // java8
        int totalWeight = ServerIps.WEIGHT_LIST.values().stream().reduce(0, Integer::sum);


        // 初始化weightMap,初始化时将currentWeight赋值为weight
        if (weightMap.isEmpty()) {
            ServerIps.WEIGHT_LIST.forEach((key, value) -> {
                weightMap.put(key, new Weight(key, value, value));
            });
        }

        // 找出currentWeight最大值
        Weight maxCurrentWeight = null;
        for (Weight weight : weightMap.values()) {
            if (maxCurrentWeight == null || weight.getCurrentWeight() > maxCurrentWeight.getCurrentWeight()) {
                maxCurrentWeight = weight;
            }
        }

        // 将maxCurrentWeight减去权重总和
        assert maxCurrentWeight != null;
        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight() - totalWeight);

        // 所有的ip的maxCurrentWeight统一加上原始权重
        for (Weight weight : weightMap.values()) {
            weight.setCurrentWeight(weight.getCurrentWeight() + weight.getWeight());
        }

        //返回maxCurrentWeight所对应的ip
        return maxCurrentWeight.getIp();

    }

    public static void main(String[] args) {
        // 简化数据
        ServerIps.WEIGHT_LIST.clear();
        ServerIps.WEIGHT_LIST.put("A", 5);
        ServerIps.WEIGHT_LIST.put("B", 1);
        ServerIps.WEIGHT_LIST.put("C", 1);
        // 连续调用50次
        for (int i=0; i<50; i++)
        {
            System.out.println(getServer());
        }
    }

}
