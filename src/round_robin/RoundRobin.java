package round_robin;

import random.ServerIps;

/**
 * @author: panghu
 * @Description: 轮询算法的简单实现
 * @Date: Created in 12:16 2020/4/25
 * @Modified By:
 */
public class RoundRobin {

    /**
     * 当前循环的位置
     */
    private static Integer pos = 0;

    public static String getServer() {
        String ip = null;
        // pos 同步
        synchronized (pos) {
            // List循环完毕，从头开始
            if (pos >= ServerIps.LIST.size()) {
                pos = 0;
            }

            ip = ServerIps.LIST.get(pos);
            pos++;
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
