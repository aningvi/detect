package zstu.utils.random;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Aning
 * Date: 14-9-2
 * Time: 下午4:22
 */
public class IDInc {
    public static AtomicInteger counterInteger = new AtomicInteger(0);

    public static String incr() {
        return String.valueOf(counterInteger.getAndIncrement());
    }

    public static void main(String[] args) {
        System.out.println(incr());
        System.out.println(incr());
        System.out.println(incr());
    }
}
