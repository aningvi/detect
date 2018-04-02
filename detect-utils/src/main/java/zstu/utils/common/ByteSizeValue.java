package zstu.utils.common;

/**
 * .
 * User: Liupeizhi
 * Date: 16-3-18
 * Time: 下午2:32
 */
import java.io.Serializable;
import java.util.Locale;

public class ByteSizeValue implements Serializable {


    public static ByteSizeValue INSTANCE = new ByteSizeValue();

    public static ByteSizeValue getInstance(){
        return INSTANCE;
    }

    private  ByteSizeValue() {
    }


    public int bytesAsInt(ByteSizeUnit sizeUnit,long size) throws Exception {
        long bytes = bytes(sizeUnit,size);
        if (bytes > Integer.MAX_VALUE) {
            throw new Exception("size [" + toString() + "] is bigger than max int");
        }
        return (int) bytes;
    }

    public long bytes(ByteSizeUnit sizeUnit,long size) {
        return sizeUnit.toBytes(size);
    }

    public long getBytes(ByteSizeUnit sizeUnit,long size) {
        return bytes(sizeUnit,size);
    }

    public long kb(ByteSizeUnit sizeUnit,long size) {
        return sizeUnit.toKB(size);
    }

    public long getKb(ByteSizeUnit sizeUnit,long size) {
        return kb(sizeUnit,size);
    }

    public long mb(ByteSizeUnit sizeUnit,long size) {
        return sizeUnit.toMB(size);
    }

    public long getMb(ByteSizeUnit sizeUnit,long size) {
        return mb(sizeUnit,size);
    }

    public long gb(ByteSizeUnit sizeUnit,long size) {
        return sizeUnit.toGB(size);
    }

    public long getGb(ByteSizeUnit sizeUnit,long size) {
        return gb(sizeUnit,size);
    }

    public long tb(ByteSizeUnit sizeUnit,long size) {
        return sizeUnit.toTB(size);
    }

    public long getTb(ByteSizeUnit sizeUnit,long size) {
        return tb(sizeUnit,size);
    }

    public long pb(ByteSizeUnit sizeUnit,long size) {
        return sizeUnit.toPB(size);
    }

    public long getPb(ByteSizeUnit sizeUnit,long size) {
        return pb(sizeUnit,size);
    }

    public double kbFrac(ByteSizeUnit sizeUnit,long size) {
        return ((double) bytes(sizeUnit,size)) / ByteSizeUnit.C1;
    }

    public double getKbFrac(ByteSizeUnit sizeUnit,long size) {
        return kbFrac(sizeUnit,size);
    }

    public double mbFrac(ByteSizeUnit sizeUnit,long size) {
        return ((double) bytes(sizeUnit,size)) / ByteSizeUnit.C2;
    }

    public double getMbFrac(ByteSizeUnit sizeUnit,long size) {
        return mbFrac(sizeUnit,size);
    }

    public double gbFrac(ByteSizeUnit sizeUnit,long size) {
        return ((double) bytes(sizeUnit,size)) / ByteSizeUnit.C3;
    }

    public double getGbFrac(ByteSizeUnit sizeUnit,long size) {
        return gbFrac(sizeUnit,size);
    }

    public double tbFrac(ByteSizeUnit sizeUnit,long size) {
        return ((double) bytes(sizeUnit,size)) / ByteSizeUnit.C4;
    }

    public double getTbFrac(ByteSizeUnit sizeUnit,long size) {
        return tbFrac(sizeUnit,size);
    }

    public double pbFrac(ByteSizeUnit sizeUnit,long size) {
        return ((double) bytes(sizeUnit,size)) / ByteSizeUnit.C5;
    }

    public double getPbFrac(ByteSizeUnit sizeUnit,long size) {
        return pbFrac(sizeUnit,size);
    }
    /**
     * Format the double value with a single decimal points, trimming trailing '.0'.
     */
    public static String format1Decimals(double value, String suffix) {
        String p = String.valueOf(value);
        int ix = p.indexOf('.') + 1;
        int ex = p.indexOf('E');
        char fraction = p.charAt(ix);
        if (fraction == '0') {
            if (ex != -1) {
                return p.substring(0, ix - 1) + p.substring(ex) + suffix;
            } else {
                return p.substring(0, ix - 1) + suffix;
            }
        } else {
            if (ex != -1) {
                return p.substring(0, ix) + fraction + p.substring(ex) + suffix;
            } else {
                return p.substring(0, ix) + fraction + suffix;
            }
        }
    }


    public String toString(ByteSizeUnit sizeUnit, long size) {
        long bytes = bytes(sizeUnit,size);
        double value = bytes;
        String suffix = "b";
        if (bytes >= ByteSizeUnit.C5) {
            value = pbFrac(sizeUnit,size);
            suffix = "pb";
        } else if (bytes >= ByteSizeUnit.C4) {
            value = tbFrac(sizeUnit,size);
            suffix = "tb";
        } else if (bytes >= ByteSizeUnit.C3) {
            value = gbFrac(sizeUnit,size);
            suffix = "gb";
        } else if (bytes >= ByteSizeUnit.C2) {
            value = mbFrac(sizeUnit,size);
            suffix = "mb";
        } else if (bytes >= ByteSizeUnit.C1) {
            value = kbFrac(sizeUnit,size);
            suffix = "kb";
        }
        return format1Decimals(value, suffix);
    }


    public static long parseString2Bytes(String sValue) throws Exception {
        if (sValue == null) {
            return 0;
        }
        long bytes;
        try {
            String lastTwoChars = sValue.substring(sValue.length() - Math.min(2, sValue.length())).toLowerCase(Locale.ROOT);
            if (lastTwoChars.endsWith("k")) {
                bytes = (long) (Double.parseDouble(sValue.substring(0, sValue.length() - 1)) * ByteSizeUnit.C1);
            } else if (lastTwoChars.endsWith("kb")) {
                bytes = (long) (Double.parseDouble(sValue.substring(0, sValue.length() - 2)) * ByteSizeUnit.C1);
            } else if (lastTwoChars.endsWith("m")) {
                bytes = (long) (Double.parseDouble(sValue.substring(0, sValue.length() - 1)) * ByteSizeUnit.C2);
            } else if (lastTwoChars.endsWith("mb")) {
                bytes = (long) (Double.parseDouble(sValue.substring(0, sValue.length() - 2)) * ByteSizeUnit.C2);
            } else if (lastTwoChars.endsWith("g")) {
                bytes = (long) (Double.parseDouble(sValue.substring(0, sValue.length() - 1)) * ByteSizeUnit.C3);
            } else if (lastTwoChars.endsWith("gb")) {
                bytes = (long) (Double.parseDouble(sValue.substring(0, sValue.length() - 2)) * ByteSizeUnit.C3);
            } else if (lastTwoChars.endsWith("t")) {
                bytes = (long) (Double.parseDouble(sValue.substring(0, sValue.length() - 1)) * ByteSizeUnit.C4);
            } else if (lastTwoChars.endsWith("tb")) {
                bytes = (long) (Double.parseDouble(sValue.substring(0, sValue.length() - 2)) * ByteSizeUnit.C4);
            } else if (lastTwoChars.endsWith("p")) {
                bytes = (long) (Double.parseDouble(sValue.substring(0, sValue.length() - 1)) * ByteSizeUnit.C5);
            } else if (lastTwoChars.endsWith("pb")) {
                bytes = (long) (Double.parseDouble(sValue.substring(0, sValue.length() - 2)) * ByteSizeUnit.C5);
            } else if (lastTwoChars.endsWith("b")) {
                bytes = Long.parseLong(sValue.substring(0, sValue.length() - 1));
            } else {
                bytes = Long.parseLong(sValue);
            }
        } catch (NumberFormatException e) {
            throw new Exception("Failed to parse [" + sValue + "]", e);
        }
        return bytes;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        ByteSizeValue sizeValue = (ByteSizeValue) o;
//
//        if (size != sizeValue.size) return false;
//        if (sizeUnit != sizeValue.sizeUnit) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = (int) (size ^ (size >>> 32));
//        result = 31 * result + (sizeUnit != null ? sizeUnit.hashCode() : 0);
//        return result;
//    }
    public static enum ByteSizeUnit {
    /**
     * 字节
     */
    BYTES {
            @Override
            public long toBytes(long size) {
                return size;
            }

            @Override
            public long toKB(long size) {
                return size / (C1 / C0);
            }

            @Override
            public long toMB(long size) {
                return size / (C2 / C0);
            }

            @Override
            public long toGB(long size) {
                return size / (C3 / C0);
            }

            @Override
            public long toTB(long size) {
                return size / (C4 / C0);
            }

            @Override
            public long toPB(long size) {
                return size / (C5 / C0);
            }
        },
    /**
     * KB级
     */
        KB {
            @Override
            public long toBytes(long size) {
                return x(size, C1 / C0, MAX / (C1 / C0));
            }

            @Override
            public long toKB(long size) {
                return size;
            }

            @Override
            public long toMB(long size) {
                return size / (C2 / C1);
            }

            @Override
            public long toGB(long size) {
                return size / (C3 / C1);
            }

            @Override
            public long toTB(long size) {
                return size / (C4 / C1);
            }

            @Override
            public long toPB(long size) {
                return size / (C5 / C1);
            }
        },
    /**
     * MB
     */
        MB {
            @Override
            public long toBytes(long size) {
                return x(size, C2 / C0, MAX / (C2 / C0));
            }

            @Override
            public long toKB(long size) {
                return x(size, C2 / C1, MAX / (C2 / C1));
            }

            @Override
            public long toMB(long size) {
                return size;
            }

            @Override
            public long toGB(long size) {
                return size / (C3 / C2);
            }

            @Override
            public long toTB(long size) {
                return size / (C4 / C2);
            }

            @Override
            public long toPB(long size) {
                return size / (C5 / C2);
            }
        },

    /**
     * GB
     */
        GB {
            @Override
            public long toBytes(long size) {
                return x(size, C3 / C0, MAX / (C3 / C0));
            }

            @Override
            public long toKB(long size) {
                return x(size, C3 / C1, MAX / (C3 / C1));
            }

            @Override
            public long toMB(long size) {
                return x(size, C3 / C2, MAX / (C3 / C2));
            }

            @Override
            public long toGB(long size) {
                return size;
            }

            @Override
            public long toTB(long size) {
                return size / (C4 / C3);
            }

            @Override
            public long toPB(long size) {
                return size / (C5 / C3);
            }
        },
    /**
     * TB
     */
        TB {
            @Override
            public long toBytes(long size) {
                return x(size, C4 / C0, MAX / (C4 / C0));
            }

            @Override
            public long toKB(long size) {
                return x(size, C4 / C1, MAX / (C4 / C1));
            }

            @Override
            public long toMB(long size) {
                return x(size, C4 / C2, MAX / (C4 / C2));
            }

            @Override
            public long toGB(long size) {
                return x(size, C4 / C3, MAX / (C4 / C3));
            }

            @Override
            public long toTB(long size) {
                return size;
            }

            @Override
            public long toPB(long size) {
                return size / (C5 / C4);
            }
        },
    /**
     *PB
     */
        PB {
            @Override
            public long toBytes(long size) {
                return x(size, C5 / C0, MAX / (C5 / C0));
            }

            @Override
            public long toKB(long size) {
                return x(size, C5 / C1, MAX / (C5 / C1));
            }

            @Override
            public long toMB(long size) {
                return x(size, C5 / C2, MAX / (C5 / C2));
            }

            @Override
            public long toGB(long size) {
                return x(size, C5 / C3, MAX / (C5 / C3));
            }

            @Override
            public long toTB(long size) {
                return x(size, C5 / C4, MAX / (C5 / C4));
            }

            @Override
            public long toPB(long size) {
                return size;
            }
        };

        static final long C0 = 1L;
        static final long C1 = C0 * 1024L;
        static final long C2 = C1 * 1024L;
        static final long C3 = C2 * 1024L;
        static final long C4 = C3 * 1024L;
        static final long C5 = C4 * 1024L;

        static final long MAX = Long.MAX_VALUE;

        /**
         * Scale d by m, checking for overflow.
         * This has a short name to make above code more readable.
         */
        static long x(long d, long m, long over) {
            if (d > over) {
                return Long.MAX_VALUE;
            }
            if (d < -over) {
                return Long.MIN_VALUE;
            }
            return d * m;
        }


        public abstract long toBytes(long size);

        public abstract long toKB(long size);

        public abstract long toMB(long size);

        public abstract long toGB(long size);

        public abstract long toTB(long size);

        public abstract long toPB(long size);
    }
}
