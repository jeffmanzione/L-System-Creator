package lsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {
	private Map<String, Rule> map;
	
	public Grammar() {
		map = new HashMap<>();
	}
	
	public void add(String name, Rule rule) {
		map.put(name, rule);
	}

	public List<String> get() {
		List<String> lst = new ArrayList<>();
		map.forEach((k,v) -> lst.add(k + " -> { " + v + " }"));
		
		return lst;
	}
	
	public Rule get(String key) {
		return map.get(key);
	}
}
