package com.nevreme.rolling.dao.sql;

import java.util.Arrays;
import java.util.Iterator;

import org.json.JSONObject;

public class SqlBuilder {
	private String select = "";
	private String where = "";
	private String fetch = "";
	private String insert = "";
	private String update = "";
	private String order = "";
	private String valuesNames = "";
	private String values = "";

	public <T> SqlBuilder select(Class<T> clazz, boolean distinct) {
		String distinctText = distinct ? "DISTINCT(t)" : "t";
		this.select = "Select " + distinctText + " from " + clazz.getSimpleName() + " t ";
		return this;
	}

	public SqlBuilder where(String id, String... altId ) {
		this.where = "WHERE t." + id + " = (:" + (altId.length>0 ? altId[0] : id) + ")";
		return this;
	}
	
	public SqlBuilder order(String field, String sortType) {
		this.order = " ORDER BY t." + field+" "+sortType;
		return this;
	}

	public SqlBuilder fetch(String name) {
		this.fetch += "LEFT JOIN FETCH t." + name + " ";
		return this;
	}

	public String build() {
		return select + fetch + where + order;
	}

	public <T> SqlBuilder insert(Class<T> clazz) {
		String insert = "INSERT INTO " + clazz.getSimpleName().toLowerCase() + " (";
		this.insert = insert;
		return this;
	}
	public <T> SqlBuilder update(Class<T> clazz) {
		String update = "UPDATE " + clazz.getSimpleName().toLowerCase() + " SET ";
		this.update = update;
		return this;
	} 
	public SqlBuilder updateValues(String json, String id) {
		//TODO: Disable commas inside string
		JSONObject object = new JSONObject(json);
		String sentence = "";
		System.out.println("::::::::::::::::");
		for (String key : object.keySet()) {
			System.out.println(key+",");
			System.out.println(object.get(key));
			sentence += key +"='"+object.get(key)+"',";
		}
		sentence = sentence.substring(0, sentence.length() - 1);
		update += sentence+ "WHERE id = "+id;
		return this;
	}
	
	public SqlBuilder values(String json, String apostrophe) {
		//TODO: Disable commas inside string
		JSONObject object = new JSONObject(json);
		String names = "";
		String values = "";
		System.out.println("::::::::::::::::");
		for (String key : object.keySet()) {
			System.out.println(key+",");
			System.out.println(object.get(key));
			names += key +",";
			apostrophe = object.get(key).getClass().getSimpleName().equals("String")?"'":"";
			values += apostrophe+object.get(key)+apostrophe+",";
		}
		System.out.println("::::::::::::::::");
		
		names = names.substring(0, names.length() - 1);
		values = values.substring(0, values.length() - 1);
		this.valuesNames = names + ")";
		this.values = " VALUES ("+values+")";
		return this;
	}

	public String insertBuild() {
		return insert + valuesNames + values;
	}
	public String updateBuild() {
		return update;
	}
	
//	public static void main(String[] a) {
//		JSONObject object = new JSONObject("{\"maki\":\"mj\",\"tamara\":\"tf\"}");
//		String [] array = new String [3];
//		array[0] = "mj";array[1] = "mj1";array[2] = "mj2";
//		
//		System.out.println(Arrays.toString(array).replace("[", "").replace("]", ""));
//	}
	
	public Object[] getValues (String json) {
		 JSONObject object = new JSONObject(json);
		 Iterator<String> keys = object.keys();
		 Object[] values = new Object[object.keySet().size()];
		 int i = 0;
		 while (keys.hasNext()) {
			 values[i] = object.get(keys.next());
			 i++;
		 }
		 return values;
	}
	
	public String getKeys (String json) {
		 JSONObject object = new JSONObject(json);
		 Iterator<String> keys = object.keys();
		 Object[] names = new Object[object.keySet().size()];
		 int i = 0;
		 while (keys.hasNext()) {
			 names[i] = keys.next();
			 i++;
		 }
		 return Arrays.toString(names).replace("[", "").replace("]", "");
	}
	
	public <T> String insertQuery (Class<T> clazz, String json) {
		int size = new JSONObject(json).keySet().size();
		String questionMarks = "";
		for (int i = 0;i<size;i++) {
			questionMarks+="?,";
		}
		questionMarks = questionMarks.substring(0,questionMarks.length()-1);
		String query = "Insert into "+clazz.getSimpleName().toLowerCase()+" ("+getKeys(json)+") VALUES ("+questionMarks+")";
		return query;
	}

}
