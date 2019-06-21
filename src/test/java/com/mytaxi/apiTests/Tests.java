package com.mytaxi.apiTests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.validator.routines.EmailValidator;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.google.gson.Gson;
import com.mytaxi.Apis.RestAssuredExtension;
import com.mytaxi.domains.comments.Comment;
import com.mytaxi.domains.posts.Post;
import com.mytaxi.domains.userinfo.User;
import io.restassured.response.Response;

public class Tests {

	@Parameters({ "url", "username" })
	@Test
	public void checkUserCommentsOnPost(String url, String username) {
		RestAssuredExtension restassuredextension = new RestAssuredExtension(url);
		// get users response
		Response usersResponse = restassuredextension.setGetRequest("/users");
		// get all the users and deserialize them.
		List<User> users = Arrays.asList(new Gson().fromJson(usersResponse.getBody().asString(), User[].class));
		List<User> specificUsers = users.stream().filter(user -> user.username.equals(username))
				.collect(Collectors.toList());
		// get all posts response of specific user.
		Response postResponse = restassuredextension.setGetRequestWithQueryParameters("/posts", "userId",
				specificUsers.get(0).id);
		// get all the posts and deserialize them.
		List<Post> posts = Arrays.asList(new Gson().fromJson(postResponse.getBody().asString(), Post[].class));
		// get all the comments and validate them.
		posts.stream()
				.map(post -> restassuredextension.setGetRequestWithQueryParameters("/comments", "postid", post.id))
				.map(commentBody -> Arrays
						.asList(new Gson().fromJson(commentBody.getBody().asString(), Comment[].class)))
				.forEach(comment -> comment
						.forEach(c -> Assert.assertTrue(EmailValidator.getInstance().isValid(c.email))));
	}
}
