package com.eoutlet.Eoutlet.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {





    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("أحذية نسائية");
        cricket.add("ملابس نسائية");


        List<String> football = new ArrayList<String>();
        football.add("أحذية نسائية");
        football.add("ملابس نسائية");


        List<String> basketball = new ArrayList<String>();
        basketball.add("أحذية نسائيةs");
        basketball.add("ملابس نسائية");

        expandableListDetail.put("ماركات", cricket);
        expandableListDetail.put("مواليد", football);
        expandableListDetail.put("أطفال", basketball);
        return expandableListDetail;
    }
}
