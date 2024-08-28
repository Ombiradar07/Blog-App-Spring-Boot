package com.osmblog;

import com.osmblog.Entities.Category;
import com.osmblog.Entities.Post;
import com.osmblog.Entities.User;
import com.osmblog.Payload.CategoryDto;
import com.osmblog.Payload.PostDto;
import com.osmblog.Payload.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OsmblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsmblogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// Add mappings for nested entities
		modelMapper.typeMap(Post.class, PostDto.class).addMappings(mapper -> {
			mapper.map(Post::getCategory, PostDto::setCategoryDto);
			mapper.map(Post::getUser, PostDto::setUserDto);
		});

		modelMapper.typeMap(Category.class, CategoryDto.class);
		modelMapper.typeMap(User.class, UserDto.class);

		return modelMapper;
	}


}
