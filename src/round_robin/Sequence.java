package round_robin;

/**
 * @author: panghu
 * @Description: 模拟调用编号获取工具
 * @Date: Created in 13:50 2020/4/25
 * @Modified By:
 */
public class Sequence {

    public static Integer num = 0;

    public static Integer getAndIncrement() {
        return ++num;
    }

}
