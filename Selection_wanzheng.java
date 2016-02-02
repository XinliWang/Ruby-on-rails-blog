package test2;

import java.util.*;


public class Selection {
	//Do not use global variables, you may create new class instead or other ways to avoid this
    public static double maxValue = 0;
    public static HashMap<String, ScheduleRequest> res = new HashMap<String, ScheduleRequest>();
    
    
    public static void main(String[] args){
        selection();
    }
    
    public static void selection(){
    	//Input -- it will be given in Iterator format during the on-site interview
    	ScheduleRequest sr1 = new ScheduleRequest(10, 0, 3, "a", "l1");
    	ScheduleRequest sr2 = new ScheduleRequest(15, 1, 3, "b", "l1");
    	ScheduleRequest sr3 = new ScheduleRequest(10, 0, 4, "a", "l2");
    	ScheduleRequest sr4 = new ScheduleRequest(15, 2, 3, "b", "l2");
    	ScheduleRequest sr5 = new ScheduleRequest(20, 3, 4, "c", "l2");
    	ScheduleRequest sr6 = new ScheduleRequest(20, 2, 5, "c", "l3");
    	ScheduleRequest sr7 = new ScheduleRequest(10, 2, 4, "a", "l4");
    	ScheduleRequest sr8 = new ScheduleRequest(25, 2, 3, "d", "l5");
    	ScheduleRequest sr9 = new ScheduleRequest(25, 0, 2, "d", "l6");
    	ScheduleRequest sr10 = new ScheduleRequest(15, 1, 3, "b", "l6");
    	
    	ArrayList<ScheduleRequest> input = new ArrayList<ScheduleRequest>();
    	input.add(sr1);
    	input.add(sr2);
    	input.add(sr3);
    	input.add(sr4);
    	input.add(sr5);
    	input.add(sr6);
    	input.add(sr7);
    	input.add(sr8);
    	input.add(sr9);
    	input.add(sr10);
    	
    	//you need to find out the best selection (with the highest score in total) at location 1,3,4,5,6 when time is at "2"
    	SelectionRequest slr1 = new SelectionRequest(2);
    	slr1.setList("l1");
    	slr1.setList("l3");
    	slr1.setList("l4");
    	slr1.setList("l5");
    	slr1.setList("l6");
    	
    	//you need to find out the best selection (with the highest score in total) at location 1,2,4,6 when time is at "3"
    	SelectionRequest slr2 = new SelectionRequest(3);
    	slr2.setList("l1");
    	slr2.setList("l2");
    	slr2.setList("l4");
    	slr2.setList("l6");
    	
    	//a hashmap (time, (location, ScheduleRequest))
        HashMap<Integer, HashMap<String, ArrayList<ScheduleRequest>>> map = new HashMap<Integer, HashMap<String, ArrayList<ScheduleRequest>>>();
        
        //6 areas with different multipliers
        HashMap<String, Double> multiplier = new HashMap<String, Double>();
        multiplier.put("l1", 1.0);
        multiplier.put("l2", 0.8);
        multiplier.put("l3", 0.75);
        multiplier.put("l4", 0.5);
        multiplier.put("l5", 0.3);
        multiplier.put("l6", 0.2);

        //a set to check if this content has been selected
        HashSet<String> visited = new HashSet<String>();
        
        
        //put all input into a hashmap
        for (ScheduleRequest sr : input) {
        	for (int i = sr.start; i <= sr.end; i++) {
        		if (!map.containsKey(i)) {
        			map.put(i, new HashMap<String, ArrayList<ScheduleRequest>>());
        			map.get(i).put(sr.LocationId, new ArrayList<ScheduleRequest>());
        			map.get(i).get(sr.LocationId).add(sr);
        		}
        		else {
        			if (!map.get(i).containsKey(sr.LocationId)) {
        				map.get(i).put(sr.LocationId, new ArrayList<ScheduleRequest>());
            			map.get(i).get(sr.LocationId).add(sr);
        			} else {
        				map.get(i).get(sr.LocationId).add(sr);
        			}
        		}
        	}
        }
        
        //get answer with slr1 or slr2
        int time = slr1.time;
        if (!map.containsKey(time)) {
        	System.out.println("There is no Seletion at this time!");
        	return;
        }
        List<String> locations = slr1.locations;
        HashMap<String, ArrayList<ScheduleRequest>> locationContents = map.get(time);
        HashMap<String, ScheduleRequest> curRes = new HashMap<String, ScheduleRequest>();

        dfs(0, locations, 0.0, visited, curRes, locationContents, multiplier);
        System.out.println(maxValue);
        for (ScheduleRequest srsr : res.values()) {
        	System.out.println(srsr.ContentId);
        }
    }

    public static void dfs(int index, List<String> locations, double sum, HashSet<String> visited, HashMap<String, ScheduleRequest> curRes, 
    		HashMap<String, ArrayList<ScheduleRequest>> locationContents, HashMap<String, Double> multiplier){
        if (index >= locations.size()){
            if (sum > maxValue){
                maxValue = sum;
                res.clear();
                res.putAll(curRes);
            }
            return;
        }
        
        String location = locations.get(index);
        if (!locationContents.containsKey(location)) {
        	dfs(index + 1, locations, sum, visited, curRes, locationContents, multiplier);
        } else {
        	for (ScheduleRequest sr: locationContents.get(location)) {
        		String name = sr.ContentId;
        		if (!visited.contains(name)) {
        			visited.add(name);
        			curRes.put(sr.LocationId, sr);
        			sum += sr.score * multiplier.get(sr.LocationId);
        			dfs(index + 1, locations, sum, visited, curRes, locationContents, multiplier);
        			visited.remove(name);
        			curRes.remove(sr.LocationId, sr);
        			sum -= sr.score * multiplier.get(sr.LocationId);
        		}
        	}
        	dfs(index + 1, locations, sum, visited, curRes, locationContents, multiplier);
        }
    }

}