package random;

import java.util.List;

/**
 * @author: panghu
 * @Description:
 * @Date: Created in 20:34 2020/4/24
 * @Modified By:
 */
public class Random {

    public static String getServer() {

        // 生成随机数作为List的下标值
        java.util.Random random = new java.util.Random();
        int randomPosition = random.nextInt(ServerIps.LIST.size());

        // 返回随机的IP
        return ServerIps.LIST.get(randomPosition);

    }

    public static void main(String[] args) {
        // 连续调用10次
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
        }
    }

}
