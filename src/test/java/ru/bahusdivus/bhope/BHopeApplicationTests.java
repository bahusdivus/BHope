package ru.bahusdivus.bhope;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.entities.Post;
import ru.bahusdivus.bhope.entities.User;
import ru.bahusdivus.bhope.repository.PostRepository;
import ru.bahusdivus.bhope.repository.UserRepository;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BHopeApplicationTests {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void savePostTest() {
		Optional<User> user = userRepository.findById(11L);
		UserDto userDto = new UserDto(user.get());
		Post post = new Post(userDto);
		post.setTitle("Тестирует добавление поста.");
		post.setContent("Тестирует добавление поста.Тестирует добавление поста.Тестирует добавление поста.");
		postRepository.save(post);
		Assert.assertNotNull(postRepository.findByTitleOrderByDateDesc("Тестирует добавление поста.").get(0));
	}

}
