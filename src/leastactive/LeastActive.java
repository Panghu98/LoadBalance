package leastactive;

import random.ServerIps;

import java.util.*;

/**
 * @author: panghu
 * @Description:
 * @Date: Created in 19:55 2020/4/25
 * @Modified By:
 */
public class LeastActive {

    private static String getServer() {
        // 找出当前活跃数最小的服务器
        Optional<Integer> minValue = ServerIps.ACTIVITY_LIST.values().stream().min(Comparator.naturalOrder());
        if (minValue.isPresent()) {
            List<String> minActivityIps = new ArrayList<>();
            ServerIps.ACTIVITY_LIST.forEach((ip, activity) -> {
                if (activity.equals(minValue.get())) {
                    minActivityIps.add(ip);
                }
            });
            // 最当有多个服务器的最小活跃数相同的时候，则根据权重来选，权重大的优先
            if (minActivityIps.size() > 1) {
                Map<String, Integer> weightList = new LinkedHashMap<>();
                // 获取
                ServerIps.WEIGHT_LIST.forEach((ip, weight) -> {
                    if (minActivityIps.contains(ip)) {
                        weightList.put(ip, ServerIps.WEIGHT_LIST.get(ip));
                    }
                });
                int totalWeight = 0;
                // 如果所有的权重都相同的话，随机IP即可
                boolean sameWeight = true;
                Object[] weights = weightList.values().toArray();
                for (int i = 0; i < weights.length; i++) {
                    Integer weight = (Integer) weights[i];
                    totalWeight += weight;
                    if (sameWeight && i > 0 && !weight.equals(weights[i - 1])) {
                        sameWeight = false;
                    }
                }

                java.util.Random random = new java.util.Random();
                int randomPos = random.nextInt(totalWeight);

                // 非相同权重的情况下，
                if (!sameWeight) {
                    for (String ip : weightList.keySet()) {
                        Integer value = weightList.get(ip);
                        if (randomPos < value) {
                            return ip;
                        }
                        randomPos = randomPos - value;
                    }
                }

                return (String) weightList.keySet().toArray()[new java.util.Random().nextInt(weightList.size())];

            } else {
                return minActivityIps.get(0);
            }
        } else {
            return (String) ServerIps.WEIGHT_LIST.keySet().toArray()[new java.util.Random().nextInt(ServerIps.WEIGHT_LIST.size())];
        }
    }

    public static void main(String[] args) {
        // 连续调用50次
        for (int i=0; i<50; i++)
        {
            // 因为没有对活跃数进行操作，所以这里的结果是固定的
            System.out.println(getServer());
        }
    }

}
