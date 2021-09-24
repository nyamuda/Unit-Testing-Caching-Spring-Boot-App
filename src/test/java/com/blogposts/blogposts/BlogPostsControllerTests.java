package com.blogposts.blogposts;

import com.blogposts.blogposts.Controllers.BlogPostController;
import com.blogposts.blogposts.Services.BlogPostService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.isNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlogPostsControllerTests {

	@Autowired
	private BlogPostController blogController;

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	// TESTING THE FIRST ROUTE "/api/ping"
	@Test
	void itShouldReturnObjectWith_Key_Success_And_Value_True() throws Exception {
		// given
		String url = "/api/ping";
		SuccessPing successPing = new SuccessPing(true);

		// when
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();

		// then
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(successPing));
	}

}

class SuccessPing {
	private Boolean success;

	public SuccessPing(Boolean success) {
		this.success = success;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
