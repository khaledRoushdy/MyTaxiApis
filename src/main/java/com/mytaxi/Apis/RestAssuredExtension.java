package com.mytaxi.Apis;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredExtension {

	private RequestSpecBuilder builder;
	private RequestSpecification request;

	public RestAssuredExtension(String url) {
		builder = new RequestSpecBuilder();
		builder.setBaseUri(url);
		builder.setContentType(ContentType.JSON);
		setRequest(builder);
	}

	private void setRequest(RequestSpecBuilder builder) {
		request = RestAssured.given().spec(builder.build());
	}

	public void setGetRequestWithPathParameters(String url, String pathParamsKey, String pathParamsValue) {
		request.pathParam(pathParamsKey, pathParamsValue);
		try {
			request.get(new URI(url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Response setGetRequest(String url) {
		try {
			return request.get(new URI(url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Response setGetRequestWithQueryParameters(String url, String pathParamsKey, String pathParamsValue) {
		request.param(pathParamsKey, pathParamsValue);
		try {
			return request.get(new URI(url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void getUserId() {
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri("https://jsonplaceholder.typicode.com");
		builder.setContentType(ContentType.JSON);
		RequestSpecification requestSpec = builder.build();
		RestAssured.given().spec(requestSpec);
	}
}
