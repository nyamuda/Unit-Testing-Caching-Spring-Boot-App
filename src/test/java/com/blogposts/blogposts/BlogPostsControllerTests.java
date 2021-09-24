package com.blogposts.blogposts;

import com.blogposts.blogposts.Controllers.BlogPostController;
import com.blogposts.blogposts.Exceptions.ApiException;
import com.blogposts.blogposts.Models.BlogPost;
import com.blogposts.blogposts.Services.BlogPostService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlogPostsControllerTests {
	// we will add our new posts to this lists.
	private List<BlogPost> listOfBlogPosts;

	@Autowired
	private BlogPostController blogController;
	// the controller is dependant on the the BlogPostService
	// so we create a mock of the BlogPostService instead of using the real one.
	@MockBean
	private BlogPostService blogPostService;
	ObjectMapper objectMapper = new ObjectMapper();
	// a mock of the MVC so that we can handle incoming HTTP requests without
	// starting up the server.
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		// CREATING NEW POSTS
		String[] techTag = { "tech" };
		String[] healthTag = { "health" };
		String[] techHealthTags = { "health", "tech" };
		this.listOfBlogPosts = new ArrayList<>();
		// posts tech only
		this.listOfBlogPosts.add(new BlogPost(1, "John", 10, 1000, 0.8, 5000, Arrays.asList(techTag)));
		this.listOfBlogPosts.add(new BlogPost(2, "Peter", 20, 2000, 0.15, 6000, Arrays.asList(techTag)));

		// posts - health only
		this.listOfBlogPosts.add(new BlogPost(3, "Amos", 30, 3000, 0.5, 7000, Arrays.asList(healthTag)));
		this.listOfBlogPosts.add(new BlogPost(4, "Susan", 40, 4000, 0.68, 8000, Arrays.asList(healthTag)));

		// post- health and tech
		this.listOfBlogPosts.add(new BlogPost(5, "Chris", 50, 5000, 0.3, 9000, Arrays.asList(techHealthTags)));
	}

	// TESTING THE FIRST ROUTE "/api/ping"
	@Test
	void itShouldReturnObjectWith_Key_Success_And_Value_True() throws Exception {
		// given
		String url = "/api/ping";
		// we expect status 200 and {"success":true}
		mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("success").value(false));

	}

	// TESTING THE SECOND ROUTE "/api/posts** with different query parameters"

	// 1. ONE QUERY PARAMETER-->tags-->we expect a list of blogs
	@Test
	void tagsQueryParameter_itShouldReturn_ListOfBlogPosts() throws Exception {
		// given
		String techHealthTags = "tech,health";
		String sortBy = null;
		String direction = null;
		// using the BlogPostService mock to bypass the call to the actual service
		when(blogPostService.getPostsService(techHealthTags, sortBy, direction)).thenReturn(listOfBlogPosts);

		// we expect 5 posts
		mockMvc.perform(get("/api/posts?tags=")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(listOfBlogPosts.size()));

	}

	// 2. NO TAGS QUERY PARAMETER--> no tags-->we expect an error
	@Test
	void noTags_thenShouldReturn_anError_status400() throws Exception {
		// given
		String tags = null;
		String sortBy = null;
		String direction = null;
		// when tags query parameter is not given
		when(blogPostService.getPostsService(tags, sortBy, direction))
				.thenThrow(new ApiException("Tags parameter is required"));

		// no tag query parameter-->what we expect-->
		// status 400 and {"error":"Tags parameter is required"}
		mockMvc.perform(get("/api/posts")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Tags parameter is required"));

	}
	// 3. sortBy QUERY PARAMETER invalid--> no tags-->we expect an error
	@Test
	void invalidSortBy_thenShouldReturn_anError_status400() throws Exception {
		// given
		String tags = "health";
		String sortBy = "helloWorld";
		String direction = null;
		String url = "/api/posts?tags=" + tags + "&sortBy=" + sortBy;
		// when sortBy query parameter is invalid
		when(blogPostService.getPostsService(tags, sortBy, direction))
				.thenThrow(new ApiException("sortBy parameter is invalid"));

		// sortBy query parameter is invalid-->what we expect-->
		// status 400 and {"error":"sortBy parameter is invalid"}
		mockMvc.perform(get(url)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("sortBy parameter is invalid"));
	}

}
