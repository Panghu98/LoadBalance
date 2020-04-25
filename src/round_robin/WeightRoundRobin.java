package round_robin;

import random.ServerIps;

/**
 * @author: panghu
 * @Description: 权重轮询版本1
 * @Date: Created in 13:55 2020/4/25
 * @Modified By:
 */
public class WeightRoundRobin {

    private static Integer position = 0;

    public static String getServer() {
        int totalWeight = 0;

        // 如果所有的权重都相同，随机ip
        boolean sameWeight = true;
        Object[] weights = ServerIps.WEIGHT_LIST.values().toArray();
        for (int i = 0; i < weights.length; i++) {
            Integer weight = (Integer) weights[i];
            totalWeight += weight;
            if (sameWeight && i > 0 && !weight.equals(weights[i - 1])) {
                sameWeight = false;
            }
        }

        Integer sequenceNum = Sequence.getAndIncrement();
        Integer offset = sequenceNum % totalWeight;

        // 计算下标
        offset = offset == 0 ?  totalWeight : offset;

        if (! sameWeight) {
            for (String ip:ServerIps.WEIGHT_LIST.keySet()
                 ) {
                Integer weight = ServerIps.WEIGHT_LIST.get(ip);
                if (offset <= weight) {
                    return ip;
                }

                offset = offset - weight;

            }
        }

        String ip = null;
        synchronized (position) {
            // 到达结尾，从头开始轮询
            if (position >= ServerIps.LIST.size()) {
                position = 0;
            }

            ip = ServerIps.LIST.get(position);
            position++;
        }


        return ip;
    }

    public static void main(String[] args) {
        // 连续调用50次
        for (int i=0; i<50; i++)
        {
            System.out.println(getServer());
        }
    }

}
