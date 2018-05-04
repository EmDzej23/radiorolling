package com.nevreme.rolling.dao.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.nevreme.rolling.dto.VideoDto;
import com.nevreme.rolling.model.Video;

@Component
public class MapperConfig {

	private ModelMapper mapper;

	
	private MapperConfig() {
		initializeMapper();
	}
	
	PropertyMap<Video, VideoDto> videoDtoMap = new PropertyMap<Video, VideoDto>() {
		protected void configure() {
			map(source.getPlaylist().getName(), destination.getPlName());
//			map(source.getDailyRecommend(), destination.getDailyRecommend());
		};
	};

	private void initializeMapper() {
		mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		mapper.addMappings(videoDtoMap);
	}

	public ModelMapper getMapper() {
		return mapper;
	}

	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

}
