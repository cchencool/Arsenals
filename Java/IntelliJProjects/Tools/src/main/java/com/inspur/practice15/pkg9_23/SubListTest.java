package com.inspur.practice15.pkg9_23;

import java.util.*;
import java.util.Map.Entry;

public class SubListTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
            map.put(String.valueOf(i), String.valueOf(Math.floor(Math.random() * 100)));
        }

        List<String> subList1 = list.subList(0, 40);
        List<String> subList2 = list.subList(40, 100);
        System.out.println(subList1.get(0) + "; " + subList1.get(subList1.size() - 1));
        System.out.println(subList2.get(0) + "; " + subList2.get(subList2.size() - 1));
        list = list.subList(40, 100);
        ;
        System.out.println(list.get(0) + "; " + list.get(list.size() - 1));


        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            System.out.println(key + " : " + map.get(key));
        }
        System.out.println("--------------END---------------");
        map = sortMapByValue(map);
        for (String key : keySet) {
            System.out.println(key + " : " + map.get(key));
        }
    }


    private static Map<String, String> sortMapByValue(Map<String, String> oriMap) {
        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        if (oriMap != null && !oriMap.isEmpty()) {
            List<Entry<String, String>> entryList = new ArrayList<Entry<String, String>>(oriMap.entrySet());
            Collections.sort(entryList, new Comparator<Entry<String, String>>() {
                public int compare(Entry<String, String> entry1, Entry<String, String> entry2) {
                    int value1 = 0, value2 = 0;
                    try {
                        value1 = Integer.parseInt(entry1.getValue());
                        value2 = Integer.parseInt(entry2.getValue());
                    } catch (NumberFormatException e) {
                        value1 = 0;
                        value2 = 0;
                    }
                    return value1 - value2;
                }
            });
            Iterator<Entry<String, String>> iter = entryList.iterator();
            Entry<String, String> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }
}
