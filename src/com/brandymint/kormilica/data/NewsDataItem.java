package com.brandymint.kormilica.data;


public class NewsDataItem {
	
	private String url;
	private String content;
	private String title;
	private String publisher;
	private String date;
	private String originalUrl;
	private String imageUrl;

	public String toString() {
		return "title - "+title+"; "
				+"publisher - "+publisher+"; "
				+"date - "+date+"; "
				+"content - "+content+"; "
				+"imageUrl - "+imageUrl+"; "
				+"originalUrl - "+originalUrl+"; "
				+"url - "+url+"; ";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
