package random;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: panghu
 * @Description: 权重随机算法的实现
 * @Date: Created in 10:58 2020/4/25
 * @Modified By:
 */
public class WeightRandom {
    public static String getServer() {
        // 初始化一个List来存储带有权重的服务器，每一个List的添加数量与权重成正比
        List<String> ips = new ArrayList<>();
        for (String ip : ServerIps.WEIGHT_LIST.keySet()) {
            Integer weight = ServerIps.WEIGHT_LIST.get(ip);

            // 根据服务器的权重来添加服务器
            for (int i=0; i<weight; i++) {
                ips.add(ip);
            }
        }
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(ips.size());
            return ips.get(randomPos);
    }

    public static void main(String[] args) {
        for (int i=0; i<50; i++)
        {
            System.out.println(getServer());
        }
    }
}
