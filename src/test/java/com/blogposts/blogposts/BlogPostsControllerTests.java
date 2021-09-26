package com.blogposts.blogposts;

import com.blogposts.blogposts.Controllers.BlogPostController;
import com.blogposts.blogposts.Exceptions.ApiException;
import com.blogposts.blogposts.Models.BlogPost;
import com.blogposts.blogposts.Services.BlogPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static java.util.Comparator.comparing;

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
		this.listOfBlogPosts.add(new BlogPost(1, "John", 10, 1000, 0.8, 12000, Arrays.asList(techTag)));
		this.listOfBlogPosts.add(new BlogPost(2, "Peter", 20, 2000, 0.15, 6000, Arrays.asList(techTag)));

		// posts - health only
		this.listOfBlogPosts.add(new BlogPost(3, "Amos", 30, 3000, 0.5, 18000, Arrays.asList(healthTag)));
		this.listOfBlogPosts.add(new BlogPost(4, "Susan", 40, 4000, 0.68, 28000, Arrays.asList(healthTag)));

		// post- health and tech
		this.listOfBlogPosts.add(new BlogPost(5, "Chris", 50, 5000, 0.3, 7000, Arrays.asList(techHealthTags)));
	}

	// TESTING THE FIRST ROUTE "/api/ping"
	@Test
	void itShouldReturnObjectWith_Key_Success_And_Value_True() throws Exception {
		// given
		String url = "/api/ping";
		// we expect status 200 and {"success":true}
		mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("success").value(true));

	}

	// TESTING THE SECOND ROUTE "/api/posts** with different query parameters"

	// 1. ONE QUERY PARAMETER-->tags-->we expect a list of blogs in ascending order
	@Test
	void tagsQueryParameter_itShouldReturn_ListOfBlogPosts() throws Exception {
		// given
		String techHealthTags = "tech,health";
		String sortBy = null;
		String direction = null;
		// using the BlogPostService mock to bypass the call to the actual service
		when(blogPostService.getPostsService(techHealthTags, sortBy, direction)).thenReturn(listOfBlogPosts);

		// we expect the posts to be in ascending order
		// the first post should have an id of 1 and last an id off 5
		mockMvc.perform(get("/api/posts?tags=" + techHealthTags)).andExpect(status().isOk())
				// we expect exactly 5 posts
				.andExpect(jsonPath("$.size()").value(listOfBlogPosts.size()))
				// first post must have id 1
				.andExpect(jsonPath("$.[0].id").value(1))
				// last post must have id 5
				.andExpect(jsonPath("$.[4].id").value(5));
	}

	// 2 Two QUERY PARAMETERS-->tags & direction-->we expect a list of blogs in
	// descending order
	@Test
	void directionAndTagsParameters_itShouldReturn_PostsInDescendingOrder() throws Exception {
		// given
		String techHealthTags = "tech,health";
		String sortBy = null;
		String direction = "desc";
		String url = "/api/posts?tags=" + techHealthTags + "&direction=" + direction;
		// reversing the order of the list of posts-->descending order
		listOfBlogPosts.sort(comparing(BlogPost::getId).reversed());
		// using the BlogPostService mock to bypass the call to the actual service
		when(blogPostService.getPostsService(techHealthTags, sortBy, direction)).thenReturn(listOfBlogPosts);

		// expectation
		mockMvc.perform(get(url)).andExpect(status().isOk())
				// we expect exactly 5 posts
				.andExpect(jsonPath("$.size()").value(listOfBlogPosts.size()))
				// first post must have id 5
				.andExpect(jsonPath("$.[0].id").value(5))
				// last post must have id 1
				.andExpect(jsonPath("$.[4].id").value(1));
	}

	// 3. tags, SortBy and direction QUERY PARAMETERS-->sorting by reads in
	// descending order
	// it should return posts in descending order
	// the first post should have reading of 28000 and last one reads of 6000
	@Test
	void givenTagsSortByReadsAndDirectionDescending_itShouldReturn_allPostsSorted() throws Exception {
		// given
		String techHealthTags = "tech,health";
		String sortBy = "reads";
		String direction = "desc";
		String url = "/api/posts?tags=" + techHealthTags + "&sortBy=" + sortBy + "&direction=" + direction;
		// sorting the the list of posts by reads-->descending order
		listOfBlogPosts.sort(comparing(BlogPost::getReads).reversed());
		// using the BlogPostService mock to bypass the call to the actual service
		when(blogPostService.getPostsService(techHealthTags, sortBy, direction)).thenReturn(listOfBlogPosts);

		// expectation
		mockMvc.perform(get(url)).andExpect(status().isOk())
				// we expect exactly 5 posts
				.andExpect(jsonPath("$.size()").value(listOfBlogPosts.size()))
				// first post must have reads of 28000
				.andExpect(jsonPath("$.[0].reads").value(28000))
				// last post must have reads of 6000
				.andExpect(jsonPath("$.[4].reads").value(6000));
	}

	// 4. NO TAGS QUERY PARAMETER--> no tags-->we expect an error
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

	// 5. sortBy QUERY PARAMETER invalid-->we expect an error
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
