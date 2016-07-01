package com.lanen.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataComparator implements Comparator<Object> {  
    private List<Object> list = new ArrayList<Object>();  
      
    public int compare(Object o1, Object o2) {  
      int o1_index = -1;  
      int o2_index = -1;  
      if ((o2_index = list.indexOf(o2)) == -1 && list.add(o2)) {  
        o2_index = list.indexOf(o2);  
      }  
      if ((o1_index = list.indexOf(o1)) == -1 && list.add(o1)) {  
        o1_index = list.indexOf(o1);  
      }  
  
      if (o1_index > o2_index) {  
        return 1;  
      } else if (o1_index < o2_index) {  
        return -1;  
      } else {  
        return 0;  
      }  
    }  
}  
