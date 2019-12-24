package pojo;

import javax.validation.constraints.NotNull;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class API {
    
    //API和Excel的映射关系(API字段和Excel列的关系)
    
    @Excel(name="接口编号")
    @NotNull
    private String id;
    
    @Excel(name="接口名称")
    @NotNull
    private String name;
    
    @Excel(name="接口提交方式")
    @NotNull
    private String type;
    
    @Excel(name="接口地址")
    @NotNull
    private String url;
    
    @Excel(name="参数类型")
    @NotNull
    private String contentType;
    

    
    
    public API() {
	super();
    }
    public API(String id, String name, String type, String url, String contentType) {
	super();
	this.id = id;
	this.name = name;
	this.type = type;
	this.url = url;
	this.contentType = contentType;
	
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
	return "API [id=" + id + ", name=" + name + ", type=" + type + ", url=" + url + ", contentType=" + contentType
		+ "]";
    }
   

     
}
