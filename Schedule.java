package amazon;

import introJAVA.newTestJava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Schedule {
	
	/**
	 * @param
	 * 
	 */
	public static void schedule(){
		ArrayList<ArrayList<Content>> lists = new ArrayList<>();
		HashMap<String, Content> map = new HashMap<>();
		
		PriorityQueue<Content> queue = new PriorityQueue<Content>(3,new Comparator<Content>() {
			public int compare(Content c1 , Content c2){
				return c1.getEndTime() - c2.getEndTime();
			}
		});
		//Read In
//		Iterator<Content> iterator = readIn();
//		while(iterator.hasNext()){
//			Content content = iterator.next();
//			String location = content.getLocationId();
//			//convert char to integer and minus 1 to be the index
//			int index = location.charAt(location.length()-1)-'1';
//			lists.get(index).add(content);
//		}
		lists.add(new ArrayList<Content>());
		lists.get(0).add(new Content("c9", "a1", 2, 5, 10));
		lists.get(0).add(new Content("c1", "a1", 1, 4, 10));
		lists.get(0).add(new Content("c5", "a1", 3, 5, 10));
		lists.get(0).add(new Content("c7", "a1", 1, 7, 10));
		lists.get(0).add(new Content("c2", "a1", 7, 12, 10));
		lists.get(0).add(new Content("c3", "a1", 13, 17, 10));
		lists.get(0).add(new Content("c1", "a1", 19, 19, 10));
		lists.get(0).add(new Content("c6", "a1", 10, 20, 10));
		lists.get(0).add(new Content("c1", "a1", 10, 12, 10));
		lists.get(0).add(new Content("c8", "a1", 15, 20, 10));
		
		
		
		
		for(int i=0; i < lists.size(); i++){
			ArrayList<Content> result = new ArrayList<>();
			
			//sort by content's start time
			Collections.sort(lists.get(i), new Comparator<Content>() {
		        @Override public int compare(Content c1, Content c2) {
		            return c1.getStartTime()- c2.getStartTime();
		        }

		    });
			
			for(int j=0; j < lists.get(i).size(); j++){
				Content content = lists.get(i).get(j);
				String contentId = content.getContentId();
				if(!map.containsKey(contentId)){
					map.put(contentId, content);
					
					if(queue.size()<3){
						queue.add(content);
						result.add(content);
					}else{
						Content temp = queue.peek();
						if(content.getStartTime()>temp.getEndTime()){
							queue.poll();
							queue.add(content);
							result.add(content);
						}
					}
					
				}else{
					if(content.getStartTime()>map.get(contentId).getEndTime()){
						map.replace(contentId, content);
						
						if(queue.size()<3){
							queue.add(content);
							result.add(content);
						}else{
							Content temp = queue.peek();
							if(content.getStartTime()>temp.getEndTime()){
								queue.poll();
								queue.add(content);
								result.add(content);
							}
						}						
						
					}
				}
				
			}
			for(int r=0;r<result.size();r++){
				System.out.println(result.get(r).getContentId());
			}
		}
		
		
		
		
	}
	public static void schedule2(){
		HashMap<String, ArrayList<Content>> hashMap = new HashMap<>();
		//Read In
		Iterator<Content> iterator = readIn();
		while(iterator.hasNext()){
			Content content = iterator.next();
			if(hashMap.containsKey(content.getLocationId())){}
		}
		
		
		
		
		
		
	}
	
	
	public static void main(String[] args){
		schedule();
	}
	
	
	private static Iterator<Content> readIn() {
		
		return null;
	}
}
