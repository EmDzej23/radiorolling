package com.nevreme.rolling.model;

public enum PostType {
	STORY(0), POST(1), NONE(-1);

	private int value;

	private PostType(int value) {
		this.value = value;
	}

	public int getPostType() {
		return this.value;
	}

	public static PostType getTypeByValue(int value) {
		switch (value) {
		case 0:
			return STORY;
		case 1:
			return POST;
		default:
			return NONE;
		}
	}
}
