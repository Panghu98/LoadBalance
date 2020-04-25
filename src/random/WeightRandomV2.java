package random;

/**
 * @author: panghu
 * @Description:
 * @Date: Created in 11:17 2020/4/25
 * @Modified By:
 */
public class WeightRandomV2 {

    public static String getServer() {
        int totalWeight = 0;
        // 如果所有的权重都相同，那么随机一个IP就好了
        boolean sameWeight = true;

        Object[] weights = ServerIps.WEIGHT_LIST.values().toArray();

        // 计算权重的总和并且判断是不是相同的权重值
        for (int i = 0; i < weights.length; i++) {
            Integer weight = (Integer)weights[i];
            totalWeight += weight;
            if (sameWeight && i > 0 && !weight.equals(weights[i -1])) {
                sameWeight = false;
            }
        }

        java.util.Random random= new java.util.Random();
        int randomPosition = random.nextInt(totalWeight);

        if (!sameWeight) {
            for(String ip : ServerIps.WEIGHT_LIST.keySet()) {
                // 获取权重
                Integer value = ServerIps.WEIGHT_LIST.get(ip);

                // 下面的这两步操作用于计算所在的区间，从小的方向开始减
                if (randomPosition < value) {
                    return ip;
                }

                randomPosition = randomPosition - value;
            }

        }

        return (String)ServerIps.WEIGHT_LIST.keySet().toArray()
                [new java.util.Random().nextInt(ServerIps.WEIGHT_LIST.size())];

    }

    public static void main(String[] args) {
        // 连续调用50次
        for (int i=0; i<50; i++)
        {
            System.out.println(getServer());
        }
    }

}
