package com.mdream.gamemanage.model.resulttransformer;



public class GameObject {
	
	

	private Integer id;
	
	private String name;
	
	private String description;
	
	private String url;
	
	private String icon;
	
	private String img;
	
	private Integer star;
	
	private Integer praise;
	
	private Integer belittle;
	
	public GameObject(){}
	
	public GameObject(Integer id, String name, String description, String url,
			String icon, String img, Integer star, Integer praise,
			Integer belittle) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.url = url;
		this.icon = icon;
		this.img = img;
		this.star = star;
		this.praise = praise;
		this.belittle = belittle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public Integer getBelittle() {
		return belittle;
	}

	public void setBelittle(Integer belittle) {
		this.belittle = belittle;
	}
	
	
	
	
}
