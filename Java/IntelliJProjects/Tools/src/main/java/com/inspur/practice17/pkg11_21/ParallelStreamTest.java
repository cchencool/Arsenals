package com.inspur.practice17.pkg11_21;

import java.util.concurrent.*;
import java.util.stream.*;
import java.util.*;

public class ParallelStreamTest {
    public static void main(String[] args) {
        List<Object> orderList = new ArrayList<Object>();
        for (int i = 0; i < 50000; ++i) {
            orderList.add(String.valueOf(i));
        }
        runDataCalculateTest(args);
    }

    private static void runDataCalculateTest(String[] args) {
        Set<DataVO> dataVOs = new HashSet<DataVO>();
        ConcurrentHashMap<String, DataVO> key2DataVo = new ConcurrentHashMap<String, DataVO>();
        Set<DataVO> dataVOs_p = new HashSet<DataVO>();
        ConcurrentHashMap<String, DataVO> key2DataVo_p = new ConcurrentHashMap<String, DataVO>();
        int default_dataCount = 10000;
        int default_valueCount = 100;
        if (args.length > 1) {
            default_dataCount = Integer.valueOf(args[0]);
            default_valueCount = Integer.valueOf(args[1]);
        }
        boolean isRunNoParallerTest = true;//false;
        if (args.length > 2) {
            isRunNoParallerTest = "true".equalsIgnoreCase(args[2]);
        }
        boolean isOutPutMap = false;
        if (args.length > 3) {
            isOutPutMap = "true".equalsIgnoreCase(args[3]);
        }


        long time_start_cd = System.currentTimeMillis();
        int valueCount = default_valueCount;
        ConcurrentHashMap<String, DataVO> key2DataVO = new ConcurrentHashMap<String, DataVO>();
        ConcurrentHashMap<String, DataVO> key2DataVO_p = new ConcurrentHashMap<String, DataVO>();
        IntStream
                .range(30, default_dataCount)
                .parallel()
                .forEach(i -> {
                    ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();
                    IntStream.range(1, valueCount).parallel().forEach(j -> map.put(String.valueOf(j), j));
                    String key = String.valueOf(i);
                    DataVO dataVO2 = new DataVO(key, map);
                    dataVOs.add(dataVO2);
                    key2DataVO.put(key, dataVO2);

                    ConcurrentHashMap<String, Integer> map_p = new ConcurrentHashMap<String, Integer>();
                    IntStream.range(1, valueCount).parallel().forEach(j -> map_p.put(String.valueOf(j), j));
                    String key_p = String.valueOf(i);
                    DataVO dataVO_p = new DataVO(key_p, map_p);
                    dataVOs_p.add(dataVO_p);
                    key2DataVO_p.put(key_p, dataVO_p);
                });
        long time_end_cd = System.currentTimeMillis();
        System.out.println("create data cost: " + (time_end_cd - time_start_cd));

//		Collections.synchronizedSet(dataVOs_p);

        long time_start = System.currentTimeMillis();
        dataVOs_p.parallelStream().forEach(dataVO -> {
            String key = String.valueOf(Integer.valueOf(dataVO.getId()) % 30);
            key2DataVO_p
                    .merge(key,
                            new DataVO(key, new ConcurrentHashMap<String, Integer>(dataVO.getValueMap())),
                            (oldV, newV) -> {
                                ConcurrentHashMap<String, Integer>  old_vmap = oldV.getValueMap();
                                ConcurrentHashMap<String, Integer>  new_vmap = oldV.getValueMap();
                                synchronized (old_vmap) {
                                    new_vmap.keySet()
                                            .forEach(k -> old_vmap.compute(k, (nv, ov) -> Integer.valueOf(nv) + Integer.valueOf(ov)));
                                }
                                return oldV;
                            });
        });
        long time_end = System.currentTimeMillis();
        System.out.println("parallel set cost: " + (time_end - time_start));


        if (isRunNoParallerTest) {
            time_start = System.currentTimeMillis();
            dataVOs.parallelStream().forEach(dataVO -> {
                String key = String.valueOf(Integer.valueOf(dataVO.getId()) % 30);
                key2DataVO.merge(key,
                        new DataVO(key, new ConcurrentHashMap<String, Integer>(dataVO.getValueMap())),
                        (oldV, newV) -> {
                            ConcurrentHashMap<String, Integer>  old_vmap = oldV.getValueMap();
                            ConcurrentHashMap<String, Integer>  new_vmap = oldV.getValueMap();
                            synchronized (old_vmap) {
                                new_vmap.keySet()
                                        .forEach(k -> old_vmap.compute(k, (nv, ov) -> Integer.valueOf(nv) + Integer.valueOf(ov)));
                            }
                            return oldV;
                        });
            });
            time_end = System.currentTimeMillis();
            System.out.println("no parallel set cost: " + (time_end - time_start));
        }


        if (isOutPutMap && isRunNoParallerTest) {
            List<DataVO> finalDs = key2DataVo.values().parallelStream()
                    .filter(d -> Integer.valueOf(d.getId()) < 30).collect(Collectors.toList());
            System.out.println(finalDs);
            System.out.println(finalDs.size());
        }

        time_start = System.currentTimeMillis();
        List<DataVO> finalDs_p = key2DataVo_p.values().parallelStream()
                .filter(d -> Integer.valueOf(d.getId()) < 30).collect(Collectors.toList());
        if (isOutPutMap) {
            System.out.println(finalDs_p);
        }
        time_end = System.currentTimeMillis();
        System.out.println("result org cost: " + (time_end - time_start));
        System.out.println("result size: " + finalDs_p.size());
    }


    public static class DataVO
    {
        private String id;
        private ConcurrentHashMap<String, Integer> valueMap;

        public DataVO(final String id, final ConcurrentHashMap<String, Integer> map) {
            this.id = id;
            this.valueMap = map;
        }

        public String getId() {
            return this.id;
        }

        public void setId(final String id) {
            this.id = id;
        }

        public ConcurrentHashMap<String, Integer> getValueMap() {
            return this.valueMap;
        }

        public void setValueMap(final ConcurrentHashMap<String, Integer> valueMap) {
            this.valueMap = valueMap;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            sb.append("id: " + this.id);
            sb.append("\n");
            sb.append("val: " + this.valueMap);
            sb.append("\n");
            return sb.toString();
        }
    }
}
