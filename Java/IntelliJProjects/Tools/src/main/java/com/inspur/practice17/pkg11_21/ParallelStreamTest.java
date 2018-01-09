package com.inspur.practice17.pkg11_21;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;

public class ParallelStreamTest {

    public static void main(String[] args) {
//		List<String> countList = new ArrayList<String>(Collections.nCopies(50000, "string"));
        List<Object> orderList = new ArrayList<Object>();

        for (int i = 0; i < 50000; i++) {
            orderList.add(String.valueOf(i));
        }


        // test
//		orderList.parallelStream().forEach(new Consumer<String>()
//		{
//			@Override
//			public void accept(String t)
//			{
//				System.out.println(t);
//			}
//		});


        // test
//		Map<Integer, String> map = new HashMap<Integer, String>();
//		List<String> ct = orderList.parallelStream()
//				//.filter((r) -> Integer.valueOf(r) > 40000)
//				.map(r -> String.valueOf(Integer.valueOf(r)* 2))
//				.collect(Collectors.toList());
//		System.out.println(ct);


        // test
//		final List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
//		
//        final Optional<Integer> sum = orderList.parallelStream()
//        		.filter((r) -> r > 40000)
//                .reduce((a, b) -> a + b);
//        
//        int a = sum.orElseGet(() -> 0);
//        System.out.println(a);

        // test
//		Optional<String> v = orderList.parallelStream()
//		.map(val -> {
//			return String.valueOf(val);
//		})
//		.reduce((a, b) -> {
//			return (a + b);
//		});
//		
//		v.ifPresent(System.out::println);

        // test
//		String[] arr = {"a","a","a","a","a","a","a","a","a","a"};
//		System.out.println(arr);
//		System.out.println(Arrays.asList(arr));
//      System.out.println(Stream.of(arr).reduce((a ,b) -> a + ',' + b).orElseGet(() -> ""));


        // test
//		List<Integer> numbers = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
//		System.out.println(numbers);


        // test
//		ConcurrentMap<String, ConcurrentMap<String, ConcurrentMap<String, ArrayList<String>>>> obj2Timedimension2Datetime2DataVOs
//		 = new ConcurrentHashMap<String, ConcurrentMap<String, ConcurrentMap<String, ArrayList<String>>>>(); 
//		
//		IntStream.rangeClosed(1, 5).parallel().forEach(i -> {
//			
//			IntStream.rangeClosed(6, 10).parallel().forEach(j -> {
//				
//				IntStream.rangeClosed(11, 15).parallel().forEach(k -> {
//					
//					IntStream.rangeClosed(16, 20).parallel().forEach(g -> {
//						
//						obj2Timedimension2Datetime2DataVOs
//						.computeIfAbsent(String.valueOf(i), (a) -> new ConcurrentHashMap<String, ConcurrentMap<String, ArrayList<String>>>())
//						.computeIfAbsent(String.valueOf(j), (a) -> new ConcurrentHashMap<String, ArrayList<String>>())
//						.computeIfAbsent(String.valueOf(k), (a) -> new ArrayList<String>())
//						.add(String.valueOf(g));
//						
//					});
//					
//				});
//				
//			});
//		});
//		
//		System.out.println(obj2Timedimension2Datetime2DataVOs);


        // test
//		String str = "20171204180520";
//		
//		System.out.println(str.substring(0, 8));
//		System.out.println(str.substring(8, 14));
//		
//		System.out.println(Integer.valueOf(str.substring(10, 12)));


        // test
//        ConcurrentMap<String, ConcurrentMap<String, Integer>> map1 = new ConcurrentHashMap<String, ConcurrentMap<String, Integer>>();
//        ConcurrentMap<String, ConcurrentMap<String, Integer>> map2 = new ConcurrentHashMap<String, ConcurrentMap<String, Integer>>();
//
//        IntStream.range(0, 5).parallel().forEach(i -> {
//
//            IntStream.range(0, 5).parallel().forEach(j -> {
//
//                map1
//                .computeIfAbsent(String.valueOf(i), a -> new ConcurrentHashMap<String, Integer>())
//                .put(String.valueOf(j), j);
//
//            });
//        });
//
//        IntStream.range(0, 5).parallel().forEach(i -> {
//
//            IntStream.range(5, 10).parallel().forEach(j -> {
//
//                map2
//                .computeIfAbsent(String.valueOf(i), a -> new ConcurrentHashMap<String, Integer>())
//                .put(String.valueOf(j - 5), j);
//
//            });
//        });
//
//
//        System.out.println(map1);
//        System.out.println(map2);
//
//        map1.keySet().parallelStream().forEach(i ->
//        {
//
//            System.out.println(Integer.valueOf(i) + 5);
//            System.out.println(i);
//
//            map1.merge(String.valueOf(i), map2.get(i), (ov, nv) ->
//            {
//                ov.keySet().forEach(j ->
//                {
//                    Integer ovj = ov.get(j);
//                    Integer nvj = nv.get(j);
//
//                    ov.put(j, ovj + nvj);
//                    if ("4".equalsIgnoreCase(j)) {
//                        ov.remove(j);
//                    }
//                });
//
//                return ov;
//            });
//        });
//		
//		System.out.println(map1);

        // test
//		Long timeStart = System.currentTimeMillis();
//		ConcurrentMap<String, List<String>> objid_src_date2Upperobjids = new ConcurrentHashMap<String, List<String>>();
//
//		IntStream.range(0, 100).parallel().forEach(i -> {
//
//			IntStream.range(0, 100).parallel().forEach(j -> {
//
//				List<String> list = objid_src_date2Upperobjids
//				.computeIfAbsent(String.valueOf(i), a -> new ArrayList<String>());
//
//				synchronized (list)
//				{
//					list.add(String.valueOf(j));
//				}
//
//			});
//		});
//		Long timeEnd = System.currentTimeMillis();
//
//		System.out.println(objid_src_date2Upperobjids);
//		System.out.println("cost: " + (timeEnd - timeStart));


        // test
//		TreeMap<String, String> treeMap = new TreeMap<String, String>();
//
//		treeMap.put("a", "123");
//		treeMap.put("c", "123");
//		treeMap.put("b", "123");
//		treeMap.put("f", "123");
//		treeMap.put("e", "123");
//		treeMap.put("d", "123");
//
//		System.out.println(treeMap);



    }
}
