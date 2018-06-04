package com.nevreme.rolling.controller.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nevreme.rolling.dao.mapping.MapperConfig;
import com.nevreme.rolling.service.AbstractService;


public abstract class AbstractRestController<T, TDTO, ID extends Serializable> {

	private Logger logger = LoggerFactory.getLogger(AbstractRestController.class);

	private AbstractService<T, ID> repo;
	
	Class<? extends Object> clazz;
	
	@Autowired
	private MapperConfig mapper;

	public AbstractRestController(AbstractService<T, ID> repo, TDTO dto) {
		this.repo = repo;
		this.clazz = dto.getClass();
	}

	@CrossOrigin(value="*")
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<T> listAll() {
		List<T> all = this.repo.findAll();
		int i = 0;
		
		for (T t : all) {
			all.set(i, t = (T)mapper.getMapper().map(t, clazz));
			i++;
		}
		return all;
	}
	@CrossOrigin(value="*")
	@RequestMapping(value = "/eager", method = RequestMethod.GET)
	public @ResponseBody List<T> listAllEagerly() {
		List<T> all = this.repo.findAllEagerly();
		int i = 0;
		
		for (T t : all) {
			all.set(i, t = (T)mapper.getMapper().map(t, clazz));
			i++;
		}
		return all;
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Map<String, Object> create(@RequestBody String json) {
		this.repo.saveAsSql(json);
		
		Map<String, Object> m = new HashMap<>();
		m.put("success", true);
		return m;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody T get(@PathVariable ID id) {
		T obj = this.repo.findOne(id);
		obj = (T)mapper.getMapper().map(obj, clazz);
		return obj;
	}
	
	@RequestMapping(value = "/eager/{id}", method = RequestMethod.GET)
	public @ResponseBody T getEager(@PathVariable ID id) {
		T obj = this.repo.findOneEagerly(id);
		obj = (T)mapper.getMapper().map(obj, clazz);
		return obj;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Map<String, Object> update(@PathVariable ID id, @RequestBody String json) {
		logger.debug("update() of id#{} with body {}", id, json);
		logger.debug("T json is of type {}", json.getClass());
		this.repo.updateAsSql(json, id.toString());
		Map<String, Object> m = new HashMap<>();
		m.put("success", true);
		m.put("id", id);
		return m;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delete(@PathVariable ID id) {
		this.repo.delete(id);
		Map<String, Object> m = new HashMap<>();
		m.put("success", true);
		return m;
	}
}
