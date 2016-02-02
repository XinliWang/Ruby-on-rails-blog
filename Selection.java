package amazon;

import introJAVA.newTestJava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Selection {
	
	private ArrayList<Content> result = new ArrayList<>();
	private ArrayList<Double> evaluation;
	private static double maxValue = 0;
	public void selection(ArrayList<Content> input,int time){
		HashMap<String, Double> areaValueMap = new HashMap<>();
		areaValueMap.put("a1",1.0);
		areaValueMap.put("a2",0.8);
		areaValueMap.put("a3",0.75);
		areaValueMap.put("a4",0.5);
		areaValueMap.put("a5",0.3);
		areaValueMap.put("a6",0.2);
		
		evaluation = new ArrayList<>();
		evaluation.add(0,10.0);
		evaluation.add(1,8.0);
		evaluation.add(2,6.0);
		evaluation.add(3,5.0);
		evaluation.add(4,4.0);
		evaluation.add(5,3.0);
		
		ArrayList<Content> lists = new ArrayList<>();
		lists.add(new Content("c9", "a1", 2, 5, 10));
		lists.add(new Content("c1", "a1", 1, 4, 6));
		lists.add(new Content("c5", "a2", 3, 5, 10));
		lists.add(new Content("c7", "a2", 1, 7, 10));
		lists.add(new Content("c2", "a3", 1, 12, 10));
		lists.add(new Content("c3", "a3", 1, 17, 10));
		lists.add(new Content("c1", "a4", 1, 19, 6));
		lists.add(new Content("c6", "a5", 1, 20, 10));
		lists.add(new Content("c1", "a6", 1, 12, 6));
		lists.add(new Content("c8", "a6", 1, 20, 10));
		
		
		
		
		HashMap<String,ArrayList<Content>> map = new HashMap<>();
		for(Content content:input){
			if(time>=content.getStartTime() && time<= content.getEndTime()){
				String locationId = content.getLocationId();
				if(!map.containsKey(locationId)){
					map.put(locationId, new ArrayList<>());
				}
				map.get(locationId).add(content);
			}
		}
		for(ArrayList<Content> list : map.values()){
			Collections.sort(list,new Comparator<Content>() {
				@Override
				public int compare(Content c1,Content c2){
					return c1.getValue() - c2.getValue();
				}
			});
		}
		helper(map,areaValueMap,new HashMap<String,Integer>(),new HashSet<String>(),new ArrayList<Content>(),0,0);
		for(Content c: result){
			System.out.println(c.getContentId());
		}
	}
	
	public void helper(HashMap<String,ArrayList<Content>> map,HashMap<String, Double> areaValueMap,  HashMap<String,Integer> current,HashSet<String> visited,ArrayList<Content> list,int area,double sum){
		
		if(area == 6){
			if(sum>maxValue){
				maxValue = sum;
				result.clear();
				result.addAll(list);
			}
			return;
		}else{
			for(Content adContent : map.get(area)){
				String adId = adContent.getContentId();
				if(!visited.contains(adId)){
					visited.add(adId);
					list.add(adContent);
					sum += adContent.getValue() * areaValueMap.get(adContent.getLocationId());
					
					int count=0;
					for(int i = area;i<=6;i++){
						count += evaluation.get(i);
					}
					if(maxValue >= count+sum){
						visited.remove(adContent);
						return;
					}
					//helper()
					helper( map, areaValueMap, current, visited, list, area+1, sum);
					sum -= adContent.getValue() * areaValueMap.get(adContent.getLocationId());
					list.remove(adContent);
				}
			}
			helper( map, areaValueMap, current, visited, list, area+1, sum);
		}
	}
}
