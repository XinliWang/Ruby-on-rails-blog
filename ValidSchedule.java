package amazon_test;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ValidSchedule{
	public static void main(String[] args){
		Ad ad1 = new Ad(0, 4, "c1", 100, "area2");
		Ad ad2 = new Ad(2, 5, "c1", 100, "area2");
		Ad ad3 = new Ad(0, 7, "c7", 200, "area2");
		Ad ad4 = new Ad(6, 12, "c2", 150, "area2");
		Ad ad5 = new Ad(9, 20, "c6", 250, "area2");
		Ad ad6 = new Ad(9, 12, "c1", 100, "area2");
		Ad ad7 = new Ad(12, 17, "c3", 125, "area2");
		Ad ad8 = new Ad(14, 20, "c8", 50, "area2");
		Ad ad9 = new Ad(18, 19, "c1", 100, "area2");
		
		Ad ad10 = new Ad(0, 4, "c1", 100, "area1");
		Ad ad11 = new Ad(2, 5, "c5", 100, "area1");
		Ad ad12 = new Ad(0, 7, "c7", 200, "area1");
		Ad ad13 = new Ad(6, 12, "c2", 150, "area1");
		Ad ad14 = new Ad(9, 20, "c6", 250, "area1");
		Ad ad15 = new Ad(9, 12, "c1", 100, "area1");
		Ad ad16 = new Ad(12, 17, "c3", 125, "area1");
		Ad ad17 = new Ad(14, 20, "c8", 50, "area1");
		Ad ad18 = new Ad(18, 19, "c1", 100, "area1");
		Ad ad19 = new Ad(1, 5, "c9", 100, "area1");
		
		List<Ad> schedule = new ArrayList<>();
		schedule.add(ad1);
		schedule.add(ad2);
		schedule.add(ad3);
		schedule.add(ad4);
		schedule.add(ad5);
		schedule.add(ad6);
		schedule.add(ad7);
		schedule.add(ad8);
		schedule.add(ad9);
		schedule.add(ad10);
		schedule.add(ad11);
		schedule.add(ad12);
		schedule.add(ad13);
		schedule.add(ad14);
		schedule.add(ad15);
		schedule.add(ad16);
		schedule.add(ad17);
		schedule.add(ad18);
		schedule.add(ad19);
		
		List<Ad> area1Schedule = new ArrayList<>();
		List<Ad> area2Schedule = new ArrayList<>();
		
		for(Ad ad : schedule){
			String area = ad.getArea();
			if(area.equals("area1")){
				area1Schedule.add(ad);
			}
			else if(area.equals("area2")){
				area2Schedule.add(ad);
			}
		}
		
		for(Ad ad : area1Schedule){
			System.out.println(ad);
		}
		
		Collections.sort(area1Schedule, ValidSchedule.AdStarttimeComparator);
		
		removeSameOverlap(area1Schedule);
		removeForthOverlap(area1Schedule);
		System.out.println("after");
		
		for(Ad ad: area1Schedule){
			System.out.println(ad);
		}
	}
	
	
	public static Comparator<Ad> AdStarttimeComparator = new Comparator<Ad>(){
		public int compare(Ad ad1, Ad ad2){
			int startTime1 = ad1.getStart();
			int startTime2 = ad2.getStart();
			
			//ascending order
			return startTime1 - startTime2;
			
			//descending order
			// return startTime2 - startTime1
		}
		
	};
	
	public static Comparator<Ad> AdEndtimeComparator = new Comparator<Ad>(){
		public int compare(Ad ad1, Ad ad2){
			int endTime1 = ad1.getEnd();
			int endTime2 = ad2.getEnd();
			//ascending order
			return endTime1 - endTime2;
			
			//descending order
			// return startTime2 - startTime1
		}
	};
	
	/**
	 * Time complexity : O(n).
	 * But it requires a sorting which is O(nlog) before this function.
	 * @param schedule
	 */
	public static void removeForthOverlap(List<Ad> schedule){
		PriorityQueue<Ad> overlap = new PriorityQueue<Ad>(4, ValidSchedule.AdEndtimeComparator);
		Iterator<Ad> scItr = schedule.iterator();
		int s;
		while(scItr.hasNext()){
			Ad a = (Ad) scItr.next();
			if (overlap.size() == 0) { overlap.add(a); }
			else {
				s = a.getStart();
				while (s >= (overlap.peek().getEnd())) { // sMax > eMin, remove the earliest end Ad.
					overlap.poll();
				}
				if (overlap.size() == 3) { // a forth overlap occurs
					scItr.remove(); // remove the forth Ad in schedule
					//overlap.clear(); // 这句似乎是多余的？2016-01-30
				} else {
					overlap.add(a);
				}				
			}
		}
	}
	
//	private static Ad getMinEndAd(List<Ad> overlap) {
//		int minEnd = Integer.MAX_VALUE, curEnd, minIndex = -1, n = overlap.size();
//		Ad ad;
//		for (int i = 0; i < n; i++) {
//			if ( (curEnd = (ad = overlap.get(i)).getEnd()) < minEnd ) {
//				minEnd = curEnd;
//				minIndex = i;
//			}
//		}
//		return overlap.get(minIndex);
//	}
	
	public static void removeSameOverlap(List<Ad> schedule){
		// map ID and endTime
		Map<String, Integer> map = new HashMap<>();
		Iterator<Ad> iterator = schedule.iterator();
		while(iterator.hasNext()){
			Ad ad = (Ad) iterator.next();
			String ID = ad.getID();
			
			// if content is not in map. Add content.
			if(map.get(ID) == null){
				map.put(ID, ad.getEnd());
			}
			else{
				// if the same contents overlap at the same time. Remove content.
				if(map.get(ID) > ad.getStart()){
					iterator.remove();
				}
			}
		}
		return;
	}
}
