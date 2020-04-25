package hash;

import random.ServerIps;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author: panghu
 * @Description: 使用红黑树来模拟hash环
 * @Date: Created in 19:12 2020/4/25
 * @Modified By:
 *
 */
public class ConsistentHash {

    private static final SortedMap<Integer, String> virtualNodes = new TreeMap<>();
    /**
     * 每个节点一共有10个虚拟节点
     */
    private static final int VIRTUAL_NODES = 160;
    static {
        //对每个真实节点添加虚拟节点，虚拟节点会根据哈希算法进行散列
        for (String ip : ServerIps.LIST) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                int hash = getHash(ip+"VN"+i);
                virtualNodes.put(hash, ip);
            }
        }
    }

    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来是负数的话，取绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }

        return hash;

    }

    public static void main(String[] args) {
        // 连续调用10次。随机10个client
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer("client" + i));
        }
    }

    /**
     * 负载均衡获取客户端
     * @param client 客户端名称前缀
     * @return
     */
    private static String getServer(String client) {
        int hash = getHash(client);
        //得到大于该Hash值的排序好的map
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);

        // 大于该Hash值的第一个元素的位置
        Integer nodeIndex = subMap.firstKey();

        //如果不存在大于该Hash值的元素，则返回根节点
        if (nodeIndex == null) {
            nodeIndex = virtualNodes.firstKey();
        }

        // 返回对应的虚拟节点名称
        return subMap.get(nodeIndex);
    }



}
